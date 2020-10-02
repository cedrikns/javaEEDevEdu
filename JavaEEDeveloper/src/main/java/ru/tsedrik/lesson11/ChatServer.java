package ru.tsedrik.lesson11;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {
    private static Map<String, ClientConnection> clients = new ConcurrentHashMap<>();
    private ServerSocket serverSocket;

    public static void main(String[] args) throws Exception{
        ChatServer server = new ChatServer();
        server.serverSocket = new ServerSocket(2000, 500);
        while (true){
            Socket clientSocket = server.serverSocket.accept();
            ClientConnection connection = server.new ClientConnection(clientSocket);
            connection.start();
        }
        
    }

    private class ClientConnection extends Thread{
        Socket clientSocket;
        BufferedWriter clientWriter;
        BufferedReader clientReader;
        String clientName;

        private ClientConnection(Socket socket){
            clientSocket = socket;
            try{
                clientWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        @Override
        public void run(){
            String msg = null;
            try {
                clientName = requestNickname();
                while ((msg = clientReader.readLine()) != null) {
                    if ("quit".equals(msg)){
                        quit();
                        return;
                    } else if (msg.startsWith("@")) {
                        String otherClient = msg.substring(1).split(" ")[0];
                        if (clients.containsKey(otherClient)){
                            sendOtherClient(msg, otherClient);
                        } else {
                            clientWriter.write("There wasn't found client with nickname '" + otherClient + "'\n");
                            clientWriter.flush();
                        }
                    } else {
                        String fullMessage = clientName + ": " + msg;
                        System.out.println(fullMessage);
                        sendAll(fullMessage);
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        private String requestNickname(){
            boolean isCorrectName = false;
            String nickName = "";
            try {
                clientWriter.write("Enter your Nickname (possible chars are letters, digits and '_'):\n");
                clientWriter.flush();
                while (!isCorrectName) {
                    nickName = clientReader.readLine();
                    if ((!clients.containsKey(nickName)) &&(nickName.matches("\\w+"))){
                        isCorrectName = true;
                        clients.put(nickName, this);
                        clientWriter.write(nickName + ", glad to see you in our chat.\n");
                        clientWriter.write("You can use following commands:\n");
                        clientWriter.write("@<nickname>\t-\tto write only user with <nickname>\n");
                        clientWriter.write("quit\t-\tto exit from the chat\n");
                        clientWriter.flush();
                        sendAll("User '" + nickName + "' join to our chat!");
                    } else {
                        clientWriter.write("This nickname is already in use or contain incorrect chars.\n");
                        clientWriter.write("Please, try another one:\n");
                        clientWriter.flush();
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }

            return nickName;
        }

        private void quit(){
            sendAll("User '" + clientName + "' left our chat!");
            clients.remove(clientName);
            close();
        }

        private void close(){
            try {
                clientWriter.close();
                clientReader.close();
                clientSocket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        private void sendOtherClient(String message, String otherClient){
            try {
                clients.get(otherClient).clientWriter.write(clientName + ": " + message + "\n");
                clients.get(otherClient).clientWriter.flush();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        private void sendAll(String message){
            clients.values().forEach(client ->{
                if (client != this){
                    try {
                        client.clientWriter.write(message + "\n");
                        client.clientWriter.flush();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}

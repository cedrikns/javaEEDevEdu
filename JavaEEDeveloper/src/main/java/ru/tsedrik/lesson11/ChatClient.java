package ru.tsedrik.lesson11;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;
    private BufferedWriter toServerWriter;
    private BufferedReader fromServerReader;
    private Scanner scanner;

    public static void main(String[] args){
        ChatClient client = new ChatClient();
        try{
            client.socket = new Socket("localhost", 2000);
            client.toServerWriter = new BufferedWriter(new OutputStreamWriter(client.socket.getOutputStream()));
            client.fromServerReader = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));

            ServerReader serverReader = client.new ServerReader();
            serverReader.start();

            client.scanner = new Scanner(System.in);
            String clientMsg;
            try {
                while ((clientMsg = client.scanner.nextLine()) != "") {
                    client.toServerWriter.write(clientMsg);
                    client.toServerWriter.newLine();
                    client.toServerWriter.flush();

                    if ("quit".equals(clientMsg)) {
                        Thread.sleep(1000);
                        break;
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }

            client.toServerWriter.close();
            client.fromServerReader.close();
            client.socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    class ServerReader extends Thread{
        @Override
        public void run(){
            String serverMsg;
            try{
                while ((serverMsg = fromServerReader.readLine()) != null){

                    System.out.println(serverMsg);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

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
        client.work();
    }

    public void work(){
        try {
            socket = new Socket("localhost", 2000);
            toServerWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            fromServerReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            ServerReader serverReader = new ServerReader();
            serverReader.start();

            scanner = new Scanner(System.in);
            String clientMsg;

            while (!(clientMsg = scanner.nextLine()).equals("")) {
                toServerWriter.write(clientMsg);
                toServerWriter.newLine();
                toServerWriter.flush();

                if ("quit".equals(clientMsg)) {
                    Thread.sleep(1000);
                    break;
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                toServerWriter.close();
                fromServerReader.close();
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
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

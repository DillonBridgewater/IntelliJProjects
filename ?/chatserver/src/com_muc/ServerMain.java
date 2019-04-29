package com_muc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerMain {
    public static void main(String[] args) {
        int port = 8818;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.accept();
            while(true) {
                System.out.println("About to accept client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write(("Hello World \n").getBytes());
                for(int i=0; i<10; i++) {
                    outputStream.write(("Time now is " + new Date() + "\n").getBytes());
                    Thread.sleep(1000);
                }
                clientSocket.close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        }
}

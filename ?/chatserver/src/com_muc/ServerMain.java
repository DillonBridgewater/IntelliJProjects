package com_muc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.zip.CheckedOutputStream;


public class ServerMain {
    public static void main(String[] args) {
        int port = 8818;
        Server server = new Server(port);
        server.run();


    }
}

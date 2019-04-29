package com_muc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class ServerWorker extends Thread {
    private final Socket clientSocket;

    public ServerWorker(Socket clientSocket) {


        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            handleClientSocket();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(("Hello World \n").getBytes());
            outputStream.write(("Time now is " + new Date() + "\n").getBytes());
            Thread.sleep(1000);
        }
        clientSocket.close();
    }
}

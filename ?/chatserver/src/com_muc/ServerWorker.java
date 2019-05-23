package com_muc;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.List;


public class ServerWorker extends Thread {
    private final Socket clientSocket;
    private final Server server;
    private String login = null;
    private OutputStream outputStream;
    public ServerWorker(Server server, Socket clientSocket) {

        this.server = server;
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
        InputStream inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ( (line = reader.readLine()) != null) {
          String[] tokens = StringUtils.split(line);

            if (tokens != null && tokens.length > 0) {
                String cmd = tokens[0];
                if ("quit".equalsIgnoreCase(cmd) || "quit".equalsIgnoreCase((cmd))) {
                    handleLogoff();
                    break;
                }
                else if ("login".equalsIgnoreCase(cmd)) {
                    handlelogin(outputStream, tokens);
                } else if("msg".equalsIgnoreCase(cmd)) {
                    System.out.println("in Msg");
                    String[] tokensMsg = StringUtils.split(line);
                    handleMessage(tokensMsg);
                    System.out.println(tokensMsg[2]);
                } else if ("join".equalsIgnoreCase(cmd)) {
                    handleJoin(tokens);
                } else if ("leave".equalsIgnoreCase(cmd)) {
                    handleLeave(tokens);
                } else {
                    String msg = "unknown" + cmd + "\n";
                }
            }
        }
        clientSocket.close();
    }

    private void handleLeave(String[] tokens) {
    }

    private void handleJoin(String[] tokens) {
    }

    private void handleMessage(String[] tokensMsg) {
    }

    private void handleLogoff() throws IOException {
        String onlineMsg = "offline " + login + "\n";
        List<ServerWorker> workerList = server.getWorkerList();
        for(ServerWorker worker : workerList) {
            if (!login.equals(worker.getLogin())) {
                worker.send(onlineMsg);
            }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }}

    public String getLogin() {
        return login;
    }
    private void handlelogin(OutputStream outputStream, String[] tokens) throws IOException {
        if(tokens.length == 3) {
            String login = tokens[1];
            String password = tokens[2];

            if (login.equals("guest") && password.equals("guest") || login.equals("jim") && password.equals("jim")) {
                String msg = "ok login\n";
                outputStream.write(msg.getBytes());
                this.login = login;
                System.out.println("User logged in succesfully: " + login);
                String onlineMsg = "online " + login + "\n";
                List<ServerWorker> workerList = server.getWorkerList();
                for(ServerWorker worker: workerList) {
                        if (worker.getLogin() != null) {
                            String msg2 = "online " + worker.getLogin() + "\n";
                            send(msg2);
                        }
                }
                for(ServerWorker worker : workerList) {
                    if (!login.equals(worker.getLogin())) {
                        worker.send(onlineMsg);
                    }
                }
            }else {
                String msg = "error login\n";
                outputStream.write(msg.getBytes());
                System.err.println("login failed for " + login);
            }
        }
    }
    private void send(String msg) throws IOException {
        if (login != null) {
            outputStream.write(msg.getBytes());
        }
        }
}

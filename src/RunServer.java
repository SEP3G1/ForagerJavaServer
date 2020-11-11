import Config.Config;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class RunServer {

    public static void main(String args[]) {
        System.out.println("Establishing REST connection");


        System.out.println("Starting server");
        InetAddress ip;
        ServerSocket serverSocket = null;
        Socket socket = null;


        try {
            serverSocket = new ServerSocket(Config.PORT_T2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
          ip = InetAddress.getLocalHost();
          System.out.println("Server hosted on: " + ip);
        } catch (UnknownHostException e) {
          e.printStackTrace();
        }

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            System.out.println("New client Connected");
            // new thread for a client
            new EchoThread(socket).start();
        }
    }
}

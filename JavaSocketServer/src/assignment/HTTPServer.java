/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Alpana
 */
public class HTTPServer {
    boolean shutDown = false;
    private int PORT = 6004;
    public void await() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        
        while(!shutDown){
            System.out.println("new");
            Socket socket = serverSocket.accept();
            try {
                MultiThread t = new MultiThread(socket);
                new Thread(t).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alpana
 */
public class MultiThread extends Thread{
    boolean execute = true;
    private Socket socket;
    protected Thread       runningThread= null;
    public void run() {
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
      while (execute) {
        try {
                initiate(socket);
                execute = false;
            } catch (IOException | ParseException ex) {
                Logger.getLogger(MultiThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     
    public void initiate(Socket socket) throws IOException, ParseException{
        InputStream inputStream = socket.getInputStream();
        HTTPRequest httpRequest = HTTPUtils.parseRequest(inputStream);

        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.serve(httpRequest,socket.getOutputStream(), socket);
    }
    
    MultiThread(Socket serverSocket) throws IOException{
            this.socket = serverSocket;
        }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.io.IOException;

/**
 *
 * @author alpana
 */
public class Assignment {
    
    public static void main(String[] ar) {
        try {
            
        HTTPServer httpServer = new HTTPServer();
        httpServer.await();
        }
        catch(IOException io) {
            System.out.print(io);
        }

    }
}
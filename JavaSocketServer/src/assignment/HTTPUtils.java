/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.io.IOException;
import java.io.*;

/**
 *
 * @author Alpana
 */
public class HTTPUtils {

    public static HTTPRequest parseRequest(InputStream stream) throws IOException {

        HTTPRequest httpRequest = new HTTPRequest();
        InputStreamReader streamReader = new InputStreamReader(stream);
        
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String reader = bufferedReader.readLine();
        System.out.println(reader);
        String[] ar = reader.split(" ");
        String reader1=null;
        httpRequest.setHttpMethod(ar[0]);
        httpRequest.setResourceURI(ar[1]);
        httpRequest.setHttpVersion(ar[2]);

        if(httpRequest.getHttpMethod().equals("PUT")){
           String contentLine = bufferedReader.readLine();
            while (!contentLine.contains( "{\"connId\"")) {
	      System.out.println(contentLine);
              contentLine = bufferedReader.readLine();
            }
           System.out.println(contentLine);
           String ars[] = contentLine.split(":");
           String connId[] = ars[ars.length-1].split("\"");
           System.out.println(connId[1]);
           reader1=connId[1];
        }
        httpRequest.setBody(reader1);
        return httpRequest;

    }
}


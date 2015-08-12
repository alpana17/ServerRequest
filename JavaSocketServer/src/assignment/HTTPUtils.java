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
        if(httpRequest.getHttpMethod().equals("POST")){
            for (int i=0;i<4;i++) {
                reader1=bufferedReader.readLine();
            }
            System.out.println(reader1);
            httpRequest.setBody(reader1);
//            String ar1[] = reader1.split(":");
//            System.out.println(ar1[2]);
//            String[] name1 = ar1[0].split("=");
//            String name = name1[1];
        }

        return httpRequest;

    }
}


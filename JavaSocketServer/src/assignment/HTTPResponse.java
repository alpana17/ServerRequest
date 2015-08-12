/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Alpana
 */
public class HTTPResponse {
    public static int i = 0;
     static String input[][] = new String[100][3];
     static DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
    public void serve(HTTPRequest httpRequest, OutputStream outputStream, Socket socket) throws IOException, ParseException {
      PrintWriter pw = new PrintWriter(outputStream, true);
      if (httpRequest.getHttpMethod().equals("GET")){
          
            String [] url = (httpRequest.getResourceURI()).split("\\?");
            if(url[0].equals("/request")){
                String ar1[] = url[1].split("&");
                String[] name1 = ar1[0].split("=");
                String connId = name1[1];
                String[] name2 = ar1[1].split("=");
                int timeout = Integer.parseInt(name2[1]);
                input[i][0] = connId;
                
                Date startTime = new Date();
                Date parsedDate = df.parse(startTime.toString()); 
                Date endTime = new Date(parsedDate.getTime() + (1*timeout));
                input[i][1] = startTime.toString();
                input[i][2] = endTime.toString();
                i++;
                JSONObject obj = new JSONObject();
                obj.put("status", "ok");
                
                try {
                    Thread.currentThread().sleep(timeout);          
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                pw.println(obj);
            } else if(url[0].equals("/serverStatus")){
                Date now = new Date();
                long newDateTime = df.parse((new Date()).toString()).getTime();
                
                String result = "";
                JSONArray ja = new JSONArray();
                int ar[]= new int[input.length];
                for(int j = 0; j<input.length;j++){
                  if(input[j][0] != null ){
                        Date end = df.parse(input[j][2]);
                        if(end.compareTo(now)>0){
                            long endDateTime = (df.parse(input[j][2])).getTime();

                            long diff = ((endDateTime-newDateTime)/1000);
                            ar[j] = Integer.parseInt(input[j][0]);
                            JSONObject obj = new JSONObject();
                            obj.put(input[j][0], diff);
                            result += obj;
                        }
                        
                    }
                }
                pw.println(result);
            }
        } else if (httpRequest.getHttpMethod().equals("POST")){
            pw.println(httpRequest.getBody());
            
        }
        socket.close();
    }
}
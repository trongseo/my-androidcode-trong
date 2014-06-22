/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fast.email.com;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;
	
public class HttpFileUpload implements Runnable{
        URL connectURL;
        String responseString;
        String Title;
        String Description;
        public String emailto="";
        byte[ ] dataToServer;
        FileInputStream fileInputStream = null;
        public String iFileName = "ovicam_temp_vid.mp4";
        public String fileExt = "";
        HttpFileUpload(String urlString, String vTitle, String vDesc) throws Exception{
               
                        connectURL = new URL(urlString);
                        Title= vTitle;
                        Description = vDesc;
               
        }
	
        void Send_Now(FileInputStream fStream) throws Exception{
        	 fileInputStream = fStream;
        	
                Sending();
        }
	
        void Sending() throws Exception{
               
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                String Tag="fSnd";
               
                        Log.e(Tag,"Starting Http File Sending to URL");
	
                        // Open a HTTP connection to the URL
                        HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();
	
                        // Allow Inputs
                        conn.setDoInput(true);
	
                        // Allow Outputs
                        conn.setDoOutput(true);
	
                        // Don't use a cached copy.
                        conn.setUseCaches(false);
	
                        // Use a post method.
                        conn.setRequestMethod("POST");
	
                        conn.setRequestProperty("Connection", "Keep-Alive");
	
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
	
                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
	
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"title\""+ lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(Title);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
	                        
                        dos.writeBytes("Content-Disposition: form-data; name=\"description\""+ lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(Description);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
	                        
                        dos.writeBytes("Content-Disposition: form-data; name=\"emailto\""+ lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(emailto);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                      //  ?title=aaa&emailto=trongtayninh@gmail.com&description=vivi&fileExt=jpg
                        dos.writeBytes("Content-Disposition: form-data; name=\"fileExt\""+ lineEnd);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(fileExt);
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        
                        if(iFileName!="")
                        {
                        	
                        	dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + iFileName +"\"" + lineEnd);
                            dos.writeBytes(lineEnd);
                            Log.e(Tag,"Headers are written");
                        	
                            // create a buffer of maximum size
                            int bytesAvailable = fileInputStream.available();
    	                        
                            int maxBufferSize = 1024;
                            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            byte[ ] buffer = new byte[bufferSize];
    	
                            // read file and write it into form...
                            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
    	
                            while (bytesRead > 0)
                            {
                                    dos.write(buffer, 0, bufferSize);
                                    bytesAvailable = fileInputStream.available();
                                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                                    bytesRead = fileInputStream.read(buffer, 0,bufferSize);
                            }
                            dos.writeBytes(lineEnd);
                            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
    	
                            // close streams
                            fileInputStream.close();
                        }
                        
                       
	                        
                        dos.flush();
	                        
                        Log.e(Tag,"File Sent, Response: "+String.valueOf(conn.getResponseCode()));
	                         
                        InputStream is = conn.getInputStream();
	                        
                        // retrieve the response from server
                        int ch;
	
                        StringBuffer b =new StringBuffer();
                        while( ( ch = is.read() ) != -1 ){ b.append( (char)ch ); }
                        String s=b.toString();
                        Log.i("Response",s);
                        dos.close();
               
        }
	
        @Override
        public void run() {
                // TODO Auto-generated method stub
        }
}
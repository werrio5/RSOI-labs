/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author werrio5
 */
public class MainClass {

    public static void main(String[] args) throws Throwable {
        ServerSocket ss = new ServerSocket(8080);
        while (true) {
            Socket s = ss.accept();
            System.err.println("Client accepted");
            new Thread(new SocketProcessor(s)).start();
        }
    }

    private static class SocketProcessor implements Runnable {

        private Socket s;
        private InputStream is;
        private OutputStream os;

        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }

        public void run() {
            try {
                Logging.log();
                readInputHeaders();
                //index.html
                String path = System.getProperty("user.dir") + File.separator
                        + "src" + File.separator + "main" + File.separator + "java" + File.separator
                        + "com" + File.separator + "mycompany" + File.separator
                        + "dockerapp" + File.separator + "index.html";
                StringBuilder contentBuilder = new StringBuilder();

                BufferedReader in = new BufferedReader(new FileReader(path));
                String str;
                while ((str = in.readLine()) != null) {
                    contentBuilder.append(str);
                }
                in.close();

                
                String content = contentBuilder.toString();
                writeResponse(content);
            } catch (Throwable t) {
                /*do nothing*/
            } finally {
                try {
                    s.close();
                } catch (Throwable t) {
                    /*do nothing*/
                }
            }
            System.err.println("Client processing finished");
        }

        private void writeResponse(String s) throws Throwable {
            String response = "HTTP/1.1 200 OK\r\n"
                    + "Server: server\r\n"
                    + "Content-Type: text/html\r\n"
                    + "Content-Length: " + s.length() + "\r\n"
                    + "Connection: close\r\n\r\n";
            String result = response + s;
            os.write(result.getBytes());
            os.flush();
        }

        private void readInputHeaders() throws Throwable {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String s = br.readLine();
                if (s == null || s.trim().length() == 0) {
                    break;
                }
            }
        }
    }
}

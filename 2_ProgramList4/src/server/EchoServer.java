//II 4-3: 213/818
//程序清单4-3
package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        //establish server socket
        try(ServerSocket s = new ServerSocket(8189)){
            //wait for client connection
            try(Socket incoming = s.accept()){
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();

                try(Scanner in = new Scanner(inStream,"UTF-8")){
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream, "UTF-8"),true/*autoFlush*/);
                    out.println("Hello! Enter BYE to exit.");

                    //echo client input
                    boolean done = false;
                    while(!done && in.hasNextLine()){
                        String line = in.nextLine();
                        out.println("Echo: "+line);
                        if(line.trim().equals("BYE")) done = true;
                    }
                }
            }
        }
    }
}

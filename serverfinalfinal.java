/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newserver;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class serverfinalfinal extends Thread{
    public ServerSocket ss;
    public Socket socket;
    public DataInputStream in;
    public DataOutputStream out;
    public BufferedReader br;
    /**
     * @param args the command line arguments
     */
    public serverfinalfinal (){
        
    }
    public void  Final() throws IOException{
       ss=new ServerSocket(333) ;
        System.out.println("waiting for a client");
        
       
        while (true) {
            socket=ss.accept();
            System.out.println("connected");
            new threadserverthread(socket).start();
    
    
    }}
    @Override
    public void run(){
       try {
           serverfinalfinal server=new serverfinalfinal();
           server.Final();
       } catch (IOException ex) {
           Logger.getLogger(serverfinalfinal.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
         serverfinalfinal server=new serverfinalfinal();
    }
    
}

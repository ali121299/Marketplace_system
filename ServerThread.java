/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ali Ahmed
 */
public class ServerThread extends Thread {
   
    Socket socket;
 
 
    public ServerThread(Socket socket){
        this.socket = socket;
    }
    public String readIn() throws IOException{
     
        DataInputStream in = new DataInputStream(socket.getInputStream());
        String str=in.readUTF();
        System.out.println("Server says "+str);
        return str;
    }
 
    
    @Override
      public void run(){
        
        try {
            String mssg;
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Scanner sc = new Scanner(System.in);
            while( !(mssg = in.readUTF()).equals("close")){
                System.out.println("[clinet] " + mssg);
                mssg=sc.nextLine();
                out.writeUTF(mssg);
            }
            System.out.println("ended");
            socket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

      }
}

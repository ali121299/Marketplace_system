/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test2;

/**
 *
 * @author east asia
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class SocketServer {
    private ServerSocket ss;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader br;
   
    public SocketServer() {
    }
    
    void connectClient(int port) throws IOException{
        //listen
        ss = new ServerSocket(port);
        //connect client
        socket = ss.accept();
        new ServerThread(client).start();
    }
    
    // Read from client
    String readIn() throws IOException{
         in = new DataInputStream(socket.getInputStream());
        String str = in.readUTF();
        System.out.println("Client says "+str);
        return str;
    }
    // Read data items, add them to array and return the array
    ArrayList<String> readIntoArray() throws IOException{
        in = new DataInputStream(socket.getInputStream());
        ArrayList<String> al = new ArrayList<>();
        al.add(in.readUTF());
        return al;
    }
    
    //write to client
    void writeOut(String str) throws IOException{
        out = new DataOutputStream(socket.getOutputStream());
        br = new BufferedReader( new InputStreamReader(System.in));
        str = br.readLine();
        out.writeUTF(str);
        out.flush();   
    }
    
    // Send array to client
    void sendArray(ArrayList<String> arr) throws IOException{
        ObjectOutputStream out_arr = new ObjectOutputStream(socket.getOutputStream());
        out_arr.writeObject(arr);   
    }
    
    //recieve array from client
    ArrayList<String> recieveArray() throws IOException, ClassNotFoundException{
        ObjectInputStream in_arr = new ObjectInputStream(socket.getInputStream());
        ArrayList<String> arr = (ArrayList<String>) in_arr.readObject();
        return arr;
    }
    
    void endConnect() throws IOException{
      // in.close();
      // socket.close();
       ss.close();
    }
}

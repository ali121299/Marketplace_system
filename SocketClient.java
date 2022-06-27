/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author east asia
 */
//import java.util.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class SocketClient {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader br;

    public SocketClient() {
    }
    
    void connectServer(int port) throws IOException{
        
            socket = new Socket("127.0.0.1",port);
            System.out.println("Connection established to port "+port);   
    }
    
    // Read data from server
    String readIn() throws IOException{
        in = new DataInputStream(socket.getInputStream());
        String str=in.readUTF();
        System.out.println("Server says "+str);
        return str;
    }
    
    // Read data items, add them to array and return the array
    ArrayList<String> readIntoArray() throws IOException{
        in = new DataInputStream(socket.getInputStream());
        ArrayList<String> al = new ArrayList<>();
        al.add(in.readUTF());
        return al;
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
    
    // write data to server
    String writeOut(String str) throws IOException{
        out = new DataOutputStream(socket.getOutputStream());
        br = new BufferedReader( new InputStreamReader(System.in));
        str = br.readLine();
        out.writeUTF(str);
        out.flush();
        return str;   
    }
 
    void endConnect() throws IOException{
       // in.close();
       // out.close();
        socket.close();
    }

}


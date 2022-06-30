/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Ali Ahmed
 */
public class SocketServer {
     private ServerSocket ss;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader br;
   
    public SocketServer() throws IOException {
        //System.out.println("test");
        ss=new ServerSocket(333) ;
        System.out.println("waiting for a client");
        while (true) {
            socket=ss.accept();
            System.out.println("connected");
            new ServerThread(socket).start();
        }
    }
    
    void connectClient(int port) throws IOException{
        //listen
        ss = new ServerSocket(port);
        //connect client
        socket = ss.accept();
        new ServerThread(socket).start();
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
    
    public static void main(String[] args) throws IOException {
      SocketServer server=new SocketServer();
      
      
      
    }
   
}

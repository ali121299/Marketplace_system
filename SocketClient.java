/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author Ali Ahmed
 */
public class SocketClient {
     private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader br;

    public SocketClient() {
    }
    
    public Socket connectServer(int port) throws IOException{
        
            socket = new Socket("127.0.0.1",port);
            System.out.println("Connection established to port "+port);  
            return socket;
    }
    
    // Read data from server
    public String readIn() throws IOException{
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
    
    
    //send string to server and recieve string
    public String request1 (String request) throws IOException{
	    Socket socket = connectServer(555);
	    writeOut(request);
	    return readIn();
    }
    
    //Send string to server and recieve array from server
    public ArrayList<String> request (String request) throws IOException, ClassNotFoundException{
        Socket socket = connectServer(555);
	    writeOut(request);
	    return recieveArray();
    }

    
 public static void main(String args[]) throws IOException, ClassNotFoundException {
        
        //String request= "";i "127.0.0.1", 333 
        Socket client = new Socket ("127.0.0.1", 333);
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        Scanner sc = new Scanner(System.in);
        String mssg;
         DataInputStream in = new DataInputStream(client.getInputStream());
        while(true){
            //System.out.println("");
            mssg = sc.nextLine();
            System.out.println("[client] " + mssg);  
            out.writeUTF(mssg);
            mssg=in.readUTF();
            System.out.println(mssg);
        }
        
    }

        
      
      
    }
   




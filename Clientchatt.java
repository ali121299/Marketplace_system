/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientchatt;

/**
 *
 * @author Mariem
 */
        import java.net.*;
import java.io.*;

public class Clientchatt {
    public static String merge (String s1,String s2){
        return s1+";"+s2;
    }
    public static String request1(String s1,String s2){
        String send,receive=null;
        try{
        String server_send=merge(s1,s2);
       Socket socket=new Socket("localhost",2784);
        System.out.println(".....................now you can start chat ...................");
//        BufferedReader keyr=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
        //client reading the message from server through it's inputstream
        InputStreamReader isr=new InputStreamReader(socket.getInputStream());//opening the input stream
        BufferedReader ser_receive=new BufferedReader(isr);
        
       
        while(true){
//        send=keyr.readLine();
          send=server_send;
        pw.println(send);
      
        if((receive=ser_receive.readLine())!=null){
        System.out.println("server : "+receive);
        
        }
        
        }
        
        }
        catch(Exception e){}
        return receive;
    }
    /**
     * @param args the command line arguments
     */
        // TODO code application logic here
        public static void main(String[] args) {
//        try
//        {
//        Socket socket=new Socket("localhost",2784);
//        System.out.println(".....................now you can start chat ...................");
//        BufferedReader keyr=new BufferedReader(new InputStreamReader(System.in));
//        PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
//        //client reading the message from server through it's inputstream
//        InputStreamReader isr=new InputStreamReader(socket.getInputStream());//opening the input stream
//        BufferedReader ser_receive=new BufferedReader(isr);
//        String send,receive;
//        String s1="login";
//        String s2="Mariam,123";
//        String total=s1+";"+s2;
//        while(true){
////        send=keyr.readLine();
//          send=total;
//        pw.println(send);
//        if(send.equals("bye")){
//        System.exit(0);
//        }
//        if((receive=ser_receive.readLine())!=null){
//        System.out.println("server : "+receive);
//        
//        }
//        
//        }
//        
//        }
//        catch(Exception e){}
String res=request1("login","mariam,123");
System.out.print(res);
    }
    
    
}

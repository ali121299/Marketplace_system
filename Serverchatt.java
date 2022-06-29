/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchatt;

/**
 *
 * @author Mariem
 */
import java.net.*;
import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class Serverchatt {

//        static void insert (){
// try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/" + "marketplace_System", "root", "01119023565");
//            PreparedStatement stmt = con.prepareStatement("insert into login_signup values(\"please\",14)");
//            stmt.executeUpdate();
//            con.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//
//     }
    //----------string parsing---------
    public static ArrayList<String> parsing(String s){
        ArrayList<String>r=new ArrayList<String>();
        int init=0;
        for (int i=0;i<s.length();i++){
            if((s.charAt(i)==',')) {
                
                r.add(s.substring(init,i));
                init=i+1;
            }
        }
        r.add(s.substring(init));
        return r;
    }
    //----------login logic---------
    public static String loginValidation(String st){
        ArrayList<String> pa=parsing(st);
        String user=pa.get(0);
        String password = pa.get(1);
        String s="";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+"marketplace_system","root","01119023565");
            Statement stmt=con.createStatement();
            ResultSet names=stmt.executeQuery("select Username from login_signup");
            boolean exist=false;
            while (names.next()){
                if((names.getString(1).compareToIgnoreCase(user))==0){
                exist=true;}
            }
            if(!exist){
            s="non existing username!";
            return s;
            }
            ResultSet rs=stmt.executeQuery("select Password from login_signup where Username=\""+user+"\"");
            boolean pass_exist=false;
            String pass="";
            while (rs.next()){
            pass=rs.getString(1);
            pass_exist=true;}
            if(pass_exist&&(pass.compareToIgnoreCase(password)==0)){
                s="successful login";}
            else s="wrong password!";
            con.close();}
        catch(Exception e){System.out.println(e);}
        return s;
           
            }
        
    
    public static void main(String[] args) {
        // TODO code application logic here
        String tst=null;
        String [] yrb;
        String sendtoclient=null;
        try{
        ServerSocket server=new ServerSocket(2784);
        System.out.println("server is waiting for the client to connect");
       Socket socket =server.accept();
       System.out.println("connection is established");
//        BufferedReader keyr=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
        //server reading from the client through it's inputstream
        InputStreamReader isr=new InputStreamReader(socket.getInputStream());//opening the input stream
        BufferedReader ser_receive=new BufferedReader(isr);
        String send,receive;
        while(true){
        if((receive=ser_receive.readLine())!=null){
        System.out.println("client : "+receive);
        tst= receive.toString();
        yrb= tst.split(";");
        for(int i=0;i<yrb.length;i++){
            System.out.println(yrb[i]);
        }
        ////////// Cases ///////////
        if(yrb[0].equals("login")){
        sendtoclient=loginValidation(yrb[1]);

        }
        }
       
        send=sendtoclient;

        System.out.println(send);
        pw.println(send);
        System.exit(0);
//        if(send.equals("bye")){
//        System.exit(0);
//        }
        }
    }
        catch(Exception e){}
    }
}

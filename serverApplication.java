/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.io.*;
import java.net.*;
import java.util.*;
import static javafx.application.Application.launch;

 public class marketserver extends Application {


    TextField search = new TextField();
    public static String Username;
    public static String DB="Marketplace_System";
    public static String DB_password="Sonsuza kadar";
    public static String connector="com.mysql.jdbc.Driver";
    GridPane grid = new GridPane();
    GridPane root = new GridPane();
    TextField qty = new TextField();

    public static String Password;


    class item_qty {

        String name;
        String qty;

        public item_qty(String n, String q) {
            name = n;
            qty = q;
        }
    }

    private static Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }
    public static void main(String[] args) throws IOException {
        
        new serverfinalfinal().start();
        launch();
        
   
         
    }


 public static synchronized int purchase(String Username) {
        ArrayList<String[]> items = new ArrayList<String[]>();
        //get items in cart
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select item_name, Amount from cart Where username = '" + Username + "'");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2));
                String[] a = {rs.getString(1), rs.getString(2)};
                items.add(a);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        float total_price = 0;
        //get total price
        for (int i = 0; i < items.size(); i++) {
            System.out.println("price:" + items.get(i)[0] + " " + Float.parseFloat(items.get(i)[1]));
            String item_name = items.get(i)[0];
            float am = Float.parseFloat(items.get(i)[1]);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select Price from items Where Name = '" + item_name + "'");
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                    total_price = total_price + (Float.parseFloat(rs.getString(1)) * am);
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("finish");
        }
        //check balance
        float cash = 0;
        System.out.println(Username);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select Current_balance from Account Where Username = '" + Username + "'");
            while (rs.next()) {
                System.out.println(rs.getString(1));
                cash = Float.parseFloat(rs.getString(1));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("finish");
        //compare balance with total price
        System.out.println(cash + " " + total_price);
        if (cash < total_price) {
            System.out.println("No Enough Balance");
            //call pop up screen
            return -1;
        }
        //add order to orderspecs

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            PreparedStatement stmt = con.prepareStatement("insert into Orderspecs (client_name, totalprice, date_time) values(?,?,?)");
            stmt.setString(1, Username);
            stmt.setFloat(2, (float) total_price);
            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            stmt.setTimestamp(3, date);
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("finish");
        //get OID to use it in Ordered items
        int OID = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT OID FROM Orderspecs ORDER BY date_time DESC LIMIT 1");
            while (rs.next()) {
                System.out.println(rs.getString(1));
                OID = Integer.parseInt(rs.getString(1));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("finish oid");
        //fill ordereditems
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            for (int i = 0; i < items.size(); i++) {
                //String[] a = items.get(i).split(",");
                String item_name = items.get(i)[0];
                float am = Float.parseFloat(items.get(i)[1]);
                System.out.println("orderspecs:"+ item_name + " " + am);
                PreparedStatement stmt = con.prepareStatement("insert into Orderitems values(?,?,?)");
                stmt.setInt(1, OID);
                stmt.setString(2, item_name);
                stmt.setFloat(3, am);
                stmt.executeUpdate();
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("finish orderitem");
        //decrease client cash using update
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
//here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("UPDATE cart SET amount = " + qty + "WHERE (item_name = '" + item_name + "') AND (Username= '" + Username + "')");
            cash = cash - total_price;
            String query = "UPDATE Account SET Current_balance = ? WHERE (Username= ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setFloat(1, cash);
            preparedStmt.setString(2, Username);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            System.out.println("bassanty");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("finish current acc");
        //remove items from client cart
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            Statement stmt = con.createStatement();
            String sql = "DELETE FROM cart  WHERE Username = '" + Username + "'";
            stmt.executeUpdate(sql);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("finish cart");
        //update market
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            Statement stmt = con.createStatement();
            String query = "UPDATE Market SET cash = cash + " + total_price + "WHERE MarketID = 1";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            //preparedStmt.setFloat(1, new_cash);
            preparedStmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("finish market");
        return 1;
    }

    public static int edit(String message) {
        String[] arrOfStr = message.split(",");
        int qty = Integer.parseInt(arrOfStr[0]);
        String item_name = arrOfStr[1];
        String Username = arrOfStr[2];
        System.out.println(qty + " " + item_name+" " + Username);
        return edit(qty, item_name, Username);
    }
    //edit cart
    public static int edit(int qty, String item_name,String Username) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            Statement stmt = con.createStatement();
            //check quantity
            int stock = 0;
            ResultSet rs = stmt.executeQuery("select Stock from items WHERE Name ='" + item_name + "'");
            while (rs.next()) {
                stock = rs.getInt(1);
                System.out.println(stock);
            }
            if (stock < qty) {
                return -1;
            }
            stock = stock - qty;
            //decrease stock
            System.out.println("stop");
            String sl = "UPDATE items SET Stock = " + stock + " WHERE Name = '" + item_name + "'";
            stmt.executeUpdate(sl);
            //get amount in cart
            int amount = 0;
            ResultSet r = stmt.executeQuery("select amount from cart WHERE (item_name = '" + item_name + "') AND (Username= '" + Username + "')");
            while (r.next()) {
                amount = r.getInt(1);
                System.out.println(stock);
            }
            //updtate cart
            String query = "UPDATE cart SET amount = ? WHERE (item_name = ?) AND (Username= ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, qty + amount);
            preparedStmt.setString(2, item_name);
            preparedStmt.setString(3, Username);
            preparedStmt.executeUpdate();
            System.out.println("bassanty");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return 1;
    }

    //use search to get the items in cart of certain Username
    public static ArrayList<String> search(String Username) {
        ArrayList<String> items = new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from cart WHERE Username = '" + Username + "'");

            while (rs.next()) {
                items.add(rs.getString(2)+","+rs.getString(3));
            }
            System.out.println("bassanty");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return items;
    }

    //search for certain item in certain category
    public static ArrayList<String> searchForItem(String text) {
        ArrayList<String> items = new ArrayList<String>();
        String[] s = name_cat(text);
        String item_name = s[0];
        String category_name = s[1];
        System.out.println(item_name + " " + category_name);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM items WHERE ( Name LIKE'%" + item_name + "%') AND (category LIKE'%" + category_name + "%')";
            
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                items.add(rs.getString(1));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return items;
    }

    private static String[] name_cat(String text) {
        String[] s = text.split(",");
        return s;
    }

    public static synchronized int addTocart(String message) {
        String[] arrOfStr = message.split(",");
        int qty = Integer.parseInt(arrOfStr[1]);
        String item_name = arrOfStr[0];
        return addToCart(item_name, qty);
    }

    //add item to user cart
    public static synchronized int addToCart(String item_name, int qty) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            Statement stmt = con.createStatement();
            //check quantity
            int stock = 0;
            ResultSet rs = stmt.executeQuery("select Stock from items WHERE Name ='" + item_name + "'");
            while (rs.next()) {
                stock = rs.getInt(1);
                System.out.println(stock);
            }
            if (stock < qty) {
                return -1;
            }
            stock = stock - qty;
            //decrease stock
            System.out.println("stop");
            String sl = "UPDATE items SET Stock = " + stock + " WHERE Name = '" + item_name + "'";
            stmt.executeUpdate(sl);
            //insert into table
            System.out.println("Inserting records into cart table...");
            String sql = "INSERT INTO cart  VALUES ('" + Username + "', '" + item_name + "' , " + qty + ")";
            stmt.executeUpdate(sql);
            System.out.println("bassanty");
            con.close();
        } catch (Exception eee) {
            System.out.println(eee);
        }
        return 1;
    }

    public static void remove(String message) {
        String[] arrOfStr = message.split(",");
        String item_name = arrOfStr[0];
        int qty = Integer.parseInt(arrOfStr[1]);
        String Username = arrOfStr[2];
        remove(item_name, qty, Username);
    }

    //remove item from user cart
    public static void remove(String item_name, int qty, String Username) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            Statement stmt = con.createStatement();
            //get stock
            int stock = 0;
            System.out.println("here");
            ResultSet rs = stmt.executeQuery("select Stock from items WHERE Name ='" + item_name + "'");
            System.out.println("here1");
            while (rs.next()) {
                stock = rs.getInt(1);
                System.out.println(stock);
            }
            //increase stock
            stock = stock + qty;
            System.out.println("new stock" + stock);
            String sl = "UPDATE items SET Stock = " + stock + " WHERE Name = '" + item_name + "'";
            stmt.executeUpdate(sl);
            System.out.println("delete..");
            String sql = "DELETE FROM cart  WHERE (item_name ='" + item_name + "') AND (Username = '" + Username + "')";
            stmt.executeUpdate(sql);
            System.out.println("bassanty");
            con.close();
        } catch (Exception eee) {
            System.out.println(eee);
        }
    }




   

    @Override
    public void start(Stage stage) throws IOException {
        server_screen();
    }
    public void server_screen(){
        Stage stage=new Stage();
        VBox v=new VBox();
        v.setAlignment(Pos.CENTER);
        Scene s=new Scene(v,300,300);
        stage.setScene(s);
        stage.setTitle("Srever screen");
        stage.getIcons().add(icon());
        Button report= new Button("Overview report");
        report.setOnMouseClicked((new EventHandler <MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                show_report();
            }            
        }));
        v.getChildren().add(report);
        stage.show();
    }
    public void show_report(){
        Stage stage=new Stage();
        double num=no_of_clients();
        ArrayList<String> names=Names();
        ArrayList<Float> money=cash();
        VBox p=new VBox();
        p.setAlignment(Pos.CENTER);        
        GridPane root=new GridPane();
        root.setAlignment(Pos.CENTER);        
        root.setHgap(10);
        root.setVgap(15);
        root.setPadding(new Insets(10, 10, 10, 10));
        //root.setGridLinesVisible(true);
        HBox h1=new HBox(7);
        h1.setAlignment(Pos.CENTER);
        //h1.setBorder(Border.stroke(Color.BLACK));
        Label clients=new Label("No of clients");
        h1.setMargin(clients,new Insets(10, 10, 10, 10));
        Label no=new Label(String.valueOf(num));
        h1.setMargin(no,new Insets(10, 10, 10, 10));
        h1.getChildren().addAll(clients,no);
        p.setMargin(h1,new Insets(20, 20, 20, 20));
        p.getChildren().addAll(h1,root);
        HBox h2=new HBox(7);
        h2.setAlignment(Pos.CENTER);
        Label client=new Label("Client name");
        h2.setMargin(client,new Insets(10, 10, 10, 10));
        Label cash=new Label("Current cash");
        h2.setMargin(cash,new Insets(10, 10, 10, 10));
        h2.getChildren().addAll(client,cash);
        root.addRow(0, h2);
        int k=1;
        for(int i=0;i<names.size();i++){
            System.out.println(i);
            String na=names.get(i);
            Float m=money.get(i);
            HBox h3 = new HBox(7);
            h3.setAlignment(Pos.CENTER);
            Label n = new Label(na);
            h3.setMargin(n, new Insets(10, 10, 10, 10));
            Label c = new Label(String.valueOf(m));
            h3.setMargin(c, new Insets(10, 10, 10, 10));
            h3.getChildren().addAll(n, c);
            root.addRow(k, h3);
            k++;
        }
        Scene s=new Scene(p,500,500);
        stage.setScene(s);
        stage.setTitle("Reports screen");
        stage.getIcons().add(icon());
        
        stage.showAndWait();
    }
    public static ArrayList<String> Names(){
        ArrayList<String> r=new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+DB, "root",DB_password ); 
            Statement stmt = con.createStatement();
            ResultSet names = stmt.executeQuery("select Username from account");
            while (names.next()){
                r.add(names.getString(1));
            }           
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        } 
        return r;
    }
    public static ArrayList<Float> cash(){
        ArrayList<Float> r=new ArrayList<Float>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+DB, "root",DB_password ); 
            Statement stmt = con.createStatement();
            ResultSet cash = stmt.executeQuery("select Current_balance from account");
            while (cash.next()){
                r.add(cash.getFloat(1));
            }           
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        } 
        return r;
    }
    public static double no_of_clients(){
        double r=0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+DB, "root",DB_password ); 
            Statement stmt = con.createStatement();
            ResultSet num = stmt.executeQuery("select count(*) from account");
            while (num.next()){
                r=num.getInt(1);
            }           
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        } 
        return r;
    }
    public static String loginValidation(String st){
        ArrayList <String>pa =parsing(st);
        String user=pa.get(0);
        String password=pa.get(1);
        String s="";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+DB, "root",DB_password ); 
            Statement stmt = con.createStatement();
            ResultSet names = stmt.executeQuery("select Username from Login_Signup");
            boolean exist=false;
            while(names.next()){
                if((names.getString(1).compareToIgnoreCase(user))==0) exist=true;
            }
            if(!exist){
                s="non existing username!";
                return s;
            }
            ResultSet rs = stmt.executeQuery("select Password from Login_Signup where Username=\""+user+"\"");
            boolean pass_exist=false;
            String pass="";
            while (rs.next()) {
                pass=rs.getString(1);
                pass_exist=true;
            }
            if(pass_exist &&(pass.compareToIgnoreCase(password)==0)){                
                s="successful login";
            }
            else s="wrong password!";
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        } 
        
        return s;
  
    }
    public static void signUp_handler(String s){
        ArrayList <String>pa =parsing(s);
        String name=pa.get(0);
        String pass=pa.get(1);
        String email=pa.get(2);
        String bir=pa.get(3);
        String phone=pa.get(4);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+DB, "root",DB_password ); 
            Statement stmt = con.createStatement();
            System.out.println("hhhhh");
            stmt.executeUpdate("insert into Login_Signup values(\""+name+"\",\""+pass+"\",\""+email+"\",\""+LocalDate.parse(bir)+"\","+Integer.parseInt(phone)+" )");
            stmt.executeUpdate("insert into account values(\""+name+"\",\"client\",0.0)");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        } 

    }

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
    public static Image icon(){
        String path="";
        try {
            String currentPath = new java.io.File(".").getCanonicalPath();

            System.out.println(currentPath);
            char c='/';
            for(int i=0;i<currentPath.length();i++){
                if(currentPath.charAt(i)=='/') {
                    c='/';
                    break;
                }
                else if(currentPath.charAt(i)=='\\'){
                c='\\';
            }
            }

          path=currentPath+c+"market.jpg";

        
        } catch (Exception e) {

        }
        Image logo=new Image(path);
        return logo;
        
    }


public static ArrayList<String>  history(String user_name) {
    ArrayList<String> temp = new ArrayList<String>();
    try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/" + DB,"root",DB_password);
        Statement stmt = con.createStatement();
        String text1= "SELECT orderitems.OID,Item_name,totalprice,amount,date_time FROM account,orderspecs,orderitems WHERE account.Username =  orderspecs.Client_name " +
                "AND orderspecs.OID = orderitems.OID AND  account.Username = \""+user_name+"\" "
                ;
        ResultSet rs1 = stmt.executeQuery(text1);
        while (rs1.next()) {
            String tempstr = "";
            tempstr += String.valueOf(rs1.getInt(1))+" : "+rs1.getString(2) + " : "+String.valueOf(rs1.getFloat(3))+" : "+String.valueOf(rs1.getFloat(4))+" : "+String.valueOf(rs1.getDate(5));
            temp.add(tempstr);
        }
        con.close();
    } catch (Exception e) {
        System.out.println(e);
    }
    return temp;
}


   



    public static String cashFunc(String user_name)  {
        float temp=0;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB,"root",DB_password);
            Statement stmt = con.createStatement();
            String text= "SELECT Current_balance FROM account WHERE account.Username=\""+user_name+"\"";
            ResultSet rs = stmt.executeQuery(text);
            while (rs.next()) {
                temp = rs.getFloat(1);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return String.valueOf(temp);
    }

   
    public static ArrayList<String> accountInfo(String user_name) {
         ArrayList<String> temp = new ArrayList<String>();
        String tempstr = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con1 = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB,"root",DB_password);

            Statement stmt1 = con1.createStatement();
            String text1= "SELECT * From login_signup1 where login_signup1.Username = \"" +user_name+"\"";

            ResultSet rs1 = stmt1.executeQuery(text1);
            while (rs1.next()) {

                tempstr += rs1.getString(1)+":"+rs1.getString(2)+":"+rs1.getString(3)+":"+rs1.getString(4)+":"+rs1.getString(5);
                temp.add(tempstr);
            }
            con1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return temp;
    }
    public static void deposit(String par) {
        float tempfloat = 0;
        try {

             ArrayList<String> temp = new ArrayList<String>();
            temp = parsing(par);
            String user_name = temp.get(0);
            float amount = Float.parseFloat(temp.get(1));
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB,"root",DB_password);
            Statement stmt = con.createStatement();
            String text= "SELECT Current_balance FROM account WHERE account.Username=\""+user_name+"\"";
            ResultSet rs = stmt.executeQuery(text);
            while (rs.next()) {
                tempfloat = rs.getFloat(1);
            }
            float newcash = tempfloat+amount;
            String text1= "UPDATE  account SET Current_balance = \""+newcash+"\"  WHERE  account.Username=\""+user_name+"\"";
            stmt.executeUpdate(text1);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
     public static String show_items(String s) throws ClassNotFoundException {
        String res = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select Name ,Price from items ");
            while (rs.next()) {
                res += rs.getString(1);
                res += ",";
                res += rs.getString(2);
                res += ",";
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("show items: " + res);
        return res;

    }
    


 
}
/////////////////////////////////////mariamwaleed///////////////////////////////////////
 class ServerThread extends Thread {
    ///////////////////////////////////////////////////global variables////////////////////////////////////////
    public  Socket clientSocket;
     public static String send=null;
   public static ArrayList<String> searchitem;
//  public static ArrayList<item_qty> s = new ArrayList<item_qty>();
   public static  ArrayList<String> cart;
   public static String DB="Market_ONLINE";
   public static String DB_password="Mariam2018";
   public static String connector="com.mysql.cj.jdbc.Driver";

 public ServerThread(Socket socket)
   {this.clientSocket=socket;}
       
    @Override 
        public void run(){
    
    
    
    try{
        PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(),true);
        InputStreamReader isr=new InputStreamReader(clientSocket.getInputStream());//opening the input stream
        BufferedReader ser_receive=new BufferedReader(isr);
        String receive= null; String tst=null;String [] yrb;String sendtoclient=null;
        while(true){
        if((receive=ser_receive.readLine())!=null){
        System.out.println("client : "+receive);
        tst= receive;
        yrb= tst.split(";");
        for(int i=0;i<yrb.length;i++){
            System.out.println(yrb[i]);
        }
        ////////// Cases ///////////
        if(yrb[0].equals("login")){
        sendtoclient=marketserver.loginValidation(yrb[1]);
        send=sendtoclient;
        }
        else if(yrb[0].equals("signup")){
       
        marketserver.signUp_handler(yrb[1]);
        
        }
        else if(yrb[0].equals("search4item")){
         searchitem=marketserver.searchForItem(yrb[1]);
            
            send=searchitem.toString();
        }
        else if(yrb[0].equals("edit")){
            String edit=String.valueOf(marketserver.edit(yrb[1]));
       
          send=edit;
        
        }
        /////////////////////////////////////////////////////////////////////////////////////
        else if(yrb[0].equals("search")){
            cart=marketserver.search(yrb[1]);
            send=cart.toString();
            
        }
        else if (yrb[0].equals("purchase")){
         String purchase=String.valueOf(marketserver.purchase(yrb[1]));
     
          send=purchase;
//              System.out.println("hhhhhhhhhhhhhhhhhhhhhh");
        }
        else if(yrb[0].equals("remove")){
        marketserver.remove(yrb[1]);
        
        }
        else if(yrb[0].equals("add2cart")){
        String add2cart=String.valueOf(marketserver.addTocart(yrb[1]));
     
          send=add2cart;
        }
        else if(yrb[0].equals("account")){
        
         cart=marketserver.accountInfo(yrb[1]);
            send=cart.toString();
        }
        else if(yrb[0].equals("deposit")){
          marketserver.deposit(yrb[1]);
        }
        else if(yrb[0].equals("history")){
        cart=marketserver.history(yrb[1]);
            send=cart.toString();
        }
        else if(yrb[0].equals("cash")){
        String cash=String.valueOf(marketserver.cashFunc(yrb[1]));
     
          send=cash;
        }
        else if(yrb[0].equals("items")){
            System.out.println("da5lt");
            String items = String.valueOf(marketserver.show_items(yrb[1]));
            send = items;

        }
        }
       
       

       
  
//        if(send.equals("bye")){
//        System.exit(0);
//        }
 pw.println(send);
 pw.close();
        }
//        System.out.println(send);
//        pw.println(send);
    }
        catch(Exception e){}
    
    

//    public ServerThread(Socket socket) {
//    }
    
}}
///////////////////////////////////////////////////////////////FinalServer
class Finalserver {
   public ServerSocket ss;
    public Socket socket;
    public DataInputStream in;
    public DataOutputStream out;
    public BufferedReader br;
    /**
     * @param args the command line arguments
     */
    
    public Finalserver() throws IOException{
      
       ss=new ServerSocket(333) ;
       
        System.out.println("waiting for a client");
        
        while (true) {
            socket=ss.accept();
            System.out.println("connected");
            new ServerThread(socket).start();
    
    
    }}
    
    
    
}

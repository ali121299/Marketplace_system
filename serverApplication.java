package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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

public class serverApplication extends Application {

    String Username;
    String Password;
    String DB ;
    String DB_password;
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
    public ArrayList<String> Names(){
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
    public ArrayList<Float> cash(){
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
    public double no_of_clients(){
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
    public String loginValidation(String st){
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
    public void signUp_handler(String s){
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
    public ArrayList<String> parsing(String s){
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
    public Image icon(){
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
    public static void main(String[] args) {
    launch();
}
}

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

public class serverApplication extends Application {
    String Username;
    String Password;
    String DB ;
    String DB_password;
    @Override
    public void start(Stage stage) throws IOException {

    }
    public String loginValidation(){
        String s="";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+DB, "root",DB_password ); 
            Statement stmt = con.createStatement();
            ResultSet names = stmt.executeQuery("select Username from Login_Signup");
            boolean exist=false;
            while(names.next()){
                if((names.getString(1).compareToIgnoreCase(Username))==0) exist=true;
            }
            if(!exist){
                s="non existing username!";
                return s;
            }
            ResultSet rs = stmt.executeQuery("select Password from Login_Signup where Username=\""+Username+"\"");
            boolean pass_exist=false;
            String pass="";
            while (rs.next()) {
                pass=rs.getString(1);
                pass_exist=true;
            }
            if(pass_exist &&(pass.compareToIgnoreCase(Password)==0)){                
                s="successful login";
            }
            else s="wrong password!";
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        } 
        
        return s;
  
    }
    public static void main(String[] args) {
    launch();
}
}

package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;

public class serverApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

    }
    String DB;
    String DB_passward;
    public float cash(String user_name) {
        ArrayList<float> temp = new ArrayList<float>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB,"root",DB_password);
            Statement stmt = con.createStatement();
            String text= "SELECT Current_balance FROM account,login_signup WHERE account.username =  login_signup.username AND account.username=\""+user_name+"\"";
            ResultSet rs = stmt.executeQuery(text);
            while (rs.next()) {
                temp.add(rs.getString(1));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return items[0];
    }

    public ArrayList<String> history(String user_name) {
        ArrayList<String> temp = new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB,"root",DB_password);
            Statement stmt = con.createStatement();
            String text= "SELECT Name,price FROM account,orderspecs,items,orderitems WHERE account.Username =  orderspecs.Client_name " +
                    "AND orderspecs.OID = orderitems.OID AND account.Username = \""+user_name+"\" AND orderitems.item_name = items.Name " +
                    ;
            ResultSet rs = stmt.executeQuery(text);
            while (rs.next()) {
                String tempstr = "";
                for(int i = 1; i < 3; i++) {
                    tempstr += rs.getString(i) + ":";
                }
                temp.add(rs.getString(i));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return temp;
    }

    public ArrayList<String> accountInfo(String user_name) {
        ArrayList<String> temp = new ArrayList<String>();
        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/" + DB,"root",DB_password);
//            Statement stmt = con.createStatement();
//            String text= "SELECT Name,price FROM account,orderspecs,items,orderitems WHERE account.Username =  orderspecs.Client_name " +
//                    "AND orderspecs.OID = orderitems.OID AND account.Username = \"" +user_name+"\" AND orderitems.item_name = items.Name " +
//                    ;
//            ResultSet rs = stmt.executeQuery(text);
//            while (rs.next()) {
//                String tempstr = "";
//                for(int i = 1; i < 3; i++) {
//                    tempstr += rs.getString(i) + ":";
//                }
//                temp.add(rs.getString(i));
//            }
//            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return temp;
    }

    public static void main(String[] args) {
        launch();
    }
}

package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.util.ArrayList;

public class serverApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

    }

    public float cash(String user_name) {
        float temp=0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB,"root",DB_password);
            Statement stmt = con.createStatement();
            String text= "SELECT Current_balance FROM account WHERE account.Username=\""+user_name+"\"";
            ResultSet rs = stmt.executeQuery(text);
            while (rs.next()) {
                temp = (Float.valueOf(rs.getString(1)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return temp;
    }

    public ArrayList<String> history(String user_name) {
        ArrayList<String> temp = new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB,"root",DB_password);
            Statement stmt = con.createStatement();
            String text= "SELECT Name,price,purchase_date FROM account,orderspecs,items,orderitems WHERE account.Username =  orderspecs.Client_name " +
                    "AND orderspecs.OID = orderitems.OID AND account.Username = \""+user_name+"\" AND orderitems.Item_name = items.Name "
                    ;
            ResultSet rs = stmt.executeQuery(text);
            while (rs.next()) {
                String tempstr = "";
                for(int i = 1; i < 4; i++) {
                    tempstr += rs.getString(i) + ":";
                }
                temp.add(tempstr);
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
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB,"root",DB_password);
            Statement stmt = con.createStatement();
            String text= "SELECT Username,Password,mail,birthday,telephone From login_signup where login_signup.Username = \"" +user_name+"\""
                    ;
            ResultSet rs = stmt.executeQuery(text);
            while (rs.next()) {
                String tempstr = "";
                for(int i = 1; i < 6; i++) {
                    tempstr += rs.getString(i) + ":";
                }
                temp.add(tempstr);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return temp;
    }

    public void deposit(String user_name, float amount) {
        float temp = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB,"root",DB_password);
            Statement stmt = con.createStatement();
            String text= "SELECT Current_balance FROM account WHERE account.Username=\""+user_name+"\"";
            ResultSet rs = stmt.executeQuery(text);
            while (rs.next()) {
                temp = (Float.parseFloat(rs.getString(1)));
            }
            float newcash = temp+amount;
            Statement stmt1 = con.createStatement();
            String text1= "UPDATE  account SET Current_balance = \""+newcash+"\"  WHERE  account.Username=\""+user_name+"\"";
            stmt1.executeUpdate(text1);

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        launch();
    }
}

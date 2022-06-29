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

    public  String cashFunc(String user_name)  {
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

    public ArrayList<String> history(String user_name) {
        ArrayList<String> temp = new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB,"root",DB_password);
            Statement stmt = con.createStatement();
            String text1= "SELECT Name FROM account,orderspecs,items,orderitems WHERE account.Username =  orderspecs.Client_name " +
                    "AND orderspecs.OID = orderitems.OID AND account.Username = \""+user_name+"\" AND orderitems.Item_name = items.Name "
                    ;
            String text2= "SELECT price FROM account,orderspecs,items,orderitems WHERE account.Username =  orderspecs.Client_name " +
                    "AND orderspecs.OID = orderitems.OID AND account.Username = \""+user_name+"\" AND orderitems.Item_name = items.Name ";
            String text3= "SELECT date_time FROM account,orderspecs,items,orderitems WHERE account.Username =  orderspecs.Client_name " +
                    "AND orderspecs.OID = orderitems.OID AND account.Username = \""+user_name+"\" AND orderitems.Item_name = items.Name ";

            ResultSet rs1 = stmt.executeQuery(text1);
            ResultSet rs2 = stmt.executeQuery(text2);
            ResultSet rs3 = stmt.executeQuery(text3);
            while (rs1.next()) {
                String tempstr = "";
                tempstr += rs1.getString(1) + ":"+String.valueOf(rs2.getFloat(1))+":"+String.valueOf(rs3.getDate(1));
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
            String text1= "SELECT Username From login_signup where login_signup.Username = \"" +user_name+"\""
                    ;
            String text2= "SELECT Password From login_signup where login_signup.Username = \"" +user_name+"\""
                    ;
            String text3= "SELECT mail From login_signup where login_signup.Username = \"" +user_name+"\""
                    ;
            String text4= "SELECT birthday From login_signup where login_signup.Username = \"" +user_name+"\""
                    ;
            String text5= "SELECT telephone From login_signup where login_signup.Username = \"" +user_name+"\""
                    ;
            ResultSet rs1 = stmt.executeQuery(text1);
            ResultSet rs2 = stmt.executeQuery(text2);
            ResultSet rs3 = stmt.executeQuery(text3);
            ResultSet rs4 = stmt.executeQuery(text4);
            ResultSet rs5 = stmt.executeQuery(text5);
            while (rs1.next()) {
                String tempstr = "";
                tempstr += rs1.getString(1) + ":"+rs2.getString(1) +":"+rs3.getString(1)+":"+String.valueOf(rs4.getDate(1))
                +":"+String.valueOf(rs5.getInt(1));
                temp.add(tempstr);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return temp;
    }

    public void deposit(String par) {
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

    public static void main(String[] args) {
        launch();
    }
}

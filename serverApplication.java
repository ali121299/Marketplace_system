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
import java.util.ArrayList;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class serverApplication extends Application {
    TextField search = new TextField();
    String Username = "batoul";
    String DB = "onlineMarket";
    String pwd = "56566565";
    GridPane grid = new GridPane();
    GridPane root = new GridPane();
    TextField qty = new TextField();
    @Override
    public void start(Stage stage) throws IOException {

    }
    
    private String[] name_cat(String text) {
        String[] s = text.split(",");
        return s;
    }
    public ArrayList<String> search(String text) {
        ArrayList<String> items = new ArrayList<String>();
        String[] s = name_cat(text);
        String item_name = s[0];
        String category_name = s[1];
        System.out.println(item_name + " " + category_name);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", pwd);
//here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM items WHERE ( Name LIKE'%" + item_name + "%') AND (category LIKE'%" + category_name + "%')";
            //from https://stackoverflow.com/questions/5373921/adding-a-variable-into-a-sql-statement-in-java
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                items.add(rs.getString(1));
            }
            //System.out.println("bassanty");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return items;
    }

    public static void main(String[] args) {
    launch();
}
    
    
}

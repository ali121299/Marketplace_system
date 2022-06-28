package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

public class serverApplication extends Application {

    TextField search = new TextField();
    String Username = "batoul";
    String DB = "onlineMarket";
    String pwd = "56566565";
    GridPane grid = new GridPane();
    GridPane root = new GridPane();
    TextField qty = new TextField();

    class item_qty {

        String name;
        String qty;

        public item_qty(String n, String q) {
            name = n;
            qty = q;
        }
    }

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
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

    public void purchase(ArrayList<item_qty> items, String Username) {
        float total_price = 0;
        //get total price
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).name + " " + items.get(i).qty);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + DB, "root", pwd);
//here sonoo is database name, root is username and password  
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select Price from items Where Name = '" + items.get(i).name + "'");
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                    total_price = total_price + (Float.parseFloat(rs.getString(1)) * Float.parseFloat(items.get(i).qty));
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        //check balance
        float cash = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", pwd);
//here sonoo is database name, root is username and password  
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
        //compare balance with total price
        System.out.println(cash + " " + total_price);
        if (cash < total_price) {
            System.out.println("No Enough Balance");
            //call pop up screen
            return;
        }
        //add order to orderspecs

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", pwd);
            PreparedStatement stmt = con.prepareStatement("insert into Orderspecs values(?,?,?,?)");
            stmt.setInt(1, 109);
            stmt.setString(2, Username);
            stmt.setFloat(3, (float) total_price);
            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            stmt.setTimestamp(4, date);
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        //get OID to use it in Ordered items
        int OID = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", pwd);
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
        //fill ordereditems
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", pwd);
            for (int i = 0; i < items.size(); i++) {
                System.out.println(items.get(i).name + " " + items.get(i).qty);
                PreparedStatement stmt = con.prepareStatement("insert into Orderitems values(?,?,?)");
                stmt.setInt(1, OID);
                stmt.setString(2, items.get(i).name);
                stmt.setFloat(3, Float.parseFloat(items.get(i).qty));
                stmt.executeUpdate();
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        //decrease client cash using update
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", pwd);
//here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("UPDATE cart SET amount = " + qty + "WHERE (item_name = '" + item_name + "') AND (Username= '" + Username + "')");

            String query = "UPDATE Account SET Current_balance = ? WHERE (Username= ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setFloat(1, cash - total_price);
            preparedStmt.setString(2, Username);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            System.out.println("bassanty");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        //remove items from client cart
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", pwd);
            Statement stmt = con.createStatement();
            String sql = "DELETE FROM cart  WHERE Username = '" + Username + '"';
            stmt.executeUpdate(sql);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void edit(int qty, String item_name, String Username) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", pwd);
//here sonoo is database name, root is username and password  
            Statement stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("UPDATE cart SET amount = " + qty + "WHERE (item_name = '" + item_name + "') AND (Username= '" + Username + "')");

            String query = "UPDATE cart SET amount = ? WHERE (item_name = ?) AND (Username= ?)";
            //String query = "update users set num_points = ? where first_name = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, qty);
            preparedStmt.setString(2, item_name);
            preparedStmt.setString(3, Username);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            System.out.println("bassanty");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //use search to get the items in cart of certain Username
    public ArrayList<item_qty> search(String Username) {
        ArrayList<item_qty> items = new ArrayList<item_qty>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", pwd);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from cart WHERE Username = '" + Username + "'");

            while (rs.next()) {
                items.add(new item_qty(rs.getString(1), rs.getString(3)));
            }
            System.out.println("bassanty");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return items;
    }

    //search for certain item in certain category
    public ArrayList<String> searchForItem(String text) {
        ArrayList<String> items = new ArrayList<String>();
        String[] s = name_cat(text);
        String item_name = s[0];
        String category_name = s[1];
        System.out.println(item_name + " " + category_name);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", pwd);
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM items WHERE ( Name LIKE'%" + item_name + "%') AND (category LIKE'%" + category_name + "%')";
            //from https://stackoverflow.com/questions/5373921/adding-a-variable-into-a-sql-statement-in-java
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

    private String[] name_cat(String text) {
        String[] s = text.split(",");
        return s;
    }

    //add item to user cart
    public void addToCart(String item_name, int qty) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", pwd);
            Statement stmt = con.createStatement();
            System.out.println("Inserting records into cart table...");
            String sql = "INSERT INTO cart  VALUES ('" + item_name + "', '" + Username + "' , " + qty + ")";
            stmt.executeUpdate(sql);
            System.out.println("bassanty");
            con.close();
        } catch (Exception eee) {
            System.out.println(eee);
        }

    }

    //rmove item from user cart
        public void remove(String item_name) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", pwd);  
            Statement stmt = con.createStatement();
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

    }

    public static void main(String[] args) {
        launch();
    }

}

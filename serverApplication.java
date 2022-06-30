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
    String DB = "Marketplace_System";
    String DB_password= "56566565";
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

    public int purchase(String message) {
        String[] arrOfStr = message.split(",");
        ArrayList<String> s = new ArrayList<String>();
        String name = "";
        for (int i = 0; i < arrOfStr.length-1; i++) {
            //System.out.println(arrOfStr[i]);
            if (i % 2 == 0) {
                name = arrOfStr[i];
            } else {
                name = name+","+ arrOfStr[i];
                s.add(name);
                name ="";

            }
        }
        return purchase(s, arrOfStr[arrOfStr.length - 1]);
    }

        public int purchase(ArrayList<String> items, String Username) {
        float total_price = 0;
        //get total price
        for (int i = 0; i < items.size(); i++) {
            String[] a = items.get(i).split(",");
            System.out.println(a[0] + " " +   Float.parseFloat(a[1]));
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select Price from items Where Name = '" + a[0]+ "'");
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                    total_price = total_price + (Float.parseFloat(rs.getString(1)) * Float.parseFloat(a[1]));
                }
                con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("finish");
        }
        //check balance
        float cash = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
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
                String[] a = items.get(i).split(",");
                System.out.println(a[0] + " " +  Float.parseFloat(a[1]));
                PreparedStatement stmt = con.prepareStatement("insert into Orderitems values(?,?,?)");
                stmt.setInt(1, OID);
                stmt.setString(2, a[0]);
                stmt.setFloat(3, Float.parseFloat(a[1]));
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
        float new_cash = 0;
        float old_cash = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + DB, "root", DB_password);
            Statement stmt = con.createStatement();
            ResultSet r = stmt.executeQuery("select cash from Market WHERE id = 1");
            while (r.next()) {
                old_cash = r.getInt(1);
            }
            new_cash = new_cash + old_cash;
            String query = "UPDATE Market SET cash ="+ new_cash + "WHERE id = 1";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("finish market");
        return 1;
    }

    public int edit(String message) {
        String[] arrOfStr = message.split(",");
        int qty = Integer.parseInt(arrOfStr[0]);
        String item_name = arrOfStr[1];
        String Username = arrOfStr[2];
        System.out.println(qty + " " + item_name+" " + Username);
        return edit(qty, item_name, Username);
    }
    //edit cart
    public int edit(int qty, String item_name,String Username) {
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
    public ArrayList<String> search(String Username) {
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
    public ArrayList<String> searchForItem(String text) {
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

    public int addTocart(String message) {
        String[] arrOfStr = message.split(",");
        int qty = Integer.parseInt(arrOfStr[1]);
        String item_name = arrOfStr[0];
        return addToCart(item_name, qty);
    }

    //add item to user cart
    public int addToCart(String item_name, int qty) {
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

    public void remove(String message) {
        String[] arrOfStr = message.split(",");
        String item_name = arrOfStr[0];
        int qty = Integer.parseInt(arrOfStr[1]);
        String Username = arrOfStr[2];
        remove(item_name, qty, Username);
    }

    //remove item from user cart
    public void remove(String item_name, int qty, String Username) {
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

    }

    public static void main(String[] args) {
        launch();
    }

}

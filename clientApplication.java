package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class clientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

    }

    public static void main(String[] args) {
        launch();
    }
    static void insert (){
 try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                   "jdbc:mysql://localhost:3306/" + "marketplace_System", "root", "01119023565");
           PreparedStatement stmt = con.prepareStatement("insert into login_signup values(\"please\",14)");
           stmt.executeUpdate();
           con.close();
        } catch (Exception e) {
            System.out.println(e);
        }


    }
}

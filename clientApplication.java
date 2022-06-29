package com.example.demo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.util.ArrayList;

public class clientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

    }
    public String removebrackets(String s){
        return s.substring(1,s.length()-2);
    }
    public ArrayList<String> parsing2(String s){
        ArrayList<String>r=new ArrayList<String>();
        int init=0;
        for (int i=0;i<s.length();i++){
            if((s.charAt(i)==':')) {

                r.add(s.substring(init,i));
                init=i+1;
            }
        }
        r.add(s.substring(init));
        return r;
    }
    public void home_display()  {
        Stage window = new Stage();
        //window.getIcons().add(icon());
        window.initModality(Modality.APPLICATION_MODAL);
        StackPane layout = new StackPane();
        VBox vBox = new VBox();
        window.setTitle("Home");
        vBox.setSpacing(6);
        Button history = new Button("History");
        Button cash = new Button("Cash");
        Button deposit = new Button("Deposit");
        Button c = new Button("Cart");
//        Button search = new Button("Search");
        Button s = new Button("Go");
        Button accountInfo = new Button("Account Information");
        Button logOut = new Button("Log Out");
//vBox.getChildren().addAll(history, cash, c, search,s, accountInfo, logOut);
        vBox.getChildren().addAll(history, cash, c, search,s, accountInfo,deposit,logOut);
        //Username

        layout.getChildren().add(vBox);


        Scene scene = new Scene(layout, 400, 400);

        window.setScene(scene);
        cash.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    cash_display(cash_server());
                } catch (Exception er1) {
                    er1.printStackTrace();

                }
            }

        }
        ));
        history.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    history_display(history_server());
                } catch (Exception er1) {
                    er1.printStackTrace();

                }
            }

        }
        ));

        accountInfo.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    account_display(account_server());
                } catch (Exception er1) {
                    er1.printStackTrace();
                }
            }

        }
        ));


        deposit.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    deposit_dislay();
                } catch (Exception er1) {
                    er1.printStackTrace();
                }
            }

        }
        ));

        EventHandler<javafx.scene.input.MouseEvent> logout
                = new EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                first_page();
            }
        };
        logOut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, logout);
        //cart event handler
        EventHandler<javafx.scene.input.MouseEvent> cart
                = new EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                cartScreen();
            }
        };
        c.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, cart);

        EventHandler<javafx.scene.input.MouseEvent> searching
                = new EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                search(search.getText());
            }
        };
        s.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, searching);
        window.showAndWait();

    }

    public  void cash_display(String s) {
        Stage window = new Stage();
        window.getIcons().add(icon());
        window.initModality(Modality.APPLICATION_MODAL);
        BorderPane layout = new BorderPane();
        Scene scene;
        window.setTitle("Cash");
        Text t = new Text(s);
        layout.setCenter(t);
        scene = new Scene(layout, 400, 400);

        window.setScene(scene);
        window.showAndWait();
    }

    public  void history_display(String s) {
        Stage window = new Stage();
        window.getIcons().add(icon());
        window.initModality(Modality.APPLICATION_MODAL);
        ScrollPane layout = new ScrollPane();
        window.setTitle("History");
        Text t = new Text(s);
        layout.setContent(t);

        Scene scene = new Scene(layout, 400, 400);
        window.setScene(scene);
        window.showAndWait();

    }

    public  void account_display(String s) {
        Stage window = new Stage();
        window.getIcons().add(icon());
        window.initModality(Modality.APPLICATION_MODAL);
        ScrollPane layout = new ScrollPane();
        window.setTitle("Account Information");
        Text t = new Text(s);
        layout.setContent(t);

        Scene scene = new Scene(layout, 400, 400);
        window.setScene(scene);
        window.showAndWait();

    }
    public  void deposit_dislay() {
        Stage window = new Stage();
        window.getIcons().add(icon());
        window.initModality(Modality.APPLICATION_MODAL);
        StackPane layout = new StackPane();
        VBox vBox = new VBox();
        vBox.setSpacing(6);
        TextField t = new TextField("amount");
        Button deposit = new Button("Deosit");
        vBox.getChildren().addAll(t, deposit);
        layout.getChildren().add(vBox);
        Scene scene;
        window.setTitle("Depisit");


        scene = new Scene(layout, 400, 400);

        window.setScene(scene);
        deposit.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    try {
                        float floatValue = Float.parseFloat(t.getText());
                        deposit_server(floatValue);
                        valid_deposit_display();
                    } catch (NumberFormatException e) {
                        invalid_deposit_display();
                    }

                } catch (Exception er1) {
                    er1.printStackTrace();
                }
            }

        }
        ));
        window.showAndWait();
    }
    public  void valid_deposit_display() {

        Stage window = new Stage();
        window.getIcons().add(icon());
        window.initModality(Modality.APPLICATION_MODAL);
        BorderPane layout = new BorderPane();
        Scene scene;
        window.setTitle("Valid Deposit");
        Text t = new Text("Valid Deposit");
        layout.setCenter(t);
        scene = new Scene(layout, 400, 400);

        window.setScene(scene);
        window.showAndWait();
    }
    public  void invalid_deposit_display() {
        Stage window = new Stage();
        window.getIcons().add(icon());
        window.initModality(Modality.APPLICATION_MODAL);
        BorderPane layout = new BorderPane();
        Scene scene;
        window.setTitle("Invalid Deposit");
        Text t = new Text("InValid Deposit");
        layout.setCenter(t);
        scene = new Scene(layout, 400, 400);

        window.setScene(scene);
        window.showAndWait();
    }
    public String cash_server(){
        String responce = new String();
        responce = request1("cash",Username);
        return responce;
    }

    public String history_server(){
        String result = new String("Item name:price:date\n");
        ArrayList<String> responce = new ArrayList<String>();
        String s = request1("history",Username);
        String s1 = removebrackets(s);
        responce = parsing(s1);
        int history_items = responce.size();
        for(int i = 0; i < history_items; i++){
            result+=responce.get(i)+"\n";
        }
        return result;
    }
    public String account_server(){
        String result = new String();
        ArrayList<String> responce = new ArrayList<String>();
        String s = request1("account",Username);
        String s1 = removebrackets(s);
        responce = parsing(s1);
        int account_items = responce.size();
        for(int i = 0; i < account_items; i++){
            result+="Username: "+ parsing2(responce.get(i)).get(0) +"\n";
            result+="Password: "+parsing2(responce.get(i)).get(1)+"\n";
            result+="Mail: "+parsing2(responce.get(i)).get(2)+"\n";
            result+="Birthday: "+parsing2(responce.get(i)).get(3)+"\n";
            result+="Telephone: "+parsing2(responce.get(i)).get(4)+"\n";

        }
        return result;
    }
    public void deposit_server(float amount){
        String send = new String(Username+","+String.valueOf(amount));
        requestvoid("deposit",send);

    }


    public static void main(String[] args) {
        launch();
    }
}

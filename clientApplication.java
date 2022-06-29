package com.example.demo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
        window.getIcons().add(icon());
        window.initModality(Modality.APPLICATION_MODAL);
        StackPane layout = new StackPane();
        VBox vBox = new VBox();
        window.setTitle("Home");
        vBox.setSpacing(6);
        Button history = new Button("History");
        Button cash = new Button("Cash");
        Button deposit = new Button("Deposit");
        Button cart = new Button("Cart");
        Button search = new Button("Search");
        Button accountInfo = new Button("Account Information");
        Button logOut = new Button("Log Out");

        vBox.getChildren().addAll(history, cash, cart, search, accountInfo,deposit,logOut);
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

        cart.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {

                } catch (Exception er1) {
                    er1.printStackTrace();
                }
            }

        }
        ));

        search.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    
                } catch (Exception er1) {
                    er1.printStackTrace();
                }
            }

        }
        ));
        logOut.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    first_page();
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

        return responce;
    }

    public String history_server(){
        String result = new String("Item name:price:date\n");
        ArrayList<String> responce = new ArrayList<String>();
        int history_items = responce.size();
        for(int i = 0; i < history_items; i++){
            result+=responce[i]+"\n";
        }
        return result;
    }
    public String account_server(){
        String result = new String();
        ArrayList<String> responce = new ArrayList<String>();
        int account_items = responce.size();
        for(int i = 0; i < account_items; i++){
            result+="Username: "+parsing2(responce[i])[0]+"\n";
            result+="Password: "+parsing2(responce[i])[1]+"\n";
            result+="Mail: "+parsing2(responce[i])[2]+"\n";
            result+="Birthday: "+parsing2(responce[i])[3]+"\n";
            result+="Telephone: "+parsing2(responce[i])[4]+"\n";

        }
        return result;
    }
    public void deposit_server(float amount){


    }



    public static void main(String[] args) {
        launch();
    }
}

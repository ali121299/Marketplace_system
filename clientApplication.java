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

import java.io.IOException;

public class clientApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

    }
    public void home_display()  {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        StackPane layout = new StackPane();
        VBox vBox = new VBox();
        vBox.setSpacing(6);
        Button history = new Button("History");
        Button cash = new Button("Cash");
        Button cart = new Button("Cart");
        Button search = new Button("Search");
        Button accountInfo = new Button("Account Information");
        Button logOut = new Button("Log Out");

        vBox.getChildren().addAll(history, cash, cart, search, accountInfo,logOut);


        layout.getChildren().add(vBox);


        Scene scene = new Scene(layout, 400, 400);

        window.setScene(scene);
        cash.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    cash_display(history_server());
                } catch (Exception er1) {
                    er1.printStackTrace();

                }
            }

        }
        ));
        history.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    history_display(cash_server());
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
        window.initModality(Modality.APPLICATION_MODAL);
        ScrollPane layout = new ScrollPane();
        window.setTitle("Account Information");
        Text t = new Text(s);
        layout.setContent(t);

        Scene scene = new Scene(layout, 400, 400);
        window.setScene(scene);
        window.showAndWait();

    }


    public String cash_server(){
        String responce = new String();

        return responce;
    }

    public String history_server(){
        String responce = new String();

        return responce;
    }
    public String account_server(){
        String responce = new String();

        return responce;
    }


    public static void main(String[] args) {
        launch();
    }
}

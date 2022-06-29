package com.example.demo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
<<<<<<< HEAD
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
=======
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
>>>>>>> 23aefd29ecbe4c960d5f91c0c40401ef4c6df5a6
import javafx.stage.Stage;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
<<<<<<< HEAD
import java.time.LocalDate;
=======
>>>>>>> 23aefd29ecbe4c960d5f91c0c40401ef4c6df5a6
import java.util.ArrayList;

public class clientApplication extends Application {
    
    String Username;
    String Password;
    String mail;
    LocalDate birth;
    int Phone;
    
    @Override
    public void start(Stage stage) throws IOException {
        first_page();

    }
<<<<<<< HEAD
    public void first_page(){
        Stage stage=new Stage();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        Button login=new Button("Login"); 
        login.setOnMouseClicked((new EventHandler <MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                loginScreen();
            }            
        }));
        
        Button signUp=new Button("Sign up");
        signUp.setOnMouseClicked((new EventHandler <MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                signUpScreen();
            }            
        }));
        vbox.setMargin(login, new Insets(5, 10, 10, 10));
        vbox.setMargin(signUp, new Insets(10, 10, 10, 10)); 
        Scene scene=new Scene(vbox,400,400);
        ImageView i=new ImageView(icon());
        i.setFitHeight(scene.getHeight()/3);
        i.setFitWidth(scene.getWidth()/3);
        vbox.setMargin(i, new Insets(10, 10, 10, 10)); 
        vbox.getChildren().addAll(i,login,signUp);
        stage.getIcons().add(icon());
        stage.setTitle("Market");
        stage.setScene(scene);
        stage.show();
    }
    public void invalidLogin(String msg) { 
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Failed login");
        alert.setHeaderText("Failed login");
        alert.setContentText(msg);        
        alert.show();
    }
    public void loginScreen(){
        Stage login =new Stage();
        VBox v=new VBox();
        v.setAlignment(Pos.CENTER);
        Scene s=new Scene(v,300,300);
        login.setScene(s);
        login.setTitle("Login screen");
        login.getIcons().add(icon());
        HBox h1=new HBox(7);
        h1.setAlignment(Pos.CENTER);
        Label username=new Label("Username");
        h1.setMargin(username,new Insets(10, 10, 10, 10));
        TextField user=new TextField();
        //Username=user.getText();
        h1.setMargin(user,new Insets(10, 10, 10, 10));
        h1.getChildren().addAll(username,user);
        HBox h2=new HBox(7);
        h2.setAlignment(Pos.CENTER);
        Label pass=new Label("Password");
        h2.setMargin(pass,new Insets(10, 10, 10, 10));
        TextField password=new TextField();
        //Password=password.getText();
        h2.setMargin(password,new Insets(10, 10, 10, 10));
        h2.getChildren().addAll(pass,password);
        Button Login=new Button("Login");        
        v.setMargin(Login,new Insets(10, 10, 10, 10));
        Login.setOnMouseClicked((new EventHandler <MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                Username=user.getText();
                Password=password.getText();                
                System.out.println(Username);
                System.out.println(Password);
                String st=Username+","+Password;
                String s=request1("login",st);
//                String s=loginValidation(st);
                if((s.compareToIgnoreCase("successful login"))==0) {
                    login.close();
                    home_display();
                    
                }
                else {
                    login.close();
                    invalidLogin(s);
                    
                }
            }            
        }));
        v.getChildren().addAll(h1,h2,Login);
        
        login.showAndWait();
    }
    
    public void signUpScreen(){
        Stage signUp=new Stage();
        VBox v=new VBox();
        v.setAlignment(Pos.CENTER);
        Scene s=new Scene(v,400,400);
        signUp.setScene(s);
        signUp.setTitle("Sign up screen");
        signUp.getIcons().add(icon());
        HBox h1=new HBox(7);
        h1.setAlignment(Pos.CENTER);
        Label email=new Label("     Email     ");
        h1.setMargin(email,new Insets(10, 10, 10, 10));
        TextField Email=new TextField();
        h1.setMargin(Email,new Insets(10, 10, 10, 10));
        h1.getChildren().addAll(email,Email);
        HBox h2=new HBox(7);
        h2.setAlignment(Pos.CENTER);
        Label username=new Label("Username ");
        h2.setMargin(username,new Insets(10, 10, 10, 10));
        TextField user=new TextField();
        h2.setMargin(user,new Insets(10, 10, 10, 10));
        h2.getChildren().addAll(username,user);
        HBox h3=new HBox(7);
        h3.setAlignment(Pos.CENTER);
        Label pass=new Label("Password  ");
        h3.setMargin(pass,new Insets(10, 10, 10, 10));
        TextField password=new TextField();
        h3.setMargin(password,new Insets(10, 10, 10, 10));
        h3.getChildren().addAll(pass,password);
        HBox h4=new HBox(7);
        h4.setAlignment(Pos.CENTER);
        Label birthday=new Label("Birthday   ");
        h4.setMargin(birthday,new Insets(10, 10, 10, 10));
        TextField Birthday=new TextField();
        h4.setMargin(Birthday,new Insets(10, 10, 10, 10));
        h4.getChildren().addAll(birthday,Birthday);
        HBox h5=new HBox(7);
        h5.setAlignment(Pos.CENTER);
        Label telephone=new Label("Telephone");
        h5.setMargin(telephone,new Insets(10, 10, 10, 10));
        TextField phone=new TextField();
        h5.setMargin(phone,new Insets(10, 10, 10, 10));
        h5.getChildren().addAll(telephone,phone);
        Button sign=new Button("Sign Up");
        sign.setOnMouseClicked((new EventHandler <MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                Username=user.getText();
                Password=password.getText();
                birth=LocalDate.parse(Birthday.getText());
                mail =Email.getText();
                Phone=Integer.parseInt(phone.getText());
                String s=Username+","+Password+","+mail+","+String.valueOf(birth)+","+String.valueOf(Phone);
                //signUp_handler(s);
                requestvoid("signup",s);
                signUp.close();
                //first_page();
            }            
        }));
        v.getChildren().addAll(h1,h2,h3,h4,h5,sign);
        signUp.showAndWait();
    }
    public Image icon(){
        String path="";
        try {
            String currentPath = new java.io.File(".").getCanonicalPath();

            System.out.println(currentPath);
            char c='/';
            for(int i=0;i<currentPath.length();i++){
                if(currentPath.charAt(i)=='/') {
                    c='/';
                    break;
                }
                else if(currentPath.charAt(i)=='\\'){
                c='\\';
            }
            }

          path=currentPath+c+"market.jpg";

        
        } catch (Exception e) {

        }
        Image logo=new Image(path);
        return logo;
        
    }
    public ArrayList<String> parsing(String s){
        ArrayList<String>r=new ArrayList<String>();
        int init=0;
        for (int i=0;i<s.length();i++){
            if((s.charAt(i)==',')) {
                
=======
    public ArrayList<String> parsing2(String s){
        ArrayList<String>r=new ArrayList<String>();
        int init=0;
        for (int i=0;i<s.length();i++){
            if((s.charAt(i)==':')) {

>>>>>>> 23aefd29ecbe4c960d5f91c0c40401ef4c6df5a6
                r.add(s.substring(init,i));
                init=i+1;
            }
        }
        r.add(s.substring(init));
        return r;
    }
<<<<<<< HEAD
=======
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

        return responce;
    }

    public String history_server(){
        String result = new String("Item name:price:date\n");
        ArrayList<String> responce = new ArrayList<String>();
        int history_items = responce.size();
        for(int i = 0; i < history_items; i++){
            result+=responce.get(i)+"\n";
        }
        return result;
    }
    public String account_server(){
        String result = new String();
        ArrayList<String> responce = new ArrayList<String>();
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
        String responce = new String();

    }

>>>>>>> 23aefd29ecbe4c960d5f91c0c40401ef4c6df5a6

    public static void main(String[] args) {
        launch();
    }
}

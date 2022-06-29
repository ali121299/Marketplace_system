package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

import java.io.IOException;

public class clientApplication extends Application {
    Image logo=new Image("C:\\Users\\DELL\\Documents\\NetBeansProjects\\market\\src\\market\\market.jpg");
    String Username;
    String Password;
    
    @Override
    public void start(Stage stage) throws IOException {
        first_page();

    }
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
        ImageView i=new ImageView(logo);
        i.setFitHeight(scene.getHeight()/3);
        i.setFitWidth(scene.getWidth()/3);
        vbox.setMargin(i, new Insets(10, 10, 10, 10)); 
        vbox.getChildren().addAll(i,login,signUp);
        stage.getIcons().add(logo);
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
        Scene s=new Scene(v,300,300);
        signUp.setScene(s);
        signUp.setTitle("Sign up screen");
        signUp.getIcons().add(logo);
        HBox h1=new HBox(7);
        h1.setAlignment(Pos.CENTER);
        Label email=new Label("     Email    ");
        h1.setMargin(email,new Insets(10, 10, 10, 10));
        TextField Email=new TextField();
        h1.setMargin(Email,new Insets(10, 10, 10, 10));
        h1.getChildren().addAll(email,Email);
        HBox h2=new HBox(7);
        h2.setAlignment(Pos.CENTER);
        Label username=new Label("Username");
        h2.setMargin(username,new Insets(10, 10, 10, 10));
        TextField user=new TextField();
        h2.setMargin(user,new Insets(10, 10, 10, 10));
        h2.getChildren().addAll(username,user);
        HBox h3=new HBox(7);
        h3.setAlignment(Pos.CENTER);
        Label pass=new Label("Password");
        h3.setMargin(pass,new Insets(10, 10, 10, 10));
        TextField password=new TextField();
        h3.setMargin(password,new Insets(10, 10, 10, 10));
        h3.getChildren().addAll(pass,password);
        HBox h4=new HBox(7);
        h4.setAlignment(Pos.CENTER);
        Label birthday=new Label("Birthday");
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

    public static void main(String[] args) {
        launch();
    }
}

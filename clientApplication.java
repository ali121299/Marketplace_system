package com.example.demo;

import javafx.application.Application;
import javafx.event.EventHandler;
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class clientApplication extends Application {

    TextField search = new TextField();
    String Username;
    GridPane grid = new GridPane();
    GridPane root = new GridPane();
    TextField qty = new TextField();
    String Password;
    String mail;
    LocalDate birth;
    int Phone;

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

    public void search(String item_name) {
        ArrayList<String> items = parsing(removebrackets(request1("search", item_name)));
        //ArrayList<String> items = searchForItem(item_name);
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i));
        }
        EventHandler<javafx.scene.input.MouseEvent> addcart
                = new EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                System.out.println("hii");
                System.out.println(GridPane.getRowIndex((Button) e.getSource()) + " " + GridPane.getColumnIndex((Button) e.getSource()));
                int row = GridPane.getRowIndex((Button) e.getSource());
                //int column = GridPane.getColumnIndex((Button) e.getSource());
                Node n = getNodeByRowColumnIndex(row, 0, root);
                Node q = getNodeByRowColumnIndex(row, 1, root);
                Label l = (Label) n;
                TextField qty = (TextField) q;
                System.out.println(l.getText() + " " + qty.getText());
                String name = l.getText();
                int amount = Integer.parseInt(qty.getText());
                System.out.println(name + " " + amount);
                String qi = name + " " + amount;
                String reply = request1("add2cart", qi);
                if (Integer.parseInt(reply) == -1) {
                    //pop up window
                }
                //addToCart(name, amount);
            }
        };
        System.out.println("here");
//        Label item = new Label("item");
//        Button add = new Button("Add To Cart");

        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        Scene scene = new Scene(root, 400, 200);
        //root.addRow(0, item, add);
        int i = 0;
        for (i = 0; i < items.size(); i++) {
            Label item = new Label(items.get(i));
            TextField q = new TextField("add quantity");
            Button add = new Button("Add To Cart");
            add.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, addcart);
            root.addRow(i, item, q, add);
        }
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("searched items");
        newWindow.setScene(scene);

        // Set position of second window, related to primary window.
//        newWindow.setX(primaryStage.getX() + 200);
//        newWindow.setY(primaryStage.getY() + 100);
        newWindow.showAndWait();
    }

    public void cartScreen() {
        String it = "";
        ArrayList<String> items = request1("search", Username);
        for (int i = 0; i < items.size(); i++) {
            String[] item = items.get(i).split(",");
            System.out.println(item[0] + " " + Float.parseFloat(item[1]));
            it = it + items.get(i) + ",";
        }
        it = it + Username;
        final String ite = it;
        EventHandler<javafx.scene.input.MouseEvent> purchas
                = new EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                request1("purchase", ite);

            }
        };
        EventHandler<javafx.scene.input.MouseEvent> remove
                = new EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                System.out.println("hiii");
                System.out.println(GridPane.getRowIndex((Button) e.getSource()) + " " + GridPane.getColumnIndex((Button) e.getSource()));
                int row = GridPane.getRowIndex((Button) e.getSource());
                int column = GridPane.getColumnIndex((Button) e.getSource());
                Node n = getNodeByRowColumnIndex(row, 0, grid);
                Node q = getNodeByRowColumnIndex(row, 1, grid);
                Label l = (Label) n;
                Button qty = (Button) q;
                System.out.println("names" + l.getText() + "," + qty.getText());
                System.out.println("here");
                requestvoid("remove", l.getText() + "," + (int) Float.parseFloat(qty.getText()) + "," + Username);
                //remove(l.getText()+","+(int) Float.parseFloat(qty.getText())+","+"batoul");
                System.out.println("here");
            }
        };
        EventHandler<javafx.scene.input.MouseEvent> edit
                = new EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                System.out.println(GridPane.getRowIndex((Button) e.getSource()) + " " + GridPane.getColumnIndex((Button) e.getSource()));
                int row = GridPane.getRowIndex((Button) e.getSource());
                int column = GridPane.getColumnIndex((Button) e.getSource());
                Node n = getNodeByRowColumnIndex(row, 0, grid);
                //Node q = getNodeByRowColumnIndex(row, 0, grid);
                Label l = (Label) n;
                System.out.println(l.getText());
                //edit(l.getText());
                editScreen(l.getText());

            }
        };
        Label item = new Label("items");
        Label quantity = new Label("quantity");
        Button purchase = new Button("purchase");
        purchase.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, purchas);
        Label item1 = new Label("item 1");
        Button q1 = new Button("2");
        Button rem1 = new Button("remove");
        rem1.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, purchas);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        Scene scene = new Scene(grid, 400, 200);
        grid.addRow(0, item, quantity);
        int i = 0;
        for (i = 0; i < items.size(); i++) {
            String[] nqty = items.get(i).split(",");
            System.out.println(nqty[0] + " " + Float.parseFloat(nqty[1]));
            Label l = new Label(nqty[0]);
            String amount = String.valueOf((int) Float.parseFloat(nqty[1]));
            Button q = new Button(amount);
            Button e = new Button("edit");
            Button r = new Button("remove");
            e.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, edit);
            r.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, remove);
            grid.addRow(i + 1, l, q, e, r);
        }
        //root.addRow(1, item1, q1, rem1);
        grid.addRow(i + 1, purchase);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Cart");
        newWindow.setScene(scene);

        // Set position of second window, related to primary window.
//        newWindow.setX(primaryStage.getX() + 200);
//        newWindow.setY(primaryStage.getY() + 100);
        newWindow.showAndWait();
    }

    public void editScreen(String item_name) {

        Label q = new Label("quantity");
        Button ok = new Button("OK");
        ok.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub  
                System.out.println(Integer.parseInt(qty.getText()) + "," + item_name + "," + "batoul");
                int reply = Integer.parseInt(request1(Integer.parseInt(qty.getText()) + "," + item_name + "," + Username));
                //edit(Integer.parseInt(qty.getText()) + "," + item_name+","+"batoul");
            }
        });
        VBox root = new VBox();
        root.getChildren().addAll(q, qty, ok);
        Scene scene = new Scene(root, 600, 400);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Cart");
        newWindow.setScene(scene);

        // Set position of second window, related to primary window.
//        newWindow.setX(primaryStage.getX() + 200);
//        newWindow.setY(primaryStage.getY() + 100);
        newWindow.showAndWait();
    }

    @Override
    public void start(Stage stage) throws IOException {
        first_page();

    }

    public void first_page() {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        Button login = new Button("Login");
        login.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                loginScreen();
            }
        }));

        Button signUp = new Button("Sign up");
        signUp.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                signUpScreen();
            }
        }));
        vbox.setMargin(login, new Insets(5, 10, 10, 10));
        vbox.setMargin(signUp, new Insets(10, 10, 10, 10));
        Scene scene = new Scene(vbox, 400, 400);
        ImageView i = new ImageView(icon());
        i.setFitHeight(scene.getHeight() / 3);
        i.setFitWidth(scene.getWidth() / 3);
        vbox.setMargin(i, new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(i, login, signUp);
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

    public void loginScreen() {
        Stage login = new Stage();
        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
        Scene s = new Scene(v, 300, 300);
        login.setScene(s);
        login.setTitle("Login screen");
        login.getIcons().add(icon());
        HBox h1 = new HBox(7);
        h1.setAlignment(Pos.CENTER);
        Label username = new Label("Username");
        h1.setMargin(username, new Insets(10, 10, 10, 10));
        TextField user = new TextField();
        //Username=user.getText();
        h1.setMargin(user, new Insets(10, 10, 10, 10));
        h1.getChildren().addAll(username, user);
        HBox h2 = new HBox(7);
        h2.setAlignment(Pos.CENTER);
        Label pass = new Label("Password");
        h2.setMargin(pass, new Insets(10, 10, 10, 10));
        TextField password = new TextField();
        //Password=password.getText();
        h2.setMargin(password, new Insets(10, 10, 10, 10));
        h2.getChildren().addAll(pass, password);
        Button Login = new Button("Login");
        v.setMargin(Login, new Insets(10, 10, 10, 10));
        Login.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Username = user.getText();
                Password = password.getText();
                System.out.println(Username);
                System.out.println(Password);
                String st = Username + "," + Password;
                String s = request1("login", st);
//                String s=loginValidation(st);
                if ((s.compareToIgnoreCase("successful login")) == 0) {
                    login.close();
                    home_display();

                } else {
                    login.close();
                    invalidLogin(s);

                }
            }
        }));
        v.getChildren().addAll(h1, h2, Login);

        login.showAndWait();
    }

    public void signUpScreen() {
        Stage signUp = new Stage();
        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
        Scene s = new Scene(v, 400, 400);
        signUp.setScene(s);
        signUp.setTitle("Sign up screen");
        signUp.getIcons().add(icon());
        HBox h1 = new HBox(7);
        h1.setAlignment(Pos.CENTER);
        Label email = new Label("     Email     ");
        h1.setMargin(email, new Insets(10, 10, 10, 10));
        TextField Email = new TextField();
        h1.setMargin(Email, new Insets(10, 10, 10, 10));
        h1.getChildren().addAll(email, Email);
        HBox h2 = new HBox(7);
        h2.setAlignment(Pos.CENTER);
        Label username = new Label("Username ");
        h2.setMargin(username, new Insets(10, 10, 10, 10));
        TextField user = new TextField();
        h2.setMargin(user, new Insets(10, 10, 10, 10));
        h2.getChildren().addAll(username, user);
        HBox h3 = new HBox(7);
        h3.setAlignment(Pos.CENTER);
        Label pass = new Label("Password  ");
        h3.setMargin(pass, new Insets(10, 10, 10, 10));
        TextField password = new TextField();
        h3.setMargin(password, new Insets(10, 10, 10, 10));
        h3.getChildren().addAll(pass, password);
        HBox h4 = new HBox(7);
        h4.setAlignment(Pos.CENTER);
        Label birthday = new Label("Birthday   ");
        h4.setMargin(birthday, new Insets(10, 10, 10, 10));
        TextField Birthday = new TextField();
        h4.setMargin(Birthday, new Insets(10, 10, 10, 10));
        h4.getChildren().addAll(birthday, Birthday);
        HBox h5 = new HBox(7);
        h5.setAlignment(Pos.CENTER);
        Label telephone = new Label("Telephone");
        h5.setMargin(telephone, new Insets(10, 10, 10, 10));
        TextField phone = new TextField();
        h5.setMargin(phone, new Insets(10, 10, 10, 10));
        h5.getChildren().addAll(telephone, phone);
        Button sign = new Button("Sign Up");
        sign.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Username = user.getText();
                Password = password.getText();
                birth = LocalDate.parse(Birthday.getText());
                mail = Email.getText();
                Phone = Integer.parseInt(phone.getText());
                String s = Username + "," + Password + "," + mail + "," + String.valueOf(birth) + "," + String.valueOf(Phone);
                //signUp_handler(s);
                requestvoid("signup", s);
                signUp.close();
                //first_page();
            }
        }));
        v.getChildren().addAll(h1, h2, h3, h4, h5, sign);
        signUp.showAndWait();
    }

    public Image icon() {
        String path = "";
        try {
            String currentPath = new java.io.File(".").getCanonicalPath();

            System.out.println(currentPath);
            char c = '/';
            for (int i = 0; i < currentPath.length(); i++) {
                if (currentPath.charAt(i) == '/') {
                    c = '/';
                    break;
                } else if (currentPath.charAt(i) == '\\') {
                    c = '\\';
                }
            }

            path = currentPath + c + "market.jpg";

        } catch (Exception e) {

        }
        Image logo = new Image(path);
        return logo;

    }

    public ArrayList<String> parsing(String s) {
        ArrayList<String> r = new ArrayList<String>();
        int init = 0;
        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) == ',')) {

                r.add(s.substring(init, i));
                init = i + 1;
            }
        }
        r.add(s.substring(init));
        return r;
    }

    public ArrayList<String> parsing2(String s) {
        ArrayList<String> r = new ArrayList<String>();
        int init = 0;
        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) == ':')) {

                r.add(s.substring(init, i));
                init = i + 1;
            }
        }
        r.add(s.substring(init));
        return r;
    }

    public void home_display() {
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
        vBox.getChildren().addAll(history, cash, c, search, s, accountInfo, deposit, logOut);
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

        }));
        history.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    history_display(history_server());
                } catch (Exception er1) {
                    er1.printStackTrace();

                }
            }

        }));

        accountInfo.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    account_display(account_server());
                } catch (Exception er1) {
                    er1.printStackTrace();
                }
            }

        }));

        deposit.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    deposit_dislay();
                } catch (Exception er1) {
                    er1.printStackTrace();
                }
            }

        }));

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

    public void cash_display(String s) {
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

    public void history_display(String s) {
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

    public void account_display(String s) {
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

    public void deposit_dislay() {
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

        }));
        window.showAndWait();
    }

    public void valid_deposit_display() {

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

    public void invalid_deposit_display() {
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

    public String removebrackets(String s) {
        return s.substring(1, s.length() - 2);
    }

    public String cash_server() {
        String responce = request1("cash", Username);

        return responce;
    }

    public String history_server() {
        String result = new String("Item name:price:date\n");
        ArrayList<String> responce = new ArrayList<String>();
        String s = request1("history", Username);
        String s2 = removebrackets(s);
        responce = parsing(s2);

        int history_items = responce.size();
        for (int i = 0; i < history_items; i++) {
            result += responce.get(i) + "\n";
        }
        return result;
    }

    public String account_server() {
        String result = new String();
        ArrayList<String> responce = new ArrayList<String>();
        String s = request1("account", Username);
        String s2 = removebrackets(s);
        responce = parsing(s2);
        int account_items = responce.size();
        for (int i = 0; i < account_items; i++) {
            result += "Username: " + parsing2(responce.get(i)).get(0) + "\n";
            result += "Password: " + parsing2(responce.get(i)).get(1) + "\n";
            result += "Mail: " + parsing2(responce.get(i)).get(2) + "\n";
            result += "Birthday: " + parsing2(responce.get(i)).get(3) + "\n";
            result += "Telephone: " + parsing2(responce.get(i)).get(4) + "\n";

        }
        return result;
    }

    public void deposit_server(float amount) {
        requestvoid("deposit", Username + "," + String.valueOf(amount));

    }

    public static void main(String[] args) {
        launch();
    }
     public static Socket socket=null;
    ////////////////////////////////////////////MERGE/////////////////////////////////////
     public static String merge (String s1,String s2){
        return s1+";"+s2;
    }
     /////////////////////////////////////////////////request1/////////////////////////////////
     public static String request1(String s1,String s2) throws IOException{
        String send,receive=null;
        try{
        String server_send=merge(s1,s2);
        socket=new Socket("localhost",333);
        System.out.println(".....................now you can start chat ...................");
//        BufferedReader keyr=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
        //client reading the message from server through it's inputstream
        InputStreamReader isr=new InputStreamReader(socket.getInputStream());//opening the input stream
        BufferedReader ser_receive=new BufferedReader(isr);
        
       
        while(true){
//        send=keyr.readLine();
          send=server_send;
        pw.println(send);
      
        if((receive=ser_receive.readLine())!=null){
        System.out.println("server : "+receive);
        
        }
        
        }
        
        }
        catch(Exception e){}
        socket.close();
        return receive;
    }
////////////////////////////////////////////////////////////////////requestvoid////////////////////////////
     public static void requestvoid(String s1,String s2) throws IOException{
     String send=null;
        try{
        String server_send=merge(s1,s2);
       Socket socket=new Socket("localhost",333);
        System.out.println(".....................now you can start chat ...................");

        PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
        //client reading the message from server through it's inputstream
       
        while(true){

          send=server_send;
        pw.println(send);
      
        }
        
        }
        catch(Exception e){}
      socket.close();
     }
}

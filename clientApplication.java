/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package client;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert.AlertType;

public class Client extends Application {
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
            socket=new Socket(Server,333);
            System.out.println(".....................now you can start chat ...................");
//        BufferedReader keyr=new BufferedReader(new InputStreamReader(System.in));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
            //client reading the message from server through it's inputstream
            InputStreamReader isr=new InputStreamReader(socket.getInputStream());//opening the input stream
            BufferedReader ser_receive=new BufferedReader(isr);



//        send=keyr.readLine();
                send=server_send;
                pw.println(send);

                if((receive=ser_receive.readLine())!=null){
                    System.out.println("server : "+receive);

                }




        }
        catch(Exception e){}
        try{
            socket.close();
        }
        catch (Exception e){};
        return receive;
    }
    ////////////////////////////////////////////////////////////////////requestvoid////////////////////////////
    public static void requestvoid(String s1,String s2) throws IOException{
        String send=null;
        try{
            String server_send=merge(s1,s2);
            Socket socket=new Socket(Server,333);
            System.out.println(".....................now you can start chat ...................");

            PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
            //client reading the message from server through it's inputstream



                send=server_send;
                pw.println(send);

            }


        catch(Exception e){}
//        socket.close();
//        try{
//            socket.close();
//        }
//        catch (Exception e){};
    }
    TextField search = new TextField();
    String Username;

    TextField qty = new TextField();
    String Password;
    String mail;
    LocalDate birth;
    int Phone;
    static String Server;

ArrayList<String> items = new ArrayList<String>();



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
     public void pop_up_screen(String msg) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("warning");
        alert.setHeaderText("warning");
        alert.setContentText(msg);
        alert.show();
    }
     
     public void itemsScreen() {
        System.out.println(items);
        ScrollPane layout = new ScrollPane();
        GridPane root = new GridPane();
        root.setVgap(10);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);
        //root.addRow(0, name, price);
        for (int i = 0; i < items.size(); i += 2) {
            Label first_name = new Label(items.get(i));
            Button b = new Button(items.get(i + 1));
            root.addRow(i + 1, first_name, b);
        }
        layout.setContent(root);
        layout.setLayoutX(10);
        layout.setLayoutY(10);
        Scene scene = new Scene(layout, 400, 200);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

     public void search(String item_name) throws IOException {
        GridPane root = new GridPane();
        String[] a = removebracket(request1("search4item", item_name)).split(",");
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < a.length; i++) {
            items.add(a[i].trim());
        }
        //ArrayList<String> items = parsing(removebrackets(request1("search", item_name)));
        if (items.size() == 1 && "".equals(items.get(0))) {
            pop_up_screen("No Available Item With This Name");
            return;
        }
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
                System.out.println(name + " " + amount);
                String qi = name + "," + amount+","+Username;
                String reply ="-1";
                try {
                    reply = request1("add2cart", qi);
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (Integer.parseInt(reply) == -1) {
                    pop_up_screen("no enough items");
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
        newWindow.show();
    }

    public void cartScreen() throws IOException {
         GridPane grid = new GridPane();
        String it = "";
        ArrayList<String> items = arraylist(request1("search", Username));
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
                try {
                    request1("purchase", Username);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

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
                try {
                    requestvoid("remove", l.getText() + "," + (int) Float.parseFloat(qty.getText()) + "," + Username);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
        newWindow.show();
    }
    public static ArrayList<String> arraylist(String message) {
        message = removebracket (message);
        System.out.println(message);
        String[] arrOfStr = message.split(",");
        ArrayList<String> s = new ArrayList<String>();
        String name = "";
        for (int i = 0; i < arrOfStr.length; i++) {
            //System.out.println(arrOfStr[i]);
            if (i % 2 == 0) {
                name = arrOfStr[i];
            } else {
                name = name+","+ arrOfStr[i];
                s.add(name);
                name ="";

            }
        }
        return s;
    }
    public static String removebracket(String s){
        return s.substring(1,s.length()-1);
    }
    public void editScreen(String item_name) {

        Label q = new Label("quantity");
        Button ok = new Button("OK");
        ok.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
                System.out.println(Integer.parseInt(qty.getText()) + "," + item_name + "," + "batoul");
                try {
                    int reply = Integer.parseInt(request1("edit",Integer.parseInt(qty.getText()) + "," + item_name + "," + Username));
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        newWindow.show();
    }

    @Override
    public void start(Stage stage) throws IOException {
        server_address();

    }
    
    public void server_address(){
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        HBox h1 = new HBox(7);
        h1.setAlignment(Pos.CENTER);
        Label se = new Label("server IP address");
        h1.setMargin(se, new Insets(10, 10, 10, 10));
        TextField ser = new TextField();
        //Username=user.getText();
        h1.setMargin(ser, new Insets(10, 10, 10, 10));
        Button server = new Button("ok");
        h1.setMargin(server, new Insets(10, 10, 10, 10));
        h1.getChildren().addAll(se, ser);
        server.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Server=ser.getText();
                first_page() ;
            }
        }));
        vbox.getChildren().addAll(h1,server);
        Scene scene = new Scene(vbox, 400, 400);
        stage.getIcons().add(icon());
        stage.setTitle("Market");
        stage.setScene(scene);
        stage.show();
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
        Alert alert = new Alert(Alert.AlertType.WARNING);
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
                String s = null;
                try {
                    s = request1("login", st);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

        login.show();
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
                try {
                    requestvoid("signup", s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                signUp.close();
                //first_page();
            }
        }));
        v.getChildren().addAll(h1, h2, h3, h4, h5, sign);
        signUp.show();
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
        window.getIcons().add(icon());
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
        Button show_items = new Button("show items");
        Button logOut = new Button("Log Out");
//vBox.getChildren().addAll(history, cash, c, search,s, accountInfo, logOut);
        vBox.getChildren().addAll(history, cash, c, search, s, accountInfo, deposit,show_items, logOut);
        //Username
        show_items.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    String reply = request1("items", "chicken");
                    parsing3(reply);
                    itemsScreen();
                } catch (Exception er1) {
                    er1.printStackTrace();

                }
            }

        }));

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
                window.close();
                first_page();
            }
        };
        logOut.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, logout);
        //cart event handler
        EventHandler<javafx.scene.input.MouseEvent> cart
                = new EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                try {
                    cartScreen();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        c.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, cart);

        EventHandler<javafx.scene.input.MouseEvent> searching
                = new EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                try {
                    search(search.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        s.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, searching);
        window.show();

    }
    public void parsing3(String str) {
        System.out.println("parse");
        System.out.println(str);
        System.out.println("len" + str.length());
        String str2 = "";
        for (int i = 0; i < str.length() - 1; i++) {
            str2 += str.charAt(i);
            if (str.charAt(i + 1) == ',') {
                System.out.println(str2);
                items.add(str2);
                str2 = "";
                i++;
            }

        }
        System.out.println(items.size());
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
        window.show();
    }

    public void history_display(String s) {
        Stage window = new Stage();
        window.getIcons().add(icon());
//        window.initModality(Modality.APPLICATION_MODAL);
        ScrollPane layout = new ScrollPane();
        window.setTitle("History");
        Text t = new Text(s);
        layout.setContent(t);

        Scene scene = new Scene(layout, 400, 400);
        window.setScene(scene);
        window.show();

    }

    public void account_display(String s) {
        Stage window = new Stage();
        window.getIcons().add(icon());
//        window.initModality(Modality.APPLICATION_MODAL);
        ScrollPane layout = new ScrollPane();
        window.setTitle("Account Information");
        Text t = new Text(s);
        layout.setContent(t);

        Scene scene = new Scene(layout, 400, 400);
        window.setScene(scene);
        window.show();

    }

    public void deposit_dislay() {
        Stage window = new Stage();
//        window.getIcons().add(icon());
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
                        if(floatValue < 0){
                            invalid_deposit_display();
                        }
                        else {
                            deposit_server(floatValue);
                        }
                    } catch (NumberFormatException e ){
                        invalid_deposit_display();
                    }

                } catch (Exception er1) {
                    er1.printStackTrace();
                }
            }

        }));
        window.show();
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
        window.show();
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
        window.show();
    }

    public String removebrackets(String s) {
        if(s.length() == 0){
            System.out.println("empty string");
        }
        return s.substring(1, s.length() - 1);
    }

    public String cash_server() throws IOException {
        String responce = request1("cash", Username);

        return responce;
    }

public String history_server() throws IOException {
    String result = new String("OID : Item name : price : amount : date\n");
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

    public String account_server() throws IOException {
        String result = new String();
        ArrayList<String> responce = new ArrayList<String>();
        String s = request1("account", Username);
        String s2 = removebrackets(s);
        responce = parsing(s2);

        int account_items = responce.size();
        for (int i = 0; i < account_items; i++) {
            System.out.println(responce.get(i));
            result += "Username: " + parsing2(responce.get(i)).get(0) + "\n";
            result += "Password: " + parsing2(responce.get(i)).get(1) + "\n";
            result += "Mail: " + parsing2(responce.get(i)).get(2) + "\n";
            result += "Birthday: " + parsing2(responce.get(i)).get(3) + "\n";
            result += "Telephone: " + parsing2(responce.get(i)).get(4) + "\n";

        }
        return result;
    }

    public void deposit_server(float amount) throws IOException {
        requestvoid("deposit", Username + "," + String.valueOf(amount));

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

    public static void main(String[] args) throws IOException {
        launch();
//        requestvoid("history","ff");
//        String s1 = request1("history","aa");
//        System.out.println(s1);
    }

}


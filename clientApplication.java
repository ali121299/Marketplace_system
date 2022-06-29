package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

public class clientApplication extends Application {

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

    public void search(Stage primaryStage, String item_name) {
        //connect to server call searchForItem(item_name)
        ArrayList<String> items = searchForItem(item_name);
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
                addToCart(name, amount);
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
        newWindow.getIcons().add(icon());

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();
    }

       public void cartScreen(Stage primaryStage) {
        ArrayList<item_qty> items = search();
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).name + " " + items.get(i).qty);
        }
        EventHandler<javafx.scene.input.MouseEvent> purchas
                = new EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                System.out.println("hi");
                purchase(items);

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
                System.out.println("names"+l.getText() + " " + qty.getText());
                System.out.println("here");
                remove(l.getText(), (int)Float.parseFloat(qty.getText()));
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
                Label l = (Label) n;
                System.out.println(l.getText());
                editScreen(primaryStage, l.getText());

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
            Label l = new Label(items.get(i).name);
           String amount = String.valueOf((int)Float.parseFloat(items.get(i).qty));
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
        newWindow.getIcons().add(icon());
        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();
        //from https://o7planning.org/11533/open-a-new-window-in-javafx
    }

    public void editScreen(Stage primaryStage, String item_name) {
        Label q = new Label("quantity");
        Button ok = new Button("OK");
        ok.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub  
                System.out.println(Integer.parseInt(qty.getText()) + " " + item_name);
                edit(Integer.parseInt(qty.getText()), item_name);
            }
        });
        VBox root = new VBox();
        root.getChildren().addAll(q,qty, ok);
        Scene scene = new Scene(root, 600, 400);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Cart");
        newWindow.setScene(scene);
        newWindow.getIcons().add(icon());

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();
    }

    @Override
    public void start(Stage stage) throws IOException {

    }

    public static void main(String[] args) {
        launch();
    }
}

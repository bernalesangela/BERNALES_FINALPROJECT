package com.example.frdmclub;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.*;
import java.util.List;
import java.util.Scanner;

public class HelloController {


    InventoryManager inventoryManager = new InventoryManager();
    private String path = "/Users/anfb/Downloads/Product_Intenvory_Management_System-main/src/main/resources/com/example/frdmclub/inventory.txt";
    private List<String[]> data = inventoryManager.loadDataFromFile(path);

    @FXML
    private TextField productNameInput;
    @FXML
    private TextField quantityInput;
    @FXML
    private TextField styleInput;
    @FXML
    private TextField materialInput;
    @FXML
    private TextField patternInput;
    @FXML
    private TextField priceInput;
    @FXML
    private GridPane mainGrid;

    @FXML Label loggedInAs;

    @FXML Label idHeader;
    @FXML Label nameHeader;
    @FXML Label priceHeader;
    @FXML Label patternHeader;
    @FXML Label styleHeader;
    @FXML Label materialHeader;
    @FXML Label quantityHeader;

    private Stage stage;


    @FXML
    public void initialize() {

        populateGrid(data);
        GridPane.setValignment(idHeader, VPos.CENTER);
        GridPane.setValignment(nameHeader, VPos.CENTER);
        GridPane.setValignment(quantityHeader, VPos.CENTER);
        GridPane.setValignment(patternHeader, VPos.CENTER);
        GridPane.setValignment(materialHeader, VPos.CENTER);
        GridPane.setValignment(styleHeader, VPos.CENTER);
        GridPane.setValignment(priceHeader, VPos.CENTER);

        GridPane.setHalignment(idHeader, HPos.CENTER);
        GridPane.setHalignment(nameHeader, HPos.CENTER);
        GridPane.setHalignment(quantityHeader, HPos.CENTER);
        GridPane.setHalignment(patternHeader, HPos.CENTER);
        GridPane.setHalignment(materialHeader, HPos.CENTER);
        GridPane.setHalignment(styleHeader, HPos.CENTER);
        GridPane.setHalignment(priceHeader, HPos.CENTER);


    }

    public void initializeData(String name) {
        loggedInAs.setText(name);
    }

    @FXML
    public void addItem(ActionEvent e) {
        String name = productNameInput.getText();
        int quantity = Integer.parseInt(quantityInput.getText());
        String style = styleInput.getText();
        String material = materialInput.getText();
        String pattern = patternInput.getText();
        int price = Integer.parseInt(priceInput.getText());
        int productId = Integer.parseInt(String.valueOf(generateID()));

        String data = Integer.toString(productId + 1) + ',' + name + ',' + quantity + ',' + style + ',' + material + ',' + pattern + ',' + price;
        writeDataToFile(data);
        clearGridPaneExceptFirstRow();
        populateGrid(inventoryManager.loadDataFromFile(path));
    }

    @FXML
    public void populateGrid(List<String[]> data) {

        for (int row = 0; row < data.size(); row++) {
            String[] rowData = data.get(row);
            for (int col = 0; col < rowData.length; col++) {
                Label label = new Label(rowData[col]);
                label.setAlignment(Pos.CENTER);
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.CENTER);
                mainGrid.add(label, col, row + 1);
            }
            Button edit = new Button("Edit");
            edit.setOnAction(event -> openEditWindow(rowData));

            VBox vBox = new VBox(5, edit);
            vBox.setAlignment(Pos.CENTER); // Center the button
            GridPane.setHalignment(vBox, HPos.CENTER);
            GridPane.setValignment(vBox, VPos.CENTER);

            mainGrid.add(vBox, 7, row + 1);


        }
    }

    @FXML
    public char generateID() {
        char firstCharacter = '0';
        try (Scanner reader = new Scanner(new File(path))) {
            String line;
            while(reader.hasNext()) {
                line = reader.nextLine();
                firstCharacter = line.charAt(0);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return firstCharacter;
    }

    @FXML
    public void writeDataToFile(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {


            writer.newLine();
            writer.write(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clearGridPaneExceptFirstRow() {

        int numRows = mainGrid.getRowConstraints().size();
        mainGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);
    }


    // Method to open the edit window
    @FXML
    void openEditWindow(String[] rowData) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditWindow.fxml"));
            Parent root = fxmlLoader.load();

            EditWindow editController = fxmlLoader.getController();
            editController.initializeData(rowData); // Pass rowData to EditWindowController

            Stage editStage = new Stage();
            editStage.initModality(Modality.APPLICATION_MODAL); // Block events to other windows
            editStage.setTitle("Edit Window");
            editStage.setScene(new Scene(root));
            editController.setStage(editStage); // Pass the stage to the controller
            editStage.showAndWait(); // Show the window and wait for it to be closed

            clearGridPaneExceptFirstRow();
//            populateGrid(data);

            // After edit window is closed, refresh the grid
            populateGrid(inventoryManager.loadDataFromFile(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
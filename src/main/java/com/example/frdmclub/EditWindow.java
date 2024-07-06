package com.example.frdmclub;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditWindow {

    private String path = "/Users/anfb/Downloads/Product_Intenvory_Management_System-main/src/main/resources/com/example/frdmclub/inventory.txt";
    InventoryManager inventoryManager = new InventoryManager();

    @FXML private TextField nameEdit;
    @FXML private TextField quantityEdit;
    @FXML private TextField styleEdit;
    @FXML private TextField materialEdit;
    @FXML private TextField patternEdit;
    @FXML private TextField priceEdit;
    @FXML private Button saveButton;
    @FXML private Button closeButton;
    @FXML private GridPane mainGrid;

    private String productId;
    private Stage stage;

    @FXML
    public void initialize() {
        saveButton.setOnAction(event -> saveAndClose());
        closeButton.setOnAction(event -> close());


    }

    public void initializeData(String[] initialData) {

        productId = initialData[0];
        nameEdit.setText(initialData[1]);
        quantityEdit.setText(initialData[2]);
        styleEdit.setText(initialData[3]);
        materialEdit.setText(initialData[4]);
        patternEdit.setText(initialData[5]);
        priceEdit.setText(initialData[6]);
    }

    @FXML
    private void saveAndClose() {
        String newData = nameEdit.getText() + ',' + quantityEdit.getText() + ',' + styleEdit.getText() + ',' + materialEdit.getText() + ',' + patternEdit.getText() + ',' + priceEdit.getText();
        inventoryManager.editDataInFile(productId, newData, path);
        close();
    }

    private void close() {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void Delete() {
        int index = inventoryManager.findLineIndex(path, productId);
        inventoryManager.deleteLineInFile(path, index);

        stage.close();
    }
}

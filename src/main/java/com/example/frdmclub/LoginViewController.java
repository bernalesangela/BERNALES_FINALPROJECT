package com.example.frdmclub;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginViewController {

    private String path = "/Users/anfb/Downloads/Product_Intenvory_Management_System-main/src/main/resources/com/example/frdmclub/users.txt";
    private String loggedInAs;

    private Stage stage;

    @FXML private Button loginButton;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label failedLabel;

    @FXML
    public void initialize() {}

    @FXML
    public void LoginButtonOnAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isFound = getCreds(path, username, password);

        if (isFound) {
            System.out.println("Login Successful");
            System.out.println("Logged in as: " + loggedInAs);

            goToHome();
            closeWindow(); // Close the login window
        } else {
            System.out.println("Login Failed");
        }
    }

    @FXML
    public boolean getCreds(String path, String user, String password) {
        List<String[]> data = new ArrayList<>();
        int lineIndex = 0;
        boolean accountFound = false;
        boolean credsMatch = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));

                if (data.get(lineIndex)[0].equals(user)) {
                    accountFound = true;
                    if (data.get(lineIndex)[1].equals(password)) {
                        credsMatch = true;
                        loggedInAs = data.get(lineIndex)[2];
                        break;
                    }
                }
                lineIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!accountFound) {
            System.out.println("Account not found");
            failedLabel.setVisible(true);
        }

        return credsMatch;
    }

    @FXML
    public void goToHome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Parent root = fxmlLoader.load();

            HelloController mainController = fxmlLoader.getController();
            mainController.initializeData(loggedInAs);

            Stage mainStage = new Stage();
            mainStage.initModality(Modality.APPLICATION_MODAL);
            mainStage.setTitle("Admin");
            mainStage.setScene(new Scene(root));
            mainController.setStage(mainStage);
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }
}

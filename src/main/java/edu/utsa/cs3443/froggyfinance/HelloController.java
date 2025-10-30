package edu.utsa.cs3443.froggyfinance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick(ActionEvent event) {

        Stage stage = (Stage) welcomeText.getScene().getWindow();
        Scene gameScene = GamePanel.createGameScene(stage, true);
        stage.setScene(gameScene);

        stage.setTitle("Froggy Finance");
        stage.centerOnScreen();
        System.out.println("Start button clicked!");
    }
}
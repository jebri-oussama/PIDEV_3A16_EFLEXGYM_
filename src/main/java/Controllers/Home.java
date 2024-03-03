package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {

    @FXML
    private Button userinterface;

    @FXML
    private Button viewProfileButton;

    @FXML
    private Button logoutButton;

    @FXML
    void userinterface(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Product.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleViewProfile(ActionEvent event) {
        // Add your code to handle view profile action
    }

    @FXML
    void handleLogout(ActionEvent event) {
        // Add your code to handle logout action
    }
}

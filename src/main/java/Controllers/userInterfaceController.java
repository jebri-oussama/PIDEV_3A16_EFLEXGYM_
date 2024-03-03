// userInterfaceController.java

package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userInterfaceController implements Initializable {

    @FXML
    private TextField textField;

    @FXML
    private Button mapButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the controller
    }

    @FXML
    private void goToMap(ActionEvent event) {
        // Handle the button click event to redirect to the map
        System.out.println("Redirecting to the map...");

        // Load the map.fxml file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/map.fxml"));
            Parent mapRoot = loader.load();
            Map mapController = loader.getController(); // Get the map controller instance

            // Pass the text field value to the map controller
            mapController.setSearchInputText(textField.getText());

            Scene mapScene = new Scene(mapRoot);

            // Get the stage information
            Stage stage = (Stage) mapButton.getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(mapScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

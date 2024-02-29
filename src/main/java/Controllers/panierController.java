package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.DataSource;

public class panierController implements Initializable {

    @FXML
    private VBox panierBox;

    @FXML
    private Label subtotalLabel;

    // Declare the connection object at the class level
    private Connection conn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Fetch products from the database when the controller is initialized
        fetchProductsFromDatabase();
    }

    // Method to fetch products from the database
    private void fetchProductsFromDatabase() {
        double subtotal = 0;
        try {
            // Use the connection object declared at the class level
            conn = DataSource.getInstance().getCnx();
            PreparedStatement statement = conn.prepareStatement("SELECT nom, prix FROM panier");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString("nom");
                Float productPrice = resultSet.getFloat("prix");
                // Create and add product label to the VBox
                Label productLabel = new Label(productName + " - $" + productPrice);
                panierBox.getChildren().add(productLabel);
                // Calculate subtotal
                subtotal += productPrice;
            }

            // Update subtotal label
            subtotalLabel.setText("Subtotal: $" + subtotal);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Event handler for the button to go back to products
    @FXML
    private void goBackToProducts(ActionEvent event) {
        // Close the current window (panier) to go back to the products page
        Stage stage = (Stage) panierBox.getScene().getWindow();
        stage.close();
    }

    // Method to close the connection when the controller is destroyed or no longer needed
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package Controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import gestion_produit.service.panierService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List; // Import List
import gestion_produit.entities.panier;// Assuming Product is your model class for products
import javafx.stage.Stage;
import utils.DataSource;

public class PaymentController {

    @FXML
    private TextField amountField;

    @FXML
    private TextField currencyField;

    @FXML
    private TextField cardNumberField;





    public void setAmountToPay(float amount) {
        // Set the payment amount in the amountField TextField
        amountField.setText(String.valueOf(amount));
    }



    @FXML
    public void processPayment() {
        String amountText = amountField.getText();
        String currency = currencyField.getText();
        String card = cardNumberField.getText();

        // Move the payment processing logic here
        try {
            Stripe.apiKey = "sk_test_51OpMszJBIZnGaCSRFp9XiTyNaj3pXhiahFXKN680Y3qS9KQk0BoBDiCiM0oB0oyXufFRu6wPvo8mK23pq79ZpxZA00n44jF8AD"; // Your Secret Key

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (Float.valueOf(amountText) * 1)) // Amount in cents
                    .setCurrency(currency)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());

            // Clear the text fields after successful payment
            amountField.clear();
            currencyField.clear();
            sup();
            refreshPanierTable();

            // Close the payment window
            Stage stage = (Stage) amountField.getScene().getWindow();
            stage.close();

            // Refresh the panier table


        } catch (StripeException e) {
            System.out.println("Payment failed. Error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sup() {
        // Confirm with the user before deleting all entries

                try {
                    // Get connection from DataSource
                    Connection connection = DataSource.getInstance().getCnx();

                    // Create statement
                    Statement statement = connection.createStatement();

                    // Execute SQL DELETE query to delete all entries from "panier" table
                    String sql = "DELETE  FROM panier";
                    statement.executeUpdate(sql);



                    // Refresh the table after deletion

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        ;

    private void refreshPanierTable() throws IOException {
        // Load the panierController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/panier.fxml"));
        Parent root = loader.load();
        panierController panierController = loader.getController();

        // Call the refreshTable method to refresh the panier table
        panierController.refreshTable();
    }


}

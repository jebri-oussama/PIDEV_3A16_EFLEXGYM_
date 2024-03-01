package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import gestion_produit.entities.produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.DataSource;

public class userInterfaceController implements Initializable {
    private final ObservableList<produit> vestimentaire = FXCollections.observableArrayList();
    private final ObservableList<produit> alimentaire = FXCollections.observableArrayList();

    @FXML
    private FlowPane productsPane;

    // Declare the connection object
    private Connection conn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the connection
        conn = DataSource.getInstance().getCnx();
    }

    // Method to create product element
    private VBox createProductBox(String name, String price, String description, String imageURL) {
        VBox productBox = new VBox(10);
        productBox.setPrefWidth(200);

        // Product Image
        ImageView imageView = new ImageView(new Image(imageURL));
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        // Product Name
        Label nameLabel = new Label(name);

        // Product Price
        Label priceLabel = new Label("Price: " + price);

        // Product Description
        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);

        // Add button to add product to cart
        Button addButton = new Button("Ajouter");
        addButton.setOnAction(event -> addToCartHandler(name, price));

        // Add elements to productBox
        productBox.getChildren().addAll(imageView, nameLabel, priceLabel, descriptionLabel, addButton);

        return productBox;
    }

    // Method to add product to cart (insert into the panier table)
    // Method to handle adding a product to the cart
    private void addToCartHandler(String productName, String productPrice) {
        // Show confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to add this product to the cart?\n\n"
                + "Product: " + productName + "\n"
                + "Price: " + productPrice);

        // Handle the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                addToCart(productName, productPrice);
            }
        });
    }

    private void addToCart(String productName, String productPrice) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO panier (nom, prix) VALUES (?, ?)");
            statement.setString(1, productName);
            statement.setString(2, productPrice);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Product added to cart: " + productName);
            } else {
                System.out.println("Failed to add product to cart");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to handle Alimentaire button click
    @FXML
    private void handleAlimentaireAction(ActionEvent event) {
        fetchProducts(7); // Category for Alimentaire
    }

    // Method to handle Vestimentaire button click
    @FXML
    private void handleVestimentaireAction(ActionEvent event) {
        fetchProducts(6); // Category for Vestimentaire
    }

    // Method to fetch products from the database based on category
    private void fetchProducts(int category) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM produit WHERE categorie = ?");
            statement.setInt(1, category);
            ResultSet resultSet = statement.executeQuery();

            // Clear existing products from the pane
            productsPane.getChildren().clear();

            // Iterate through the results and create product elements
            while (resultSet.next()) {
                String productName = resultSet.getString("nom");
                String productPrice = resultSet.getString("prix");
                String productDescription = resultSet.getString("description");
                String productImageURL = resultSet.getString("image");

                // Create and add product element to the FlowPane
                VBox productBox = createProductBox(productName, productPrice, productDescription, productImageURL);
                productsPane.getChildren().add(productBox);
            }

            // Add button to go back to the main interface
            Button backButton = new Button("Back to Main Interface");
            backButton.setStyle("-fx-background-color: #FB8000; -fx-text-fill: white; -fx-font-size: 14pt;");
            backButton.setOnAction(this::loadMainInterface);
            productsPane.getChildren().add(backButton);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to load the main interface
    private void loadMainInterface(ActionEvent event) {
        // Get the reference to the stage
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(loader.load()));
            newStage.show();

            // Close the current stage
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Event handler for "Panier" button to redirect to panier.fxml
    @FXML
    private void redirectToPanier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/panier.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to close the connection when the application exits or when it's no longer needed
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

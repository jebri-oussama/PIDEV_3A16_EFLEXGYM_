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
import javafx.scene.control.Button;
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

        addButton.setOnAction(this::addToCartHandler);


        // Add elements to productBox
        productBox.getChildren().addAll(imageView, nameLabel, priceLabel, descriptionLabel, addButton);

        return productBox;
    }

    // Method to add product to cart (insert into the panier table)
    private void addToCart(String productName, String productPrice) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO panier (nom, prix) VALUES (?, ?)");

            // Set the product name and price as parameters for the prepared statement
            statement.setString(1, productName);
            statement.setString(2, productPrice);

            // Execute the query
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

    // Event handler for "Ajouter" button
    @FXML
    private void addToCartHandler(ActionEvent event) {
        Button addButton = (Button) event.getSource();
        VBox productBox = (VBox) addButton.getParent();
        Label nameLabel = (Label) productBox.getChildren().get(1);
        Label priceLabel = (Label) productBox.getChildren().get(2);
        String productName = nameLabel.getText();
        String productPrice = priceLabel.getText().replace("Price: ", ""); // Remove "Price: " prefix

        addToCart(productName, productPrice);
    }

    // Method to handle Alimentaire button click
    @FXML
    private void handleAlimentaireAction(ActionEvent event) {
        // Clear existing products from the pane
        productsPane.getChildren().clear();

        // Fetch products with category 7 (Alimentaire) from the data source
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM produit WHERE categorie = ?");
            statement.setInt(1, 7); // Set category filter
            ResultSet resultSet = statement.executeQuery();

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
            Button gobackButton = new Button("products");
            gobackButton.setOnAction(this::loadMainInterface);
            productsPane.getChildren().add(gobackButton);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleVestimentaireAction(ActionEvent event) {
        // Clear existing products from the pane
        productsPane.getChildren().clear();

        // Fetch products with category 7 (Alimentaire) from the data source
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM produit WHERE categorie = ?");
            statement.setInt(1, 6); // Set category filter
            ResultSet resultSet = statement.executeQuery();

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
            Button gobackButton = new Button("products");
            gobackButton.setOnAction(this::loadMainInterface);
            productsPane.getChildren().add(gobackButton);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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

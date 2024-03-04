package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import gestion_produit.entities.panier;
import gestion_produit.service.panierService;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.BadWordFilter;
import utils.DataSource;

public class ProductController implements Initializable {

    @FXML
    private FlowPane productsPane;
    private final panierService panierService = new panierService();


    // Declare the connection object
    private Connection conn;
    @FXML
    private TextField searchField;
    private int userId;

    public void initData(int userId) {
        this.userId = userId;}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the connection
        conn = DataSource.getInstance().getCnx();

        // Fetch product data from the database and populate the FlowPane
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM produit");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString("nom");
                String productPrice = resultSet.getString("prix");
                String productDescription = resultSet.getString("description");
                String productImageURL = resultSet.getString("image");

                // Create and add product element to the FlowPane
                VBox productBox = createProductBox(productName, productPrice, productDescription, productImageURL);
                productsPane.getChildren().add(productBox);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add listener to searchField
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchByName(newValue);
            }
        });
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
        Button addButton = new Button("Add to Cart");
        addButton.setOnAction(event -> addToCart(name, price));

        // Add elements to productBox
        productBox.getChildren().addAll(imageView, nameLabel, priceLabel, descriptionLabel, addButton);

        return productBox;
    }

    // Method to add product to cart (insert into the panier table)
    private void addToCart(String productName, String productPrice) {
        try {


            // Check if the product already exists in the cart
            PreparedStatement checkStatement = conn.prepareStatement("SELECT prix FROM panier WHERE nom = ?");
            checkStatement.setString(1, productName);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                float aa = Float.parseFloat(productPrice);
                float currentPrice = resultSet.getFloat("prix");
                float newPrice = currentPrice + aa;

                PreparedStatement updateStatement = conn.prepareStatement("UPDATE panier SET prix = ? WHERE nom = ?");
                updateStatement.setFloat(1, newPrice);
                updateStatement.setString(2, productName);
                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Product price updated: " + productName);
                } else {
                    System.out.println("Failed to update product price: " + productName);
                }
            } else {
                User p = new UserService().readById(userId);
                float ab = Float.parseFloat(productPrice);
                panier pp = new panier(productName, ab, p);
                panierService.add(pp);
                float aa = Float.parseFloat(productPrice);
                PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO panier (nom, prix) VALUES (?, ?)");
                insertStatement.setString(1, productName);
                insertStatement.setFloat(2, aa);
                int rowsInserted = insertStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Product added to cart: " + productName);
                } else {
                    System.out.println("Failed to add product to cart");
                }
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

    @FXML
    private void searchByName(String searchTerm) {
        searchTerm = searchTerm.trim();

        // Check if search term is empty
        if (searchTerm.isEmpty()) {
            // Clear existing products in the FlowPane
            productsPane.getChildren().clear();

            // Fetch all products from the database
            try {
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM produit");
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
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return; // Exit search if search term is empty
        }

        // Check if search term contains bad words
        if (BadWordFilter.filterText(searchTerm)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Bad Words Found");
            alert.setHeaderText("Search term contains bad words.");
            alert.setContentText("Please enter a different search term.");

            // Add "OK" button to the alert dialog
            ButtonType okButton = new ButtonType("OK");
            alert.getButtonTypes().setAll(okButton);

            // Show the alert and wait for user response
            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    // Clear search field after user clicks "OK"
                    searchField.clear();
                }
            });

            return; // Exit search if bad words found
        }

        // Clear existing products in the FlowPane
        productsPane.getChildren().clear();

        try {
            // Assuming conn is a valid and accessible database connection
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM produit WHERE nom LIKE ?");
            statement.setString(1, "%" + searchTerm + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString("nom");
                String productPrice = resultSet.getString("prix");
                String productDescription = resultSet.getString("description");
                String productImageURL = resultSet.getString("image");

                // Create and add product element to the FlowPane
                VBox productBox = createProductBox(productName, productPrice, productDescription, productImageURL);
                productsPane.getChildren().add(productBox);
            }

            // Optionally, clear search field after successful search
            // searchField.clear();

        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, show an alert or log the error
        }
    }

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


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

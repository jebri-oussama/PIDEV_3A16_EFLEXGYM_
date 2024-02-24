package Controllers;
import java.sql.Connection;
import GestionFinance.entites.BilanFinancier;
import gestion_produit.entities.categorie;
import gestion_produit.entities.produit;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import gestion_produit.service.produitService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.DataSource;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherProduitController implements Initializable {

    @FXML
    private TableView<produit> tableViewProduit;

    @FXML
    private TableColumn<produit, Integer> idColumn;

    @FXML
    private TableColumn<produit, String> nomColumn;

    @FXML
    private TableColumn<produit, Integer> prixColumn;

    @FXML
    private TableColumn<produit, Integer> quantiteColumn;

    @FXML
    private TableColumn<produit, String> descriptionColumn;

    @FXML
    private TableColumn<produit, Integer> categorieColumn;

    @FXML
    private TableColumn<produit, Integer> idbilanfinancierColumn;

    @FXML
    private TableColumn<produit, String> idadminColumn;

    @FXML
    private TableColumn<produit, Void> deleteColumn;

    @FXML
    private TableColumn<produit, Void> selectColumn;
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtimage;

    @FXML
    private TextField txtprix;

    @FXML
    private TextField txtquantite;

    @FXML
    private TextField txtdescription;

    @FXML
    private TextField txtcategorie;

    @FXML
    private TextField txtid_bilan_financier;

    @FXML
    private TextField txtid_admin;

    private produitService produitService;
    @FXML
    private ToggleButton chkAlimentaire;

    @FXML
    private ToggleButton chkVestimentaire;
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    private final ObservableList<produit> produits = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        produitService = new produitService();

        // Set up the columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categorieColumn.setCellValueFactory(data -> {
            int categoryId = data.getValue().getCategorie().getId();
            return new SimpleIntegerProperty(categoryId).asObject();
        });
        idbilanfinancierColumn.setCellValueFactory(data -> {
            int bilanid = data.getValue().getId_bilan_financier().getId();
            return new SimpleIntegerProperty(bilanid).asObject();
        });
        idadminColumn.setCellValueFactory(new PropertyValueFactory<>("id_admin"));



        // Create the "Supprimer" button column
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    produit produit = getTableView().getItems().get(getIndex());
                    supprimerproduit(produit.getId());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Create the "Sélectionner" button column
        selectColumn.setCellFactory(param -> new TableCell<>() {
            private final Button selectButton = new Button("Sélectionner");

            {
                selectButton.setOnAction(event -> {
                    produit produit = getTableView().getItems().get(getIndex());
                    txtId.setText(String.valueOf(produit.getId()));
                    txtnom.setText(produit.getNom());
                    txtimage.setText(produit.getImage());
                    txtprix.setText(String.valueOf(produit.getPrix()));
                    txtquantite.setText(String.valueOf(produit.getQuantite()));
                    txtdescription.setText(produit.getDescription());
                    int categoryId = produit.getCategorie().getId();
                    txtcategorie.setText(String.valueOf(categoryId));
                    int bilanid = produit.getId_bilan_financier().getId();
                    txtid_bilan_financier.setText(String.valueOf(bilanid));
                    txtid_admin.setText(String.valueOf(produit.getId_admin()));
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(selectButton);
                }
            }
        });

        // Load data from the database
        refreshTable();
    }

    public void supprimerproduit(int id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this product?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                produitService.delete(id);
                refreshTable();
            }
        });
    }

    private void refreshTable() {
        produits.clear();
        List<produit> produitList = produitService.readAll();
        produits.addAll(produitList);
        tableViewProduit.setItems(produits);
    }

    @FXML
    public void updateProduit(ActionEvent event) {
        // Get the selected product
        produit selectedProduit = tableViewProduit.getSelectionModel().getSelectedItem();
      /*  if (selectedProduit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun produit sélectionné", "Veuillez sélectionner un produit à mettre à jour.");
            return;
        }*/

        // Get data from text fields
        int id = Integer.parseInt(txtId.getText());
        String nom = txtnom.getText();
        String image = txtimage.getText();
        float prix = Float.parseFloat(txtprix.getText());
        int quantite = Integer.parseInt(txtquantite.getText());
        String description = txtdescription.getText();
        int categoryId = Integer.parseInt(txtcategorie.getText());
        categorie category = new categorie(categoryId,null,description);
        int bilanid = Integer.parseInt(txtid_bilan_financier.getText());
        BilanFinancier bilan = new BilanFinancier(bilanid,null,null,0,0,0,0,0,0);
        int id_admin = Integer.parseInt(txtid_admin.getText());
        produit p = new produit(nom, image, prix, quantite, description, category, bilan, id_admin);

        // Create a new produit object with updated data
        produit updatedProduit = new produit(nom, image, prix, quantite, description, category, bilan, id_admin);

        // Update the produit in the database
        produitService.update(id, updatedProduit);
        txtId.setText("");
        clearFields();

        // Refresh the table
        refreshTable();
    }


    public AfficherProduitController() {
        conn = DataSource.getInstance().getCnx();
    }
    @FXML
    void addProduit() {
        // Check if the connection is initialized
        if (conn == null) {
            System.err.println("Connection is not initialized.");
            return;
        }

        // Get data from text fields
        String nom = txtnom.getText();
        String image = txtimage.getText();
        float prix = Float.parseFloat(txtprix.getText());
        int quantite = Integer.parseInt(txtquantite.getText());
        String description = txtdescription.getText();
        int bilanid = Integer.parseInt(txtid_bilan_financier.getText());
        BilanFinancier bilan =new BilanFinancier(bilanid,null,null,0,0,0,0,0,0);
        int id_admin = Integer.parseInt(txtid_admin.getText());

        // Determine the category based on the user's selection
        int categoryId;
        if (chkAlimentaire.isSelected()) {
            categoryId = 7; // Assuming 7 is the ID for "alimentaire"
        } else if (chkVestimentaire.isSelected()) {
            categoryId = 6; // Assuming 6 is the ID for "vestimentaire"
        } else {
            // No category selected
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucune catégorie sélectionnée", "Veuillez sélectionner une catégorie.");
            return;
        }

        // Create the category object
        categorie category = new categorie(categoryId, null, null);

        // Create the produit object with the selected category
        produit p = new produit(nom, image, prix, quantite, description, category, bilan, id_admin);

        // Add the produit to the database
        produitService.add(p);

        // Clear fields and refresh the table
        clearFields();
        refreshTable();
    }





    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    private void clearFields() {
        txtId.setText("");
        txtnom.setText("");
        txtimage.setText("");
        txtprix.setText("");
        txtquantite.setText("");
        txtdescription.setText("");
        txtcategorie.setText("");
        txtid_bilan_financier.setText("");
        txtid_admin.setText("");
    }
}
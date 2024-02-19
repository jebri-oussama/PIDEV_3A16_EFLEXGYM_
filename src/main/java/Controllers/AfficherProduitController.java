package Controllers;

import entities.produit;
import service.produitService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
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
    private TableColumn<produit, String> categorieColumn;
    @FXML
    private TableColumn<produit, String> idbilanfinancierColumn;
    @FXML
    private TableColumn<produit, String> idadminColumn;

    private final ObservableList<produit> produits = FXCollections.observableArrayList();
    @FXML
    private TableColumn<produit, Void> deleteColumn;

    private produitService produitService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        produitService = new produitService();

        // Set up the columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
       descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
       categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        idbilanfinancierColumn.setCellValueFactory(new PropertyValueFactory<>("id_bilan_financier"));
        idadminColumn.setCellValueFactory(new PropertyValueFactory<>("id_admin"));

        // Create the "Supprimer" button column
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    produit produit = getTableView().getItems().get(getIndex());
                    supprimerEvenement(produit.getId());
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

        // Load data from the database
        List<produit> produitList =produitService.readAll();
        produits.addAll(produitList);
        tableViewProduit.setItems(produits);
    }


    public void supprimerEvenement(int id) {
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
        List<produit> evenementsList = produitService.readAll();
        produits.addAll(evenementsList);
        tableViewProduit.setItems(produits);
    }}
package GestionFinance.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import GestionFinance.entites.Abonnement;
import GestionFinance.service.AbonnementService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherAbonnementsController implements Initializable {

    @FXML
    private TableView<Abonnement> abonnementsTable;

    @FXML
    private TableColumn<Abonnement, String> typeColumn;

    @FXML
    private TableColumn<Abonnement, String> dateDebutColumn;

    @FXML
    private TableColumn<Abonnement, String> dateFinColumn;
    @FXML
    private TableColumn<Abonnement, String> etatColumn;

    @FXML
    private TableColumn<Abonnement, Integer> idAdherentColumn;

    @FXML
    private TableColumn<Abonnement, Integer> idBilanFinancierColumn;

    private final ObservableList<Abonnement> abonnements = FXCollections.observableArrayList();
    private AbonnementService abonnementService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        abonnementService = new AbonnementService();

        // Set up the columns
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
        idAdherentColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId_adherent().getId()).asObject());
        idBilanFinancierColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId_bilan_financier().getId()).asObject());


        // Load data from the database
        refreshTable();
    }

    private void refreshTable() {
        abonnements.clear();
        List<Abonnement> abonnementsList = abonnementService.readAll();
        abonnements.addAll(abonnementsList);
        abonnementsTable.setItems(abonnements);
    }


    public void supprimerAbonnement(int id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this subscription?");
        Abonnement abonnement = new Abonnement();
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                abonnementService.delete(abonnement);
                refreshTable();
            }
        });
    }
}

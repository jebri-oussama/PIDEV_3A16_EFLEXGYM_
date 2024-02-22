package GestionFinance.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import GestionFinance.entites.Abonnement;
import GestionFinance.service.AbonnementService;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherAbonnementsController implements Initializable {

    @FXML
    private TableView<Abonnement> abonnementsTable;
    @FXML
    private TableColumn<Abonnement, Void> modifierColumn;

    @FXML
    private TableColumn<Abonnement, String> typeColumn;
    @FXML
    private TableColumn<Abonnement, String> prixColumn;

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
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
        idAdherentColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId_adherent().getId()).asObject());
        idBilanFinancierColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId_bilan_financier().getId()).asObject());

        // Load data from the database
        refreshTable();

        modifierColumn.setCellValueFactory(new PropertyValueFactory<>("modifierButton"));
        modifierColumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Modifier");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    button.setOnAction(event -> {
                        Abonnement abonnement = getTableView().getItems().get(getIndex());
                        openUpdateAbonnement(abonnement);
                    });
                    setGraphic(button);
                    setText(null);
                }
            }
        });
    }

    public void refreshTable() {
        abonnements.clear();
        List<Abonnement> abonnementsList = abonnementService.readAll();
        abonnements.addAll(abonnementsList);
        abonnementsTable.setItems(abonnements);
    }

    @FXML
    void ajouterAbonnement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAbonnement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            AjouterAbonnementController controller = loader.getController();
            controller.setCurrentScene(scene); // Passer la scène actuelle au contrôleur AjouterAbonnementController

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openUpdateAbonnement(Abonnement abonnement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateAbonnement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            UpdateAbonnementController controller = loader.getController();
            controller.setCurrentScene(scene); // Passer la scène actuelle au contrôleur UpdateAbonnementController
            controller.initData(abonnement.getId()); // Passer l'ID de l'abonnement au contrôleur UpdateAbonnementController

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

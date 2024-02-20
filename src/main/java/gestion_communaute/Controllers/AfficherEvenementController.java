package gestion_communaute.Controllers;

import gestion_communaute.entities.Evenement;
import gestion_communaute.service.EvenementService;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherEvenementController implements Initializable {

    @FXML
    private TableView<Evenement> tableViewEvenements;

    @FXML
    private TableColumn<Evenement, Integer> idColumn;

    @FXML
    private TableColumn<Evenement, String> typeColumn;

    @FXML
    private TableColumn<Evenement, String> dateDebutColumn;

    @FXML
    private TableColumn<Evenement, String> dateFinColumn;

    @FXML
    private TableColumn<Evenement, String> dureeColumn;

    private final ObservableList<Evenement> evenements = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Evenement, Void> deleteColumn;
    @FXML
    private TableColumn<Evenement, Void> updateColumn;
    private EvenementService evenementService;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        evenementService = new EvenementService();

        // Set up the columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        dureeColumn.setCellValueFactory(new PropertyValueFactory<>("duree"));

        // Create the "Supprimer" button column
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    supprimerEvenement(evenement.getId());
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

        // Create the "Update" button column
        updateColumn.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Modifier");

            {
                updateButton.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    updateEvenement(evenement.getId());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });

        // Load data from the database
        List<Evenement> evenementsList = evenementService.readAll();
        evenements.addAll(evenementsList);
        tableViewEvenements.setItems(evenements);
    }


    public void supprimerEvenement(int id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this event?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                evenementService.delete(id);
                refreshTable();
            }
        });
    }
   private void refreshTable() {
        evenements.clear();
        List<Evenement> evenementsList = evenementService.readAll();
        evenements.addAll(evenementsList);
        tableViewEvenements.setItems(evenements);}





    public void redirectToAddEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvenement.fxml"));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    public void redirectToUpdateEvent(Evenement evenement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvenement.fxml"));
            Parent root = loader.load();

            UpdateEvenementController updateController = loader.getController();
            updateController.initData(evenement.getId());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void updateEvenement(int id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvenement.fxml"));
            Parent root = loader.load();

            UpdateEvenementController updateController = loader.getController();
            updateController.initData(id);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}


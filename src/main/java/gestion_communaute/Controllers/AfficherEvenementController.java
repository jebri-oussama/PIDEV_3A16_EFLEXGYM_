package gestion_communaute.Controllers;

import gestion_communaute.entities.Evenement;
import gestion_communaute.service.EvenementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
        tableViewEvenements.setItems(evenements);
    }}



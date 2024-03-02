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
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherAbonnementsController implements Initializable {

    @FXML
    private TableView<Abonnement> abonnementsTable;
    @FXML
    private Button dashboardId;
    @FXML
    private Button bilanFinancierId;
    @FXML
    private Button abonnementsId;
    @FXML
    private TableColumn<Abonnement, Void> modifierColumn;
    @FXML
    private TableColumn<Abonnement, Void> supprimerColumn;
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
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
        idAdherentColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId_adherent().getId()).asObject());
        idBilanFinancierColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId_bilan_financier().getId()).asObject());

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
                        updateAbonnement(abonnement);
                    });
                    setGraphic(button);
                    setText(null);
                }
            }
        });

        supprimerColumn.setCellValueFactory(new PropertyValueFactory<>("supprimerButton"));
        supprimerColumn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Supprimer");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    button.setOnAction(event -> {
                        Abonnement abonnement = getTableView().getItems().get(getIndex());
                        deleteAbonnement(abonnement);
                    });
                    setGraphic(button);
                    setText(null);
                }
            }
        });


    }
    @FXML
    void afficherBilanFinancier() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherBilanFinancier.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void afficherAbonnements() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void afficherDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            controller.setCurrentScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateAbonnement(Abonnement abonnement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateAbonnement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            UpdateAbonnementController controller = loader.getController();
            controller.setCurrentScene(scene);
            controller.initData(abonnement.getId());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteAbonnement(Abonnement abonnement) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet abonnement ?");
        alert.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            abonnementService.delete(abonnement);
            refreshTable();
        }
    }
}
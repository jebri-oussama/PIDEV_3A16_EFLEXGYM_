package GestionFinance.controller;

import GestionFinance.entites.Abonnement;
import GestionFinance.entites.BilanFinancier;
import GestionFinance.service.BilanFinancierService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AfficherBilanFinancierController {
    private final ObservableList<BilanFinancier> bilanFinanciers = FXCollections.observableArrayList();

    @FXML
    private TableView<BilanFinancier> bilanFinancierTable;
    @FXML
    private Button dashboardId;
    @FXML
    private Button bilanFinancierId;
    @FXML
    private Button abonnementsId;

    @FXML
    private TableColumn<BilanFinancier, String> dateDebutColumn;

    @FXML
    private TableColumn<BilanFinancier, String> dateFinColumn;

    @FXML
    private TableColumn<BilanFinancier, Double> salairesCoachsColumn;

    @FXML
    private TableColumn<BilanFinancier, Double> prixLocationColumn;

    @FXML
    private TableColumn<BilanFinancier, Double> revenusAbonnementsColumn;

    @FXML
    private TableColumn<BilanFinancier, Double> revenusProduitsColumn;

    @FXML
    private TableColumn<BilanFinancier, Double> depensesColumn;

    @FXML
    private TableColumn<BilanFinancier, Double> profitColumn;
    @FXML
    private TableColumn<BilanFinancier, Void> modifierColumn;
    @FXML
    private TableColumn<BilanFinancier, Void> supprimerColumn;

    private final BilanFinancierService bilanFinancierService = new BilanFinancierService();

    @FXML
    public void initialize() {

        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        salairesCoachsColumn.setCellValueFactory(new PropertyValueFactory<>("revenus_abonnements")); // Afficher les revenus des abonnements dans la colonne des salaires des coachs
        prixLocationColumn.setCellValueFactory(new PropertyValueFactory<>("revenus_produits")); // Afficher les revenus des produits dans la colonne de la location
        revenusAbonnementsColumn.setCellValueFactory(new PropertyValueFactory<>("salaires_coachs")); // Afficher les salaires des coachs dans la colonne des revenus des abonnements
        revenusProduitsColumn.setCellValueFactory(new PropertyValueFactory<>("prix_location")); // Afficher le prix de la location dans la colonne des revenus des produits
        depensesColumn.setCellValueFactory(new PropertyValueFactory<>("depenses"));
        profitColumn.setCellValueFactory(new PropertyValueFactory<>("profit"));


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
                        BilanFinancier bilanFinancier = getTableView().getItems().get(getIndex());
                        updateBilanFinancier(bilanFinancier);
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
                        BilanFinancier bilanFinancier = getTableView().getItems().get(getIndex());
                        deleteBilanFinancier(bilanFinancier);
                    });
                    setGraphic(button);
                    setText(null);
                }
            }
        });
    }


    public void refreshTable() {
        bilanFinanciers.clear();
        List<BilanFinancier> bilanFinancierList = bilanFinancierService.readAll();
        bilanFinanciers.addAll(bilanFinancierList);
        bilanFinancierTable.setItems(bilanFinanciers);
    }

    public void ajouter(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterBilanFinancier.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            AjouterBilanFinancierController controller = loader.getController();
            controller.setCurrentScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBilanFinancier(BilanFinancier bilanFinancier) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BilanFinancier.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            BilanFinancierController controller = loader.getController();
            controller.setCurrentScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteBilanFinancier(BilanFinancier bilanFinancier) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce bilan ?");
        alert.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            bilanFinancierService.delete(bilanFinancier);
            refreshTable();
        }
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
}


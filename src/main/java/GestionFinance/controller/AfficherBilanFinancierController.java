package GestionFinance.controller;

import GestionFinance.entites.BilanFinancier;
import GestionFinance.service.BilanFinancierService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AfficherBilanFinancierController {
    private final ObservableList<BilanFinancier> bilanFinanciers = FXCollections.observableArrayList();

    @FXML
    private TableView<BilanFinancier> bilanFinancierTable;

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

    private final BilanFinancierService bilanFinancierService = new BilanFinancierService();

    @FXML
    public void initialize() {
        // Initialize table columns
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        salairesCoachsColumn.setCellValueFactory(new PropertyValueFactory<>("salaires_coachs"));
        prixLocationColumn.setCellValueFactory(new PropertyValueFactory<>("prix_location"));
        revenusAbonnementsColumn.setCellValueFactory(new PropertyValueFactory<>("revenus_abonnements"));
        revenusProduitsColumn.setCellValueFactory(new PropertyValueFactory<>("revenus_produits"));
        depensesColumn.setCellValueFactory(new PropertyValueFactory<>("depenses"));
        profitColumn.setCellValueFactory(new PropertyValueFactory<>("profit"));
        // Load data into the table
        refreshTable();
    }

    private void refreshTable() {
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
}


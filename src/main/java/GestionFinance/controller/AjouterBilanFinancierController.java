package GestionFinance.controller;

import GestionFinance.entites.Abonnement;
import GestionFinance.entites.BilanFinancier;
import GestionFinance.entites.Etat;
import GestionFinance.entites.Type;
import GestionFinance.service.BilanFinancierService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AjouterBilanFinancierController {
    private Scene currentScene;

    @FXML
    private DatePicker dateDebutId;

    @FXML
    private DatePicker dateFinId;

    @FXML
    private TextField depensesId;

    @FXML
    private TextField prixLocationId;

    @FXML
    private TextField profitId;

    @FXML
    private TextField revenusAbonnementsId;

    @FXML
    private TextField revenusProduitsId;

    @FXML
    private TextField salairesCoachsId;

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }


    @FXML
    void ajouter(ActionEvent event) {
        LocalDate dateDebut = dateDebutId.getValue();
        LocalDate dateFin = dateFinId.getValue();
        double salairesCoachs = Double.parseDouble(salairesCoachsId.getText());
        double prixLocation = Double.parseDouble(prixLocationId.getText());
        double revenusAbonnements = Double.parseDouble(revenusAbonnementsId.getText());
        double revenusProduits = Double.parseDouble(revenusProduitsId.getText());
        double depenses = Double.parseDouble(depensesId.getText());
        double profit = Double.parseDouble(profitId.getText());

        BilanFinancier bilanFinancier = new BilanFinancier(dateDebut, dateFin, salairesCoachs,prixLocation,revenusAbonnements,revenusProduits,depenses,profit );

        BilanFinancierService bs = new BilanFinancierService();
        bs.add(bilanFinancier);
        clearFields();

        // Rediriger vers l'interface Afficher Abonnements
        redirectToAfficherBilanFinancier();

    }
    private void clearFields() {
        dateDebutId.getEditor().clear();
        dateFinId.getEditor().clear();
        salairesCoachsId.clear();
        prixLocationId.clear();
        revenusAbonnementsId.clear();
        revenusProduitsId.clear();
        depensesId.clear();
        profitId.clear();
    }

    private void redirectToAfficherBilanFinancier() {
        Stage stage = (Stage) currentScene.getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherBilanFinancier.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            AfficherAbonnementsController controller = loader.getController();
            controller.refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


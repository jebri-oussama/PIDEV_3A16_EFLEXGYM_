package GestionFinance.controller;

import GestionFinance.entites.BilanFinancier;
import GestionFinance.service.BilanFinancierService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class BilanFinancierController {
    @FXML
    private Label revenusAbonnementsLabel;

    @FXML
    private Label revenusProduitsLabel;
    @FXML
    private Button dashboardId;
    @FXML
    private Button bilanFinancierId1;
    @FXML
    private Button abonnementsId;

    @FXML
    private Label salairesCoachsLabel;
    private Scene currentScene;

    @FXML
    private Label profitLabel;

    private BilanFinancierService bilanFinancierService = new BilanFinancierService();

    private int bilanFinancierId; // Ajouter cet attribut pour stocker l'ID du bilan financier

    @FXML
    public void obtenirRevenusAbonnements() {
        int id = bilanFinancierId; // Utiliser l'ID du bilan financier stocké
        BilanFinancier bilanFinancier = bilanFinancierService.readById(id);
        double nouveauxRevenusAbonnements = bilanFinancier.recupererRevenuAbonnements();
        revenusAbonnementsLabel.setText(Double.toString(nouveauxRevenusAbonnements));
        bilanFinancierService.updateRevenusAbonnements(id, nouveauxRevenusAbonnements);
    }

    @FXML
    public void obtenirRevenusProduits() {
        int id = bilanFinancierId; // Utiliser l'ID du bilan financier stocké
        BilanFinancier bilanFinancier = bilanFinancierService.readById(id);
        double nouveauxRevenusProduits = bilanFinancier.recupererRevenusProduits();
        revenusProduitsLabel.setText(Double.toString(nouveauxRevenusProduits));
        bilanFinancierService.updateRevenusProduits(id, nouveauxRevenusProduits);
    }

    @FXML
    public void obtenirSalairesCoachs() {
        int id = bilanFinancierId; // Utiliser l'ID du bilan financier stocké
        BilanFinancier bilanFinancier = bilanFinancierService.readById(id);
        double nouveauxSalairesCoachs = bilanFinancier.recupererSalairesCoachs();
        salairesCoachsLabel.setText(Double.toString(nouveauxSalairesCoachs));
        bilanFinancierService.updateSalairesCoachs(id, nouveauxSalairesCoachs);
    }

    @FXML
    public void calculerProfit() {
        int id = bilanFinancierId; // Utiliser l'ID du bilan financier stocké
        BilanFinancier bilanFinancier = bilanFinancierService.readById(id);

        // Récupération des valeurs des revenus et des salaires
        double nouveauxRevenusAbonnements = bilanFinancier.recupererRevenuAbonnements();
        double nouveauxRevenusProduits = bilanFinancier.recupererRevenusProduits();
        double nouveauxSalairesCoachs = bilanFinancier.recupererSalairesCoachs();

        // Mise à jour des champs du bilan financier
        bilanFinancier.setRevenus_abonnements(nouveauxRevenusAbonnements);
        bilanFinancier.setRevenus_produits(nouveauxRevenusProduits);
        bilanFinancier.setSalaires_coachs(nouveauxSalairesCoachs);

        // Mise à jour des valeurs dans le service
        bilanFinancierService.updateRevenusAbonnements(id, nouveauxRevenusAbonnements);
        bilanFinancierService.updateRevenusProduits(id, nouveauxRevenusProduits);
        bilanFinancierService.updateSalairesCoachs(id, nouveauxSalairesCoachs);

        // Calcul du profit
        double depenses = bilanFinancier.getDepenses();
        double prixLocation = bilanFinancier.getPrix_location();
        double profit = bilanFinancier.calculerProfit(prixLocation, depenses);

        // Mise à jour du profit dans le bilan financier
        bilanFinancier.setProfit(profit);
        bilanFinancierService.updateProfit(id, profit);

        // Affichage du profit dans le label
        profitLabel.setText(Double.toString(profit));

        // Nettoyage des champs
        clearFields();

        // Fermeture de la fenêtre actuelle
        profitLabel.getScene().getWindow().hide();

        // Redirection vers la vue AfficherBilanFinancier
        redirectToAfficherBilanFinancier();
    }


    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }

    public void setBilanFinancierId(int id) {
        this.bilanFinancierId = id;
    }

    private void redirectToAfficherBilanFinancier() {
        Stage stage = (Stage) currentScene.getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherBilanFinancier.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            AfficherBilanFinancierController controller = loader.getController();
            controller.refreshTable();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {


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


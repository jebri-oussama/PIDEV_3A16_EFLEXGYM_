package GestionFinance.controller;

import GestionFinance.entites.BilanFinancier;
import GestionFinance.service.BilanFinancierService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BilanFinancierController {
    @FXML
    private TextField idField;

    @FXML
    private Label revenusAbonnementsLabel;

    @FXML
    private Label revenusProduitsLabel;

    @FXML
    private Label salairesCoachsLabel;

    @FXML
    private Label profitLabel;

    private BilanFinancierService bilanFinancierService = new BilanFinancierService();

    @FXML
    public void obtenirRevenusAbonnements() {
        int id = Integer.parseInt(idField.getText());
        BilanFinancier bilanFinancier = bilanFinancierService.readById(id);
        double nouveauxRevenusAbonnements = bilanFinancier.recupererRevenuAbonnements();
        revenusAbonnementsLabel.setText(Double.toString(nouveauxRevenusAbonnements));
        bilanFinancierService.updateRevenusAbonnements(id, nouveauxRevenusAbonnements);
    }

    @FXML
    public void obtenirRevenusProduits() {
        int id = Integer.parseInt(idField.getText());
        BilanFinancier bilanFinancier = bilanFinancierService.readById(id);
        double nouveauxRevenusProduits = bilanFinancier.recupererRevenusProduits();
        revenusProduitsLabel.setText(Double.toString(nouveauxRevenusProduits));
        bilanFinancierService.updateRevenusProduits(id, nouveauxRevenusProduits);
    }

    @FXML
    public void obtenirSalairesCoachs() {
        int id = Integer.parseInt(idField.getText());
        BilanFinancier bilanFinancier = bilanFinancierService.readById(id);
        double nouveauxSalairesCoachs = bilanFinancier.recupererSalairesCoachs();
        salairesCoachsLabel.setText(Double.toString(nouveauxSalairesCoachs));
        bilanFinancierService.updateSalairesCoachs(id, nouveauxSalairesCoachs);
    }

    @FXML
    public void calculerProfit() {
        int id = Integer.parseInt(idField.getText());
        BilanFinancier bilanFinancier = bilanFinancierService.readById(id);

        // Mettre à jour les revenus des abonnements, des revenus des produits et des salaires des coachs
        double nouveauxRevenusAbonnements = bilanFinancier.recupererRevenuAbonnements();
        double nouveauxRevenusProduits = bilanFinancier.recupererRevenusProduits();
        double nouveauxSalairesCoachs = bilanFinancier.recupererSalairesCoachs();

        // Mettre à jour les valeurs dans l'objet BilanFinancier
        bilanFinancier.setRevenus_abonnements(nouveauxRevenusAbonnements);
        bilanFinancier.setRevenus_produits(nouveauxRevenusProduits);
        bilanFinancier.setSalaires_coachs(nouveauxSalairesCoachs);

        // Mettre à jour les valeurs dans la base de données
        bilanFinancierService.updateRevenusAbonnements(id, nouveauxRevenusAbonnements);
        bilanFinancierService.updateRevenusProduits(id, nouveauxRevenusProduits);
        bilanFinancierService.updateSalairesCoachs(id, nouveauxSalairesCoachs);

        // Récupérer le prix de location et les dépenses du bilan financier depuis la base de données
        double prixLocation = bilanFinancier.getPrix_location();
        double depenses = bilanFinancier.getDepenses();

        // Calculer le profit en utilisant les données récupérées de la base de données
        double profit = bilanFinancier.calculerProfit();

        // Mettre à jour la valeur du profit dans la base de données
        bilanFinancierService.updateProfit(id, profit);

        profitLabel.setText(Double.toString(profit));
    }



}

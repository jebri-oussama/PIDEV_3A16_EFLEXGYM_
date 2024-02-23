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
        double revenusAbonnements = bilanFinancier.recupererRevenuAbonnements();
        revenusAbonnementsLabel.setText(Double.toString(revenusAbonnements));
    }

    @FXML
    public void obtenirRevenusProduits() {
        int id = Integer.parseInt(idField.getText());
        BilanFinancier bilanFinancier = bilanFinancierService.readById(id);
        double revenusProduits = bilanFinancier.recupererRevenusProduits();
        revenusProduitsLabel.setText(Double.toString(revenusProduits));
    }

    @FXML
    public void obtenirSalairesCoachs() {
        int id = Integer.parseInt(idField.getText());
        BilanFinancier bilanFinancier = bilanFinancierService.readById(id);
        double salairesCoachs = bilanFinancier.recupererSalairesCoachs();
        salairesCoachsLabel.setText(Double.toString(salairesCoachs));
    }

    @FXML
    public void calculerProfit() {
        int id = Integer.parseInt(idField.getText());
        BilanFinancier bilanFinancier = bilanFinancierService.readById(id);
        double profit = bilanFinancier.calculerProfit();
        profitLabel.setText(Double.toString(profit));
    }
}

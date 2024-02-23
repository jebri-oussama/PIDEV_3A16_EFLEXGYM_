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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;

public class AjouterBilanFinancierController {

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


    @FXML
    void ajouter(ActionEvent event) {
        LocalDate dateDebut = dateDebutId.getValue();
        LocalDate dateFin = dateFinId.getValue();
        double depenses = Double.parseDouble(depensesId.getText());
        double prixLocation = Double.parseDouble(prixLocationId.getText());
        double profit = Double.parseDouble(profitId.getText());
        double revenusAbonnements = Double.parseDouble(revenusAbonnementsId.getText());
        double revenusProduits = Double.parseDouble(revenusProduitsId.getText());
        double salairesCoachs = Double.parseDouble(salairesCoachsId.getText());

        BilanFinancierService bs = new BilanFinancierService();

     /* BilanFinancier bilanFinancier = new BilanFinancier(dateDebut, dateFin, salairesCoachs, prixLocation, revenusAbonnements, revenusProduits, depenses, profit);
        bs.add(bilanFinancier);
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/AfficherBilanFinancier.fxml"));
        try {
            Parent root = loader.load();
            AfficherBilanFinancierController ac = loader.getController();
            ac.setIdRevenusDesAbonnements(revenusAbonnements);
            ac.setIdDateDebut(dateDebut);
            ac.setIdDateFin(dateFin);
            ac.setIdDepenses(depenses);
            ac.setIdPrixLocation(prixLocation);
            ac.setIdRevenusDesProduits(revenusProduits);
            ac.setIdSalairesDesCoachs(salairesCoachs);
            ac.setIdList(bs.readAll().toString());
            dateDebutId.getScene().setRoot(root);


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }*/
    }
}
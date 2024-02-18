package GestionFinance.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AfficherBilanFinancierController {
    @FXML
    private TextField RevenusDesAbonnementsId;

    @FXML
    private DatePicker dateDebutId;

    @FXML
    private DatePicker dateFinId;

    @FXML
    private TextField depensesId;

    @FXML
    private TextField listId;

    @FXML
    private TextField prixLocationId;

    @FXML
    private TextField profitId;

    @FXML
    private TextField revenusDesProduitsId;

    @FXML
    private TextField salairesDesCoachsId;

    public void setRevenusDesAbonnementsId(TextField revenusDesAbonnementsId) {
        RevenusDesAbonnementsId = revenusDesAbonnementsId;
    }

    public void setDateDebutId(DatePicker dateDebutId) {
        this.dateDebutId = dateDebutId;
    }

    public void setDateFinId(DatePicker dateFinId) {
        this.dateFinId = dateFinId;
    }

    public void setDepensesId(TextField depensesId) {
        this.depensesId = depensesId;
    }

    public void setListId(TextField listId) {
        this.listId = listId;
    }

    public void setPrixLocationId(TextField prixLocationId) {
        this.prixLocationId = prixLocationId;
    }

    public void setProfitId(TextField profitId) {
        this.profitId = profitId;
    }

    public void setRevenusDesProduitsId(TextField revenusDesProduitsId) {
        this.revenusDesProduitsId = revenusDesProduitsId;
    }

    public void setSalairesDesCoachsId(TextField salairesDesCoachsId) {
        this.salairesDesCoachsId = salairesDesCoachsId;
    }
}

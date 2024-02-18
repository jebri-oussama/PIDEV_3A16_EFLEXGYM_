package GestionFinance.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class AfficherBilanFinancierController {
    @FXML
    private DatePicker idDateDebut;

    @FXML
    private DatePicker idDateFin;

    @FXML
    private TextField idDepenses;

    @FXML
    private TextField idList;

    @FXML
    private TextField idPrixLocation;

    @FXML
    private TextField idProfit;

    @FXML
    private TextField idRevenusDesAbonnements;

    @FXML
    private TextField idRevenusDesProduits;

    @FXML
    private TextField idSalairesDesCoachs;

    public void setIdRevenusDesAbonnements(double idRevenusDesAbonnements) {
        this.idRevenusDesAbonnements.setText(String.valueOf(idRevenusDesAbonnements));
    }

    public void setIdDateDebut(LocalDate idDateDebut) {
        this.idDateDebut.setValue(idDateDebut);
    }

    public void setIdDateFin(LocalDate idDateFin) {

        this.idDateFin.setValue(idDateFin);
    }

    public void setIdDepenses(Double idDepenses) {

        this.idDepenses.setText(String.valueOf(idDepenses));
    }

    public void setIdList(String idList) {

        this.idList.setText(idList);
    }

    public void setIdPrixLocation(Double idPrixLocation) {

        this.idPrixLocation.setText(String.valueOf(idPrixLocation));
    }

    public void setIdProfit(Double idProfit) {

        this.idProfit.setText(String.valueOf(idProfit));
    }

    public void setIdRevenusDesProduits(Double idRevenusDesProduits) {
        this.idRevenusDesProduits.setText(String.valueOf(idRevenusDesProduits));
    }

    public void setIdSalairesDesCoachs(Double idSalairesDesCoachs) {
        this.idSalairesDesCoachs.setText(String.valueOf(idSalairesDesCoachs));
    }
}
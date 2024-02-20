package GestionFinance.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class AfficherBilanFinancierController {
    @FXML
    private TableColumn<?, ?> deleteColumn;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> idDateDebut;

    @FXML
    private TableColumn<?, ?> idDateFin;

    @FXML
    private TableColumn<?, ?> idDepenses;

    @FXML
    private TableColumn<?, ?> idPrixLocation;

    @FXML
    private TableColumn<?, ?> idProfit;

    @FXML
    private TableColumn<?, ?> idRevenusDesAbonnements;

    @FXML
    private TableColumn<?, ?> idRevenusDesProduits;

    @FXML
    private TableColumn<?, ?> idSalairesDesCoachs;

    @FXML
    private TableView<?> tableViewEvenements;

    public TableColumn<?, ?> getDeleteColumn() {
        return deleteColumn;
    }

    public void setDeleteColumn(TableColumn<?, ?> deleteColumn) {
        this.deleteColumn = deleteColumn;
    }

    public TableColumn<?, ?> getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(TableColumn<?, ?> idColumn) {
        this.idColumn = idColumn;
    }

    public TableColumn<?, ?> getIdDateDebut() {
        return idDateDebut;
    }

    public void setIdDateDebut(TableColumn<?, ?> idDateDebut) {
        this.idDateDebut = idDateDebut;
    }

    public TableColumn<?, ?> getIdDateFin() {
        return idDateFin;
    }

    public void setIdDateFin(TableColumn<?, ?> idDateFin) {
        this.idDateFin = idDateFin;
    }

    public TableColumn<?, ?> getIdDepenses() {
        return idDepenses;
    }

    public void setIdDepenses(TableColumn<?, ?> idDepenses) {
        this.idDepenses = idDepenses;
    }

    public TableColumn<?, ?> getIdPrixLocation() {
        return idPrixLocation;
    }

    public void setIdPrixLocation(TableColumn<?, ?> idPrixLocation) {
        this.idPrixLocation = idPrixLocation;
    }

    public TableColumn<?, ?> getIdProfit() {
        return idProfit;
    }

    public void setIdProfit(TableColumn<?, ?> idProfit) {
        this.idProfit = idProfit;
    }

    public TableColumn<?, ?> getIdRevenusDesAbonnements() {
        return idRevenusDesAbonnements;
    }

    public void setIdRevenusDesAbonnements(TableColumn<?, ?> idRevenusDesAbonnements) {
        this.idRevenusDesAbonnements = idRevenusDesAbonnements;
    }

    public TableColumn<?, ?> getIdRevenusDesProduits() {
        return idRevenusDesProduits;
    }

    public void setIdRevenusDesProduits(TableColumn<?, ?> idRevenusDesProduits) {
        this.idRevenusDesProduits = idRevenusDesProduits;
    }

    public TableColumn<?, ?> getIdSalairesDesCoachs() {
        return idSalairesDesCoachs;
    }

    public void setIdSalairesDesCoachs(TableColumn<?, ?> idSalairesDesCoachs) {
        this.idSalairesDesCoachs = idSalairesDesCoachs;
    }

    public TableView<?> getTableViewEvenements() {
        return tableViewEvenements;
    }

    public void setTableViewEvenements(TableView<?> tableViewEvenements) {
        this.tableViewEvenements = tableViewEvenements;
    }
}
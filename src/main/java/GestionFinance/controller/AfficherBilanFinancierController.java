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



}
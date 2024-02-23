package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import gestion_suivi.entitis.Suivi_Progre;
import gestion_suivi.service.Suivi_Progre_Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AfficherSuiviProgresController {

    @FXML
    private TableView<Suivi_Progre> tableView;

    @FXML
    private TableColumn<Suivi_Progre, String> nomColumn;

    @FXML
    private TableColumn<Suivi_Progre, String> prenomColumn;

    @FXML
    private TableColumn<Suivi_Progre, Integer> ageColumn;

    @FXML
    private TableColumn<Suivi_Progre, Double> tailleColumn;

    @FXML
    private TableColumn<Suivi_Progre, Double> poidsColumn;

    @FXML
    private TableColumn<Suivi_Progre, Double> tourDeTailleColumn;

    @FXML
    private TableColumn<Suivi_Progre, String> sexeColumn;

    @FXML
    private TableColumn<Suivi_Progre, String> userColumn;

    private Suivi_Progre_Service suiviProgService;

    public AfficherSuiviProgresController() {
        suiviProgService = new Suivi_Progre_Service();
    }

    @FXML
    private void initialize() {
        // Initialisation des colonnes avec PropertyValueFactory
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        tailleColumn.setCellValueFactory(new PropertyValueFactory<>("taille"));
        poidsColumn.setCellValueFactory(new PropertyValueFactory<>("poids"));
        tourDeTailleColumn.setCellValueFactory(new PropertyValueFactory<>("tour_de_taille"));
        sexeColumn.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));


        // Charger les données dans le TableView
        loadSuiviProgres();
    }

    private void loadSuiviProgres() {
        ObservableList<Suivi_Progre> suiviProgData = FXCollections.observableArrayList(suiviProgService.readAll());
        tableView.setItems(suiviProgData);
    }
}

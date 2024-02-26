package controller;

import gestion_suivi.entitis.Exercice;
import gestion_suivi.entitis.Programme_personnalise;
import gestion_suivi.entitis.Suivi_Progre;
import gestion_suivi.service.Exercice_Service;
import gestion_suivi.service.Programme_personnalise_service;
import gestion_suivi.service.Suivi_Progre_Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class ResultatController {

    @FXML
    private Label evaluationLabel;

    @FXML
    private Label programmeLabel;

    @FXML
    private TableView<Exercice> exercicesTableView;

    private Suivi_Progre suivi;
    private Suivi_Progre_Service cervice;
    private Programme_personnalise_service prog;
    private ObservableList<Exercice> exercices;
    private Programme_personnalise p;

    public ResultatController() {
        cervice = new Suivi_Progre_Service();
        prog = new Programme_personnalise_service();
    }

    public void initialize() {
        exercices = FXCollections.observableArrayList();
    }

    public void populateFieldsWithExercie(int id) {
        suivi = cervice.readById(id);
        String msg;

        if (suivi.getSexe().equals("homme")) {
            if (suivi.getIMC() < 20 && suivi.getIMG() < 15) {
                msg = "Mise en forme";
            } else if (suivi.getIMC() < 25 && suivi.getIMG() < 20) {
                msg = "Perte de poids";
            } else {
                msg = "Musculation";
            }
        } else {
            if (suivi.getIMC() < 19 && suivi.getIMG() < 25) {
                msg = "Mise en forme";
            } else if (suivi.getIMC() < 24 && suivi.getIMG() < 30) {
                msg = "Perte de poids";
            } else {
                msg = "Musculation";
            }
        }

        p = prog.readById(msg);

        exercices = FXCollections.observableArrayList(prog.readIdExerciceByIdProgramm(p.getId()));

        evaluationLabel.setText("Évaluation: " + suivi.getEvaluation());
        programmeLabel.setText(p.getObjectif());
        exercicesTableView.setItems(exercices);
    }
}

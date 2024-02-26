package controller;

import gestion_suivi.entitis.Exercice;
import gestion_suivi.entitis.Programme_personnalise;
import gestion_suivi.service.Exercice_Service;
import gestion_suivi.service.Programme_personnalise_service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class AffecterExerciceController {

    @FXML
    private TableView<Programme_personnalise> programTableView;

    @FXML
    private TableColumn<Programme_personnalise, String> programNameColumn;

    @FXML
    private TableView<Exercice> exerciseTableView;

    @FXML
    private TableColumn<Exercice, String> exerciseNameColumn;

    @FXML
    private Button addExerciceButton;

    @FXML
    private Button removeExerciseButton;

    @FXML
    private Button validateButton;

    private Exercice_Service ex;
    private Programme_personnalise_service prog;
    private List<Exercice> exercices;
    private List<Programme_personnalise> programmePersonnalises;
    private ObservableList<Exercice> selectedExercises;
//selectionner
    private Programme_personnalise selectedProgramme;

    public AffecterExerciceController() {
        ex = new Exercice_Service();
        prog = new Programme_personnalise_service();
        exercices = new ArrayList<>();
        programmePersonnalises = new ArrayList<>();
        selectedExercises = FXCollections.observableArrayList();
    }

    public void initialize() {
        programNameColumn.setCellValueFactory(new PropertyValueFactory<>("objectif"));
        exerciseNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        programmePersonnalises = prog.readAll();
        programTableView.getItems().addAll(programmePersonnalises);
        exercices = ex.readAll();
        exerciseTableView.getItems().addAll(exercices);
//si on change la selection
        programTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldProgramme, newProgramme) -> {
            selectedProgramme = newProgramme;
            System.out.println(selectedProgramme);
            selectedExercises.clear();
        });
    }

    @FXML
    private void addExerciseButtonClicked() {
        Exercice selectedExercise = exerciseTableView.getSelectionModel().getSelectedItem();
        if (selectedExercise != null && selectedExercises.size() < 4) {
            selectedExercises.add(selectedExercise);
            System.out.println(selectedExercises);
        }
        else{
            showAlert("Attention", "Il faut ajouter jusqu'a 4 exercices", Alert.AlertType.INFORMATION);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void removeExerciseButtonClicked() {
        Exercice selectedExercise = exerciseTableView.getSelectionModel().getSelectedItem();
        selectedExercises.remove(selectedExercise);
    }

    @FXML
    private void validateButtonClicked() {
        if (selectedProgramme != null) {
            System.out.println("Selected Program: " + selectedProgramme.getObjectif());
            System.out.println("Selected Exercises:");
            for (Exercice exercise : selectedExercises) {
                System.out.println("- " + exercise.getNom());
            }
            for (Exercice exercise : selectedExercises) {
                prog.addExercice(selectedProgramme,exercise);
            }
        }
    }
// effacer la selection
    @FXML
    private void programSelected() {
        selectedExercises.clear();
        selectedProgramme = programTableView.getSelectionModel().getSelectedItem();
    }
}

package controller;

import gestion_suivi.entitis.Exercice;
import gestion_suivi.entitis.Programme_personnalise;
import gestion_suivi.entitis.Suivi_Progre;
import gestion_suivi.pdf.PdfGenerator;
import gestion_suivi.service.Programme_personnalise_service;
import gestion_suivi.service.Suivi_Progre_Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;

public class ResultatController {

    @FXML
    private AnchorPane root;

    @FXML
    private Button voirStatButton;

    @FXML
    private Label evaluationLabel;

    @FXML
    private Label programmeLabel;

    @FXML
    private TextField search; // Change to TextField for search input
    @FXML
    private Button generatePDFButton;
    @FXML
    private TableView<Exercice> exercicesTableView;

    private Suivi_Progre suivi;
    private Suivi_Progre_Service service;
    private Programme_personnalise_service prog;
    private ObservableList<Exercice> exercices;
    private Programme_personnalise p;

    public ResultatController() {
        service = new Suivi_Progre_Service();
        prog = new Programme_personnalise_service();
    }

    public void initialize() {
        TableColumn<Exercice, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Exercice, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Exercice, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Exercice, Integer> nbrDeSetColumn = new TableColumn<>("Nombre de Sets");
        nbrDeSetColumn.setCellValueFactory(new PropertyValueFactory<>("nbrDeSet"));

        TableColumn<Exercice, String> groupeMusculaireColumn = new TableColumn<>("Groupe Musculaire");
        groupeMusculaireColumn.setCellValueFactory(new PropertyValueFactory<>("groupeMusculaire"));

        TableColumn<Exercice, Integer> nbrDeRepetitionsColumn = new TableColumn<>("Nombre de Répétitions");
        nbrDeRepetitionsColumn.setCellValueFactory(new PropertyValueFactory<>("nbrDeRepetitions"));

        TableColumn<Exercice, String> categorieExerciceColumn = new TableColumn<>("Catégorie Exercice");
        categorieExerciceColumn.setCellValueFactory(new PropertyValueFactory<>("categorieExercice"));

        TableColumn<Exercice, String> typeEquipementColumn = new TableColumn<>("Type d'Équipement");
        typeEquipementColumn.setCellValueFactory(new PropertyValueFactory<>("typeEquipement"));

        exercicesTableView.getColumns().addAll(idColumn, nameColumn, descriptionColumn, nbrDeSetColumn, groupeMusculaireColumn, nbrDeRepetitionsColumn, categorieExerciceColumn, typeEquipementColumn);
        root = (AnchorPane) evaluationLabel.getParent();
        generatePDFButton.setOnAction(event -> generatePDF());
    }

    public void populateFieldsWithExercie(int id) {
        suivi = service.readById(id);
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

    @FXML
    void redirectToStatistique(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/statistique.fxml"));
            Parent root = loader.load();
            StatistiqueController statistiqueController = loader.getController();

            // Retrieve IMC value from Suivi_Progre object
            double imc = suivi.getIMC();

            // Populate IMC statistics in StatistiqueController
            statistiqueController.populateEvaluationStatistics(imc);

            Scene scene = new Scene(root, 1180.0, 655.0);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchByName() {
        String searchTerm = search.getText().trim().toLowerCase();

        ObservableList<Exercice> filteredExercices = FXCollections.observableArrayList(
                exercices.stream()
                        .filter(exercice -> exercice.getNom().toLowerCase().contains(searchTerm))
                        .collect(Collectors.toList())
        );

        if (filteredExercices.isEmpty()) {
            showAlert("Aucun exercice trouvé", "Aucun exercice trouvé avec le nom spécifié.");
        } else {
            exercicesTableView.setItems(filteredExercices);
        }
    }

    // Method to display an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void generatePDF() {
        String outputPath = "dashboard.pdf"; // Chemin où enregistrer le fichier PDF
        PdfGenerator.generatePdf(root, outputPath); // Passer le nœud racine de la scène
    }
}

package controller;

import gestion_suivi.entitis.Exercice;
import gestion_suivi.service.Exercice_Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AjouterExerciceController {

    @FXML
    private TextField nomField;
    @FXML
    private Label titre;
    @FXML
    private TextField descriptionField;

    @FXML
    private TextField nbrSetsField;

    @FXML
    private TextField groupeMusculaireField;

    @FXML
    private TextField nbrRepetitionsField;

    @FXML
    private TextField categorieExerciceField;

    @FXML
    private TextField typeEquipementField;

    @FXML
    private Button enregistrerButton;

    private final Exercice_Service exerciceService;
    private Exercice exercice;

    public AjouterExerciceController() {
        this.exerciceService = new Exercice_Service();
    }

    @FXML
    private void initialize() {
        // Vous pouvez ajouter des initialisations ici si nécessaire
    }

    @FXML
    private void enregistrerButtonClicked() {
        String nom = nomField.getText();
        String description = descriptionField.getText();
        int nbrSets = Integer.parseInt(nbrSetsField.getText());
        String groupeMusculaire = groupeMusculaireField.getText();
        int nbrRepetitions = Integer.parseInt(nbrRepetitionsField.getText());
        String categorieExercice = categorieExerciceField.getText();
        String typeEquipement = typeEquipementField.getText();

        // Création de l'objet Exercice
        Exercice exercice = new Exercice(nom,description,nbrSets,groupeMusculaire,nbrRepetitions,categorieExercice,typeEquipement);

        // Appel de la méthode d'ajout dans le service Exercice_Service
        if(exerciceService.add(exercice)) {
            showAlert("Success", "Exercice ajoutée", Alert.AlertType.INFORMATION);
            clearFields();
            redirectToAffecterExercice();
        }

    }
    private void clearFields() {
        nomField.clear();
        descriptionField.clear();
        nbrSetsField.clear();
        groupeMusculaireField.clear();
        nbrRepetitionsField.clear();
        categorieExerciceField.clear();
        typeEquipementField.clear();
    }
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void populateFieldsWithExercie(int id) {
        Exercice_Service ex = new Exercice_Service();
        exercice = ex.readById(id);
        nomField.setText(exercice.getNom());
        descriptionField.setText(exercice.getDescription());
        nbrSetsField.setText(String.valueOf(exercice.getNbrDeSet()));
        groupeMusculaireField.setText(exercice.getGroupeMusculaire());
        nbrRepetitionsField.setText(String.valueOf(exercice.getNbrDeRepetitions()));
        categorieExerciceField.setText(exercice.getCategorieExercice());
        typeEquipementField.setText(exercice.getTypeEquipement());

        titre.setText("Mettre à jour votre Exercice");
    }
    private void redirectToAffecterExercice() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherExercice.fxml"));
            Scene scene = new Scene(root, 1180.0, 655.0);
            Stage stage = (Stage) enregistrerButton.getScene().getWindow(); // Assuming saveButton is present in NewClub.fxml
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

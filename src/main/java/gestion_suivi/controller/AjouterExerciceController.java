package gestion_suivi.controller;

import gestion_suivi.entitis.Exercice;
import gestion_suivi.service.Exercice_Service;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TextField groupeMusculaireField;
    @FXML
    private TextField categorieExerciceField;
    @FXML
    private TextField typeEquipementField;
    @FXML
    private Button enregistrerButton;
    private final Exercice_Service exerciceService;
    private Exercice exercice;
    @FXML
    private ChoiceBox<String> nbrSetsChoiceBox;
    @FXML
    private ChoiceBox<String> nbrRepsChoiceBox;

    public AjouterExerciceController() {
        this.exerciceService = new Exercice_Service();
    }

    @FXML
    private void initialize() {
        // Vous pouvez ajouter des initialisations ici si nécessaire
        nbrSetsChoiceBox.setItems(FXCollections.observableArrayList(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        nbrRepsChoiceBox.setItems(FXCollections.observableArrayList(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
    }

    @FXML
    private void enregistrerButtonClicked() {
        String nom = nomField.getText();
        String description = descriptionField.getText();
        String groupeMusculaire = groupeMusculaireField.getText();
        String categorieExercice = categorieExerciceField.getText();
        String typeEquipement = typeEquipementField.getText();

        // Vérifier si tous les champs obligatoires sont remplis
        if (nom.isEmpty() || description.isEmpty() || groupeMusculaire.isEmpty() ||
                categorieExercice.isEmpty() || typeEquipement.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
            return;
        }

        // Vérifier si les champs contenant des chaînes ne contiennent pas de chiffres
        if (!isString(nom) || !isString(description) || !isString(groupeMusculaire) || !isString(categorieExercice) || !isString(typeEquipement)) {
            showAlert("Erreur", "Verifier vos informations", Alert.AlertType.ERROR);
            return;
        }

        // Création de l'objet Exercice
        int nbrSets = Integer.parseInt(nbrSetsChoiceBox.getValue());
        int nbrRepetitions = Integer.parseInt(nbrRepsChoiceBox.getValue());
        Exercice exercice = new Exercice(nom, description, nbrSets, groupeMusculaire, nbrRepetitions, categorieExercice, typeEquipement);

        // Appel de la méthode d'ajout dans le service Exercice_Service
        if (exerciceService.add(exercice)) {
            showAlert("Success", "Exercice ajoutée", Alert.AlertType.INFORMATION);
            clearFields();
            redirectToAffecterExercice();
        }
    }

    private boolean isString(String value) {
        return value.matches("[a-zA-Z]+");
    }

    private void clearFields() {
        nomField.clear();
        descriptionField.clear();
        groupeMusculaireField.clear();
        categorieExerciceField.clear();
        typeEquipementField.clear();
        nbrSetsChoiceBox.setValue(null);
        nbrRepsChoiceBox.setValue(null);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void redirectToAffecterExercice() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherExercice.fxml"));
            Scene scene = new Scene(root, 1180.0, 655.0);
            Stage stage = (Stage) enregistrerButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateFieldsWithExercie(int id) {
        Exercice_Service ex = new Exercice_Service();
        exercice = ex.readById(id);
        nomField.setText(exercice.getNom());
        descriptionField.setText(exercice.getDescription());
        nbrSetsChoiceBox.setValue(String.valueOf(exercice.getNbrDeSet()));
        groupeMusculaireField.setText(exercice.getGroupeMusculaire());
        nbrRepsChoiceBox.setValue(String.valueOf(exercice.getNbrDeRepetitions()));
        categorieExerciceField.setText(exercice.getCategorieExercice());
        typeEquipementField.setText(exercice.getTypeEquipement());

        titre.setText("Mettre à jour votre Exercice");
    }
}

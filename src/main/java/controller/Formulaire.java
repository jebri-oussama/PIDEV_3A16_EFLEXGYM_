package controller;

import java.io.IOException;
import java.util.regex.Pattern;

import gestion_suivi.entitis.Suivi_Progre;
import gestion_suivi.service.Suivi_Progre_Service;
import gestion_user.entities.Role;
import gestion_user.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Formulaire {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField ageField;

    @FXML
    private RadioButton hommeRadioButton;

    @FXML
    private RadioButton femmeRadioButton;

    @FXML
    private TextField poidsField;

    @FXML
    private TextField tailleField;

    @FXML
    private TextField tourDeTailleField;

    private Suivi_Progre_Service suiviProgService;
    private int id;

    public Formulaire() {
        suiviProgService = new Suivi_Progre_Service();
    }

    @FXML
    private void enregistrerButtonClicked(ActionEvent event) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String ageText = ageField.getText();
        String poidsText = poidsField.getText();
        String tailleText = tailleField.getText();
        String tourDeTailleText = tourDeTailleField.getText();
        String sexe = hommeRadioButton.isSelected() ? "homme" : "femme";

        if (nom.isEmpty() || prenom.isEmpty() || ageText.isEmpty() || poidsText.isEmpty()
                || tailleText.isEmpty() || tourDeTailleText.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs", Alert.AlertType.ERROR);
            return;
        }

        if (!isAlpha(nom) || !isAlpha(prenom)) {
            showAlert("Erreur", "Veuillez vérifier vos informations", Alert.AlertType.ERROR);
            return;
        }

        if (!isInteger(ageText)) {
            showAlert("Erreur", "Veuillez vérifier vos informations", Alert.AlertType.ERROR);
            return;
        }

        if (!isDouble(poidsText) || !isDouble(tailleText) || !isDouble(tourDeTailleText)) {
            showAlert("Erreur", "Veuillez vérifier vos informations", Alert.AlertType.ERROR);
            return;
        }

        int age = Integer.parseInt(ageText);
        double poids = Double.parseDouble(poidsText);
        double taille = Double.parseDouble(tailleText);
        double tourDeTaille = Double.parseDouble(tourDeTailleText);

        User u1 = new User();
        u1.setId(3);
        u1.setRole(Role.Adherent);

        Suivi_Progre suiviProg = new Suivi_Progre(nom, prenom, age, taille, poids, tourDeTaille, sexe, u1);

        this.id = suiviProgService.add(suiviProg);
        if (id > 0) {
            showAlert("Succès", "Suivi ajouté", Alert.AlertType.INFORMATION);
            redirectToAffecterExercice(id);
        } else {
            showAlert("Erreur", "Veuillez vérifier vos informations", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void redirectToAffecterExercice(int ide) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resultat.fxml"));
            Parent root = loader.load();
            ResultatController exerciceController = loader.getController();
            exerciceController.populateFieldsWithExercie(ide);
            Scene scene = new Scene(root, 1180.0, 655.0);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isAlpha(String str) {
        return Pattern.matches("[a-zA-Z]+", str);
    }

    private boolean isInteger(String str) {
        return Pattern.matches("\\d+", str);
    }

    private boolean isDouble(String str) {
        return Pattern.matches("\\d+(\\.\\d+)?", str);
    }
}

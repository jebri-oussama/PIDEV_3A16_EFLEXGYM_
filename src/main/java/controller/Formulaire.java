package controller;

import gestion_suivi.entitis.Exercice;
import gestion_user.entities.Role;
import gestion_suivi.entitis.Sexe;
import gestion_suivi.entitis.Suivi_Progre;
import gestion_suivi.service.Suivi_Progre_Service;
import gestion_user.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


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
    private int id ;

    public Formulaire() {
        suiviProgService = new Suivi_Progre_Service();
    }

    // Event handler for the "Enregistrer" button click event
    @FXML
    private void enregistrerButtonClicked(ActionEvent event) {

        // Retrieve values from UI elements
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        int age = Integer.parseInt(ageField.getText());
        double poids = Double.parseDouble(poidsField.getText());
        double taille = Double.parseDouble(tailleField.getText());
        double tourDeTaille = Double.parseDouble(tourDeTailleField.getText());
        String sexe = hommeRadioButton.isSelected() ? "homme" : "femme";

        User u1 = new User();
        u1.setId(3);
        u1.setRole(Role.Adherent);

        // Create a new Suivi_Progre instance with the retrieved values
        Suivi_Progre suiviProg = new Suivi_Progre(nom, prenom, age, taille, poids, tourDeTaille, sexe, u1);

        //System.out.println(suiviProg.getIdUser().getRole());
        System.out.println(suiviProg);
        // Add the Suivi_Progre instance to the database using the service
        ; // Assuming the User object is not available here
        this.id=suiviProgService.add(suiviProg);
        if (id>0) {
            showAlert("Success", "Suivi ajoutée", Alert.AlertType.INFORMATION);
            redirectToAffecterExercice(id);
        } else {
            showAlert("Erreur", "Verifier vos informations", Alert.AlertType.INFORMATION);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resultat.fxml"));            Parent root = loader.load();
            ResultatController exerciceController = loader.getController();
            exerciceController.populateFieldsWithExercie(ide);
            Scene scene = new Scene(root, 1180.0, 655.0);
            Stage stage = new Stage(); // Create a new stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

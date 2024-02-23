package controller;

import gestion_suivi.entitis.Role;
import gestion_suivi.entitis.Sexe;
import gestion_suivi.entitis.Suivi_Progre;
import gestion_suivi.entitis.User;
import gestion_suivi.service.Suivi_Progre_Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;


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
        Sexe sexe = hommeRadioButton.isSelected() ? Sexe.homme : Sexe.femme;

        User u1 = new User();
        u1.setId(3);
        u1.setRole(Role.adherant);

        // Create a new Suivi_Progre instance with the retrieved values
        Suivi_Progre suiviProg = new Suivi_Progre(nom, prenom, age, taille, poids,tourDeTaille,sexe,u1);

        // Add the Suivi_Progre instance to the database using the service
        suiviProgService.add(suiviProg); // Assuming the User object is not available here

        // Optionally, you can provide feedback to the user about the success of the operation
        System.out.println("Suivi_Progre added successfully.");
    }
}
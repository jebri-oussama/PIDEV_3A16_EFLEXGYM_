package controllers;


import gestion_user.entities.User;
import gestion_user.entities.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdherentController {

    @FXML
    private Label dn;

    @FXML
    private Button editerProfile;

    @FXML
    private Label email;

    @FXML
    private Button logoutButton;

    @FXML
    private Label mdp;

    @FXML
    private Label nom;

    @FXML
    private Label prenom;

    @FXML
    private Label sexe;
    @FXML
    private Button logout;


    @FXML
    private Label NomPrenom;
    User loggedInUser = UserSession.getLoggedInUser();
    public void initialize() {

            User loggedInUser = UserSession.getLoggedInUser();
            NomPrenom.setText(loggedInUser.getFullName());
            nom.setText(loggedInUser.getNom());
            prenom.setText(loggedInUser.getPrenom());
            email.setText(loggedInUser.getEmail());
            mdp.setText(loggedInUser.getMot_de_passe());
            dn.setText(loggedInUser.getDate_de_naissance().toString());
            sexe.setText(loggedInUser.getSexe().toString());



    }
    @FXML
    void handleUpadate(ActionEvent event) {
        openUpdatePage(loggedInUser);
    }


    private void openUpdatePage(User selectedAdherent) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/UpdateAdherent.fxml"));
        try {
            Parent root = loader.load();

            UpdateAdherentController updateController = loader.getController();
            updateController.initData(selectedAdherent);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier un User");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/login.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene( new Scene(root));
            stage.setTitle("Gestion des Users");
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());}}

            public void handleUpdateEvent(User updatedUser) {
        // Update the UI with the new user information
        // For example, set the text fields with the updated user details

        nom.setText(updatedUser.getNom());
        prenom.setText(updatedUser.getPrenom());
        email.setText(updatedUser.getEmail());
        mdp.setText(updatedUser.getMot_de_passe());
        dn.setText(updatedUser.getDate_de_naissance().toString());
        sexe.setText(updatedUser.getSexe().toString());
        NomPrenom.setText(updatedUser.getFullName());


        // ... update other fields ...

        // Optionally, you might want to reload additional data or perform other actions
        // based on your application's requirements.
    }
}

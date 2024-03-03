package Controllers;


import Controllers.UpdateAdherentController;
import GestionFinance.controller.ConsulterAbonnementController;

import gestion_evenement.Controllers.ParticiperController;
import gestion_user.entities.User;
import gestion_user.entities.UserSession;
import gestion_user.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AdherentController {
    @FXML
    private Label eventsLabel;
    @FXML
    private Label panierlabel;
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

        // User loggedInUser = UserSession.getLoggedInUser();
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
        }}
    @FXML
    void handleEventsClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Participer.fxml"));
            Parent root = loader.load();

            ParticiperController participerController = loader.getController();
            participerController.initData(loggedInUser.getId());


            Stage stage = (Stage) eventsLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Participer");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handlePanierClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Product.fxml"));
            Parent root = loader.load();

           ProductController P = loader.getController();
            P.initData(loggedInUser.getId());



            Stage stage = (Stage) panierlabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("panier");
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

    @FXML
    void handleRefresh(ActionEvent event) {
        try {
            // Your existing refresh logic...


            User refreshedUser =  UserService.getUserByEmail(loggedInUser.getEmail());

            nom.setText(refreshedUser.getNom());
            prenom.setText(refreshedUser.getPrenom());
            email.setText(refreshedUser.getEmail());
            mdp.setText(refreshedUser.getMot_de_passe());
            dn.setText(refreshedUser.getDate_de_naissance().toString());
            sexe.setText(refreshedUser.getSexe().toString());
            NomPrenom.setText(refreshedUser.getFullName());

            // You can perform additional actions or reload data as needed
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    void handleConsultAbonn(){
        // Retrieve the currently logged-in user from the session
        User loggedInUser = UserSession.getLoggedInUser();

        if (loggedInUser != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConsulterAbonnement.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(loader.load());

                // Pass the logged-in user ID to the ConsulterAbonnementController
                ConsulterAbonnementController controller = loader.getController();
                controller.setAdherentId(loggedInUser.getId());

                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle the case where no user is logged in
            // You can display an error message or take appropriate action
        }
    }


}

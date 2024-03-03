package Controllers;

import gestion_user.entities.*;
import gestion_user.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label status;

    @FXML
    private void login(ActionEvent event) {
        String email = username.getText();
        String p = password.getText();

        UserService us =new UserService();
       if (us.connexion(email,p)){
           User loggedInUser = UserService.getUserByEmail(email);
           UserSession.setLoggedInUser(loggedInUser);

           // Redirect based on the role
           if (loggedInUser.getRole().toString().equals("Adherent")) {
               // Redirect to Adherent profile
               loadAdherentProfile();
           } else if (loggedInUser.getRole().toString().equals("Coach")) {
               // Redirect to Coach profile
               loadCoachProfile();
           } else {

        loadAjouterAdherentPage();}
    } else {
        status.setText("Authentification échouée. \n" +
                        "Veuillez vérifier vos informations .");
    }

    }



    private void loadCoachProfile() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Coach.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) username.getScene().getWindow();
            stage.setTitle("Profile Coach");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAdherentProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Adherent.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) username.getScene().getWindow();
            stage.setTitle("Profile Adherent");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadAjouterAdherentPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterAdherent.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) username.getScene().getWindow();
            stage.setTitle("Login Gestion Users");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*private void loadUserInfo() {
        // Retrieve user information from UserSession
        Double userSalaire = null;
        String userEmail = UserSession.getInstance().getUserEmail();
         String userRole = UserSession.getInstance().getUserRole();
        String userNom =UserSession.getInstance().getUserNom();
        String userPrenom=UserSession.getInstance().getUserPrenom();
        String userMDP=UserSession.getInstance().getUserMDP();
        Date userDateNaissance=UserSession.getInstance().getUserDateNaissance();
        String userSexe=UserSession.getInstance().getUserSexe();
        userSalaire=UserSession.getInstance().getUserSalaire();

loadAdherentProfile(userEmail,userNom,userPrenom,userMDP,userDateNaissance, Sexe.valueOf(userSexe), Role.valueOf(userRole),userSalaire);

        // Fetch additional information about the user from the database
        // You can use userEmail and userRole to fetch specific data

        // For example:
        // AdditionalUserInfo userInfo = userService.fetchAdditionalUserInfo(userEmail, userRole);
        // Display or store the additional information as needed
    }*/

@FXML
    private void handleForgotPassword() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forgotPassword.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) username.getScene().getWindow();
            stage.setTitle("Forgot Password");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
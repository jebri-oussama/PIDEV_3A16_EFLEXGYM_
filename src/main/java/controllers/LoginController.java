package controllers;

import gestion_user.entities.*;
import gestion_user.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.DataSource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.UUID;
import javafx.embed.swing.SwingFXUtils;

public class LoginController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label status;
    // Generate random code
    String captchaCode = CaptchaGenerator.generateRandomCode(6);

    // Generate image
    BufferedImage captchaImage = CaptchaImageGenerator.generateCaptchaImage(captchaCode);

// Display the image to the user (you need to handle this in your UI)
// You can convert BufferedImage to other formats based on your UI framework



    @FXML
    private void login(ActionEvent event) {
        String email = username.getText();
        String p = password.getText();
        String enteredCode = captchaInputField.getText();

        if (validateCaptcha(enteredCode)) {
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
        } else {
            status.setText("Code CAPTCHA incorrect.");
        }

    }
    private boolean validateCaptcha(String enteredCaptcha) {
        // Implement your CAPTCHA validation logic here
        // Compare enteredCaptcha with the generated CAPTCHA code
        // Return true if they match, otherwise return false
        // You may want to make this case-insensitive and consider using a dedicated CAPTCHA library
        return enteredCaptcha.equals(captchaCode);
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

    @FXML
    private ImageView captchaImageView;

    @FXML
    private TextField captchaInputField;

    public void initialize() {
        // Generate random code
        String captchaCode = CaptchaGenerator.generateRandomCode(6);

        // Generate image
        Image image = SwingFXUtils.toFXImage(CaptchaImageGenerator.generateCaptchaImage(captchaCode), null);

        // Set the image to the ImageView
        captchaImageView.setImage(image);

        // Save the CAPTCHA code for validation
        // You may want to store it as a member variable in your controller
        this.captchaCode = captchaCode;
    }



}
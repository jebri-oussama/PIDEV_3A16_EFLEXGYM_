package controllers;

import gestion_user.entities.MailSender;
import gestion_user.entities.User;
import gestion_user.entities.UserSession;
import gestion_user.service.UserService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.UUID;

public class ForgotPasswordController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField token;
    @FXML
    private Label verifemail;

    private String tokenGenerated;


    @FXML
    private void resetPassword(ActionEvent event) {
        // Get the user's email
        String userEmail = emailField.getText();
                    UserService as = new UserService();

        if (as.isEmailExists(userEmail)) {
            verifemail.setVisible(false);

            // Generate a random password reset token
            String resetToken = generateRandomToken();

            // Calculate the expiration time (e.g., 1 hour from now)
            LocalDateTime expirationTime = LocalDateTime.now().plusHours(1);

            // Store the reset token in the database
            new UserService().storeResetToken(userEmail, resetToken, expirationTime);

            // Send an email with the reset link using JavaMail API
            sendPasswordResetEmail(userEmail, resetToken);

            // Optionally, show a message to the user indicating that an email has been sent
            System.out.println("Password reset email sent to: " + userEmail);
        } else { verifemail.setText("Aucun compte se registrer avec ce Email");
        return;}
    }
    private String generateRandomToken() {
        // Implement logic to generate a random token (e.g., using UUID)
        return UUID.randomUUID().toString();
    }

    private void sendPasswordResetEmail(String userEmail, String resetToken) {
        // Implement logic to send an email with the reset link
        // Use JavaMail API or any other mailing library
        // Example: Send an email using JavaMail API
         MailSender.sendResetPasswordEmail(userEmail, resetToken);
        tokenGenerated= resetToken;
    }

    @FXML
    private void handleVerifyTokenButton() {
        String enteredToken = token.getText();
        if (isValidToken(enteredToken)) {
            String userEmail = emailField.getText();
            // Token is valid, navigate to the password update window
            // You can use FXMLLoader to load the new window
            // and set the appropriate controller for the password update window
            // Example:
            User loggedInUser = UserService.getUserByEmail(userEmail);
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
            // stage.show();
        } else {
            // Display an error message indicating that the token is invalid
            // For simplicity, we'll just print a message to the console
            System.out.println("Invalid token. Please check and try again.");
        }
    }


    private boolean isValidToken(String token) {
        // In a real-world scenario, you would compare the entered token
        // with the stored/generated token in your database or service.
        // For demonstration purposes, we'll use a hardcoded token.
        return tokenGenerated.equals(token);
    }



    private void loadCoachProfile() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Coach.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) emailField.getScene().getWindow();
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

            Stage stage = (Stage) emailField.getScene().getWindow();
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

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setTitle("Login Gestion Users");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReturn( ){
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/login.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene( new Scene(root));
            stage.setTitle("Gestion des Users");
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
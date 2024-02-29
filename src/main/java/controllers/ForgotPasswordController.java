package controllers;

import gestion_user.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.UUID;

public class ForgotPasswordController {
    @FXML
    private TextField emailField;

    @FXML
    private void resetPassword(ActionEvent event) {
        // Get the user's email
        String userEmail = emailField.getText();

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
    }

    private String generateRandomToken() {
        // Implement logic to generate a random token (e.g., using UUID)
        return UUID.randomUUID().toString();
    }

    private void sendPasswordResetEmail(String userEmail, String resetToken) {
        // Implement logic to send an email with the reset link
        // Use JavaMail API or any other mailing library
        // Example: Send an email using JavaMail API
        // MailSender.sendResetPasswordEmail(userEmail, resetToken);
    }

}
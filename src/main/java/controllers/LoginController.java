package controllers;

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
        String u = username.getText();
        String p = password.getText();

        UserService us =new UserService();
       if (us.connexion(u,p)){

        loadAjouterAdherentPage();
    } else {
        status.setText("Authentification échouée. \n" +
                        "Veuillez vérifier vos informations .");
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

}
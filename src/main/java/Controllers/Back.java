package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Back {
    @FXML
    private Tab gestionDashbordTab;
    @FXML
    private Tab gestionEvenementTab;
    @FXML
    private Tab gestionProduitTab;
    @FXML
    private Tab membersTab;
    @FXML
    private Tab settingsTab;
    @FXML
    private void initialize() {
        loadAfficherProduit();
        loadAfficherEvenement();
        loadADashbord();
        loadMembers();
        loadSettings();
    }

    private void loadMembers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAdherent.fxml"));
            Node membersContent = loader.load();
            membersTab.setContent(membersContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadAfficherEvenement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenement.fxml"));
            Node afficherEvenementContent = loader.load();
            gestionEvenementTab.setContent(afficherEvenementContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAfficherProduit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduit.fxml"));
            Node afficherProduitContent = loader.load();
            gestionProduitTab.setContent(afficherProduitContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void loadADashbord() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Node Dashbord = loader.load();
            gestionDashbordTab.setContent(Dashbord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherFormulaire.fxml"));
            Node settingsContent = loader.load();
            settingsTab.setContent(settingsContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        // Perform logout action
        showAlert("Logout");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package Gestion_planing.Controllers;

import Gestion_planing.entities.TypeCours;
import Gestion_planing.entities.cours;
import Gestion_planing.service.CoursService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;


public class AjouterCoursController {

        private final CoursService coursService = new CoursService();

        private Scene currentScene;


        @FXML
        private TextField nomId;


        @FXML
        private ComboBox<TypeCours> typeeId;


        @FXML
        private TextField duréeId;

        @FXML
        private Button ajouterId;

        public void setCurrentScene(Scene scene) {
            this.currentScene = scene;
        }


        @FXML
        void ajouter() {
            String nom = nomId.getText();
            TypeCours selectedType = typeeId.getValue();
            String duree = duréeId.getText();
            // Validate input
            if (nom.isEmpty() || duree.isEmpty() || selectedType == null ) {
                showAlert("Error", "All fields are required.");
                return;
            }


            // Création de l'abonnement avec les données récupérées
            cours cour = new cours(nom, selectedType, duree );

            // Ajout de l'abonnement à la base de données
            coursService.add(cour);

            // Effacer les champs après l'ajout
            clearFields();

            // Rediriger vers l'interface Afficher Abonnements
            redirectToAfficherCour();
        }

    private void clearFields() {
        nomId.clear();
        typeeId.getSelectionModel().clearSelection();
        duréeId.clear();
    }

    private void redirectToAfficherCour() {
        Stage stage = (Stage) currentScene.getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCour.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            AfficherCourController controller = loader.getController();
            controller.refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private void showAlert(String error, String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(null); // No header text
        alert.setContentText(s);
        alert.showAndWait();
    }
    
    }




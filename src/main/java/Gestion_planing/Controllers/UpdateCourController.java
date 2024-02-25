package Gestion_planing.Controllers;
import Gestion_planing.entities.TypeCours;
import Gestion_planing.entities.cours;
import Gestion_planing.service.CoursService;
import javafx.event.ActionEvent;
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

public class UpdateCourController {
        private final CoursService coursService = new CoursService();

        @FXML
        private Scene currentScene;


        @FXML
        private TextField nomId;


        @FXML
        private ComboBox<TypeCours> typeeId;


        @FXML
        private TextField duréeId;

        @FXML
        private Button confirmerId;

        public void initData(int id) {
            cours cour = coursService.readById(id);
            nomId.setText(cour.getNom().toString());
            typeeId.setValue(cour.getType());
            duréeId.setText(cour.getDuree().toString());
        }
        public void setCurrentScene(Scene scene) {
            this.currentScene = scene;
        }



        public void update(ActionEvent actionEvent) {
            // Récupération des valeurs des champs et des sélecteurs
            String nom = nomId.getText();
            TypeCours selectedType = typeeId.getValue();
            String duree = duréeId.getText();

            // Création de l'objet Abonnement avec les valeurs récupérées
            cours cour = new cours(nom, selectedType, duree);

            // Appel de la méthode update de CourService pour mettre à jour le cour dans la base de données
            coursService.update(cour);

            // Nettoyage des champs
            clearFields();

            // Fermeture de la fenêtre
            nomId.getScene().getWindow().hide();

            // Redirection vers l'interface Afficher cour
            redirectToAfficherCour();

        }


        private void redirectToAfficherCour() {
            Stage stage = (Stage) currentScene.getWindow();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                AfficherCourController controller = loader.getController();
                controller.refreshTable(); // Actualiser la table des abonnements
                stage.show(); // Assurez-vous d'afficher la nouvelle scène après la mise à jour
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void clearFields() {
            nomId.clear();
            typeeId.getSelectionModel().clearSelection();
            duréeId.clear();
        }
    }


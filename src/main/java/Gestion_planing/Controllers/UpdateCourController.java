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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
        Stage stage = (Stage) confirmerId.getScene().getWindow();
        stage.close();

        // Redirection vers l'interface Afficher cour
        redirectToAfficherCour();
    }

    private void redirectToAfficherCour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCour.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
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

package Gestion_planing.Controllers;

import Gestion_planing.Controllers.AfficherCourController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import Gestion_planing.entities.TypeCours;
import Gestion_planing.entities.cours;
import Gestion_planing.service.CoursService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterCoursController {

    private final CoursService coursService = new CoursService();
    private Scene currentScene;
    @FXML private TextField nomId;
    @FXML private ComboBox<TypeCours> typeId;
    @FXML private TextField duréeId;
    @FXML private Button ajouterId;

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }
    void initialize() {
        typeId.setItems(FXCollections.observableArrayList(TypeCours.values()));
        // Make sure the ComboBox is not disabled or locked
        typeId.setDisable(false);
        typeId.setCache(false);

    }
    @FXML
    void ajouter() {
        String nom = nomId.getText();
        TypeCours selectedType = typeId.getValue();
        String duree = duréeId.getText();

        // Validate input
        if (nom.isEmpty() || duree.isEmpty() || selectedType == null) {
            showAlert("Error", "All fields are required.");
            return;
        }

        // Create new course with the retrieved data
        cours cour = new cours(nom, selectedType, duree);

        // Add the course to the database
        coursService.add(cour);

        // Clear fields after adding
        clearFields();

        // Redirect to the "AfficherCour.fxml" interface
        redirectToAfficherCour();
    }

    private void clearFields() {
        nomId.clear();
        typeId.getSelectionModel().clearSelection();
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
            showAlert("Error", "Failed to redirect to AfficherCour.fxml");
        }
    }

    private void showAlert(String error, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package Gestion_planing.Controllers;

import Gestion_planing.entities.cours;
import Gestion_planing.entities.planning;
import Gestion_planing.service.CoursService;
import Gestion_planing.service.PlanningService;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PlanifierUnCourController {

    @FXML
    private TextField salleId;

    @FXML
    private TextField nbplaceId;

    @FXML
    private ComboBox<Integer> courId;

    @FXML
    private ComboBox<Integer> coachId;

    @FXML
    private TextField tempId;

    @FXML
    private DatePicker dateId;

    @FXML
    private Button ajouterId;

    private final PlanningService planningService = new PlanningService();
    private final UserService userService = new UserService();
    private final CoursService coursService = new CoursService();
    private Scene currentScene;

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }

    @FXML
    void initialize() {
        try {
            // Retrieve the list of users and extract their IDs
            List<User> users = userService.readAll();
            List<Integer> coachIds = users.stream()
                    .map(User::getId)
                    .collect(Collectors.toList());
            coachId.setItems(FXCollections.observableArrayList(coachIds));

            // Retrieve the list of courses and extract their IDs
            List<cours> coursList = coursService.readAll();
            List<Integer> courIds = coursList.stream()
                    .map(cours::getId)
                    .collect(Collectors.toList());
            courId.setItems(FXCollections.observableArrayList(courIds));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouter(ActionEvent event) {
        String salle = salleId.getText();
        int nbplace;
        Date date;
        String heur = tempId.getText();
        int coachIdSelected;
        int courIdSelected;
        try {
            nbplace = Integer.parseInt(nbplaceId.getText());
            date = Date.valueOf(dateId.getValue());
            coachIdSelected = coachId.getValue();
            courIdSelected = courId.getValue();
        } catch (NumberFormatException | NullPointerException e) {
            showAlert("Error", "Invalid input format.");
            return;
        }

        User user = userService.readById(coachIdSelected);
        cours cour = coursService.readById(courIdSelected);
        if (user == null || cour == null) {
            showAlert("Error", "User or course not found.");
            return;
        }

        // Validate input
        if (salle.isEmpty() || nbplace <= 0 || date == null || heur.isEmpty() || coachIdSelected <= 0 || courIdSelected <= 0) {
            showAlert("Error", "All fields are required.");
            return;
        }

        // Create the new planning entry
        planning p = new planning(salle, nbplace, date, heur, cour, user);

        // Add the planning entry to the database
        planningService.add(p);

        // Clear fields after adding
        clearFields();

        // Redirect to the interface to display the schedule
        redirectToAfficherPlaningAdmin();
    }

    private void clearFields() {
        salleId.clear();
        nbplaceId.clear();
        dateId.setValue(null);
        tempId.clear();
        coachId.getSelectionModel().clearSelection();
        courId.getSelectionModel().clearSelection();
    }

    private void redirectToAfficherPlaningAdmin() {
        Stage stage = (Stage) currentScene.getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlaningAdmine.fxml"));
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
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}

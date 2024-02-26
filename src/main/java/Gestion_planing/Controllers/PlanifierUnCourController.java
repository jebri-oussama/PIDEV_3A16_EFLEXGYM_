package Gestion_planing.Controllers;

import Gestion_planing.entities.cours;
import Gestion_planing.entities.planning;
import Gestion_planing.service.CoursService;
import Gestion_planing.service.PlanningService;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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

    @FXML
    void initialize() {
        List<User> users = userService.readAll();
        List<Integer> coachIds = users.stream().map(User::getId).toList();
        coachId.getItems().addAll(coachIds);

        List<cours> coursList = coursService.readAll();
        List<Integer> courIds = coursList.stream().map(cours::getId).toList();
        courId.getItems().addAll(courIds);

        dateId.setValue(LocalDate.now());
    }

    @FXML
    void ajouter(ActionEvent event) {
        String salle = salleId.getText();
        int nbplace;
        Date date;
        String heure = tempId.getText();
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

        // Retrieve the user associated with the coach ID
        User user = userService.readById(coachIdSelected);

        // Retrieve the course based on the selected course ID
        cours cour = coursService.readById(courIdSelected);

        if (salle.isEmpty() || nbplace <= 0 || heure.isEmpty() || coachIdSelected <= 0 || courIdSelected <= 0) {
            showAlert("Error", "All fields are required.");
            return;
        }

        // Ensure that the user retrieved is not null
        if (user == null) {
            showAlert("Error", "Selected coach does not exist.");
            return;
        }

        // Create the planning instance with the retrieved user and course
        planning p = new planning(salle, nbplace, date, heure, cour, user);
        planningService.add(p);

        clearFields();

        Stage stage = (Stage) ajouterId.getScene().getWindow();
        stage.close(); // Close the planning window after adding
    }


    private void clearFields() {
        salleId.clear();
        nbplaceId.clear();
        tempId.clear();
        coachId.getSelectionModel().clearSelection();
        courId.getSelectionModel().clearSelection();
        dateId.setValue(LocalDate.now());
    }

    private void showAlert(String error, String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }
}

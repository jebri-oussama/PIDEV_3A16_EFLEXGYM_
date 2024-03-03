package Gestion_planing.Controllers;

import Gestion_planing.entities.Reservation;
import Gestion_planing.entities.cours;
import Gestion_planing.entities.planning;
import Gestion_planing.service.CoursService;
import Gestion_planing.service.PlanningService;
import Gestion_planing.service.ReservationService;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ReserverUnCourController implements Initializable {
    @FXML
    private ComboBox<Integer> planingId;
    @FXML
    private TextField numtell;
    @FXML
    private TextField iduser;
    private final PlanningService planningService = new PlanningService();
    private final UserService userService = new UserService();
    private final ReservationService reservationService = new ReservationService();
    private Scene currentScene;
    public void initialize(URL location, ResourceBundle resources) {
        List<User> users = userService.readAll();
        List<Integer> idusers = users.stream().map(User::getId).toList();
        iduser.getText().equals(idusers);

        List<planning> planingList = planningService.readAll();
        List<Integer> planingIds = planingList.stream().map(planning::getId).toList();
        planingId.getItems().addAll(planingIds);
    }
    public void reserver(ActionEvent actionEvent) {
        int reservationSelected = planingId.getValue();
        planning p = planningService.readById(reservationSelected);
        //int id_user = Integer.parseInt(iduser.getText());
        //User user = userService.readById(id_user);
        String num_tell = numtell.getText();
        //Reservation r = new Reservation(user, p, num_tell);
        //reservationService.add(r);
        //clearFields();
        //Stage stage = (Stage) numtell.getScene().getWindow();
        //stage.close(); // Close the planning window after adding
        if (reservationSelected == 0 || num_tell.isEmpty() ) {
            showAlert("Champs requis", "Veuillez remplir tous les champs avant de réserver.");
            return; // Sortir de la méthode si un champ est vide ou si aucun cours n'est sélectionné
        }

        try {
            int id_user = Integer.parseInt(iduser.getText());
            User user = userService.readById(id_user);
            if (user == null) {
                // Afficher un message d'erreur si l'utilisateur n'existe pas
                showAlert("Utilisateur non trouvé", "L'utilisateur avec l'ID spécifié n'existe pas.");
                return; // Sortir de la méthode pour éviter la création de la réservation
            }
            // Créer la réservation si l'utilisateur existe
            Reservation r = new Reservation(user, p, num_tell);
            reservationService.add(r);
            clearFields();
            Stage stage = (Stage) numtell.getScene().getWindow();
            stage.close(); // Fermer la fenêtre de réservation après l'ajout
        } catch (NumberFormatException e) {
            // Afficher un message d'erreur si l'ID utilisateur n'est pas un entier
            showAlert("Erreur de saisie", "Veuillez entrer un ID utilisateur valide.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void clearFields() {
            planingId.getSelectionModel().clearSelection();
            numtell.clear();
            iduser.clear();
    }

    public void setCurrentScene(Scene scene) {

    this.currentScene = scene;
    } }



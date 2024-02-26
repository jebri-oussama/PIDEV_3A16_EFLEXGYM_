package Gestion_planing.Controllers;

import Gestion_planing.entities.cours;
import Gestion_planing.entities.planning;
import Gestion_planing.service.CoursService;
import Gestion_planing.service.PlanningService;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;



public class UpdateUnPlaning {
    private final PlanningService planningServiceng = new PlanningService();
    private final CoursService coursService = new CoursService();
    private final UserService userService = new UserService();

    private Scene currentScene;

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
    private int planingId;

    public void initData(int planingId) {
        this.planingId = planingId;
        planning p = planningServiceng.readById(planingId);
        salleId.setText(p.getSalle());
        nbplaceId.setText(String.valueOf(p.getNb_place_max()));
        dateId.setValue(p.getDate());
        tempId.setText(p.getHeure());
        courId.setValue(p.getCours().getId());
        coachId.setValue(p.getUser().getId());

    }
    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }

    @FXML
    void update(ActionEvent event) {

       /* if (salleId.getText().isEmpty() || nbplaceId.getValue() == null  || tempId.getText() || coachId.getValue() == null|| courId.getValue()) {
            dateId.getValue() == null {
                showAlert("Veuillez remplir tous les champs.");
                return;
            }*/

            Integer nbplacemax = Integer.parseInt(nbplaceId.getText());
            Date date = Date.valueOf(dateId.getValue());
            String salle = salleId.getText();
            String heur = tempId.getText();
            int coachIdSelected = coachId.getValue();
            int courIdSelected = courId.getValue();


            User user = userService.readById(coachIdSelected);
            cours cour = coursService.readById(courIdSelected);


            planning p = new planning(salle, nbplacemax, date, heur, cour, user);

            planningServiceng.update(p);


            clearFields();


            redirectToAfficherPlaningAdmine();
        }





    private void redirectToAfficherPlaningAdmine() {
        Stage stage = (Stage) currentScene.getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlaningAdmine.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            AfficherPlaningAdmineController controller = loader.getController();
            controller.refreshTable();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {

        salleId.clear();
        nbplaceId.clear();
        dateId.getEditor().clear();
        tempId.clear();
        courId.getSelectionModel().clearSelection();
        coachId.getSelectionModel().clearSelection();

    }
}






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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


public class UpdateUnPlaning implements Initializable {


    private Scene currentScene;

    @FXML
    private TextField salle;

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

    public   PlanningService planningServiceng = new PlanningService();
    public   CoursService coursService = new CoursService();
    public   UserService userService = new UserService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<User> users = userService.readAll();
        List<Integer> coachIds = users.stream().map(User::getId).toList();
        coachId.getItems().addAll(coachIds);

        List<cours> coursList = coursService.readAll();
        List<Integer> courIds = coursList.stream().map(cours::getId).toList();
        courId.getItems().addAll(courIds);

        dateId.setValue(LocalDate.now());
    }
    public void initData(int planingI) {
        this.planingId = planingI;
        planning p = planningServiceng.readById(planingId);
        salle.setText(p.getSalle());
        nbplaceId.setText(String.valueOf(p.getNb_place_max()));
        dateId.setValue(p.getDate());
        tempId.setText(p.getHeure());
        //courId.setValue(p.getId_cour().getId());
        //coachId.setValue(p.getId_coach().getId());

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

            int nbplacemax = Integer.parseInt(nbplaceId.getText());
            LocalDate localDate = dateId.getValue();
            Date date = Date.valueOf(localDate);
        String sall = salle.getText();
            String heur = tempId.getText();
            int coachIdSelected = coachId.getValue();
            int courIdSelected = courId.getValue();

            User user = userService.readById(coachIdSelected);
            cours cour = coursService.readById(courIdSelected);


            planning p = new planning(planingId,sall, nbplacemax, date.toLocalDate(), heur, cour, user);

            planningServiceng.update(p);





        Stage stage = (Stage) salle.getScene().getWindow();

        stage.close();
        }





    private void redirectToAfficherPlaningAdmine() {


    }

    private void clearFields() {

        salle.clear();
        nbplaceId.clear();
        dateId.getEditor().clear();
        tempId.clear();
        courId.getSelectionModel().clearSelection();
        coachId.getSelectionModel().clearSelection();

    }




}






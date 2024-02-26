package Gestion_planing.Controllers;

import Gestion_planing.entities.cours;
import Gestion_planing.entities.planning;
import Gestion_planing.service.CoursService;
import Gestion_planing.service.PlanningService;
import Gestion_planing.service.ReservationService;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.sql.Date;
import java.util.List;

public class ReserverUnCourController {
    @FXML
    private ComboBox<Integer> planingId;
    private final PlanningService planningService = new PlanningService();
    private final UserService userService = new UserService();
    private final ReservationService reservationService = new ReservationService();
    @FXML private Button reserverId;

    private Scene currentScene;
    List<planning> plannings = planningService.readAll();
    List<Integer> idplaning = plannings.stream().map(planning::getId).toList();
    planingId..addAll(idplaning);
    public void reserver(ActionEvent actionEvent) {
        int reservationSelected = planingId.getValue();
        planning p = planningService.readById(reservationSelected);


    }
    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }

}

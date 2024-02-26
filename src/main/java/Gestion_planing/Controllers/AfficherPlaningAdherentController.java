package Gestion_planing.Controllers;

import Gestion_planing.entities.planning;
import Gestion_planing.service.PlanningService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherPlaningAdherentController {
        @FXML
        private TableView<planning> planingTable;
        @FXML
        private TableColumn<planning, Void> modifierColumn;
        @FXML
        private TableColumn<planning, Void> supprimerColumn;

        @FXML
        private TableColumn<planning, String> salleColumn;
        @FXML
        private TableColumn<planning, Integer> nbplacemaxColumn;
        @FXML
        private TableColumn<planning, String> dateColumn;
        private TableColumn<planning, String> heureColumn;

        @FXML
        private TableColumn<planning, Integer> idcourColumn;
        @FXML
        private TableColumn<planning, Integer> iduserColumn;

        private final ObservableList<planning> plannings = FXCollections.observableArrayList();
        private PlanningService planningService;

        public void initialize(URL location, ResourceBundle resources) {
            planningService = new PlanningService();

            salleColumn.setCellValueFactory(new PropertyValueFactory<>("salle"));
            nbplacemaxColumn.setCellValueFactory(new PropertyValueFactory<>("nb_place_max"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            heureColumn.setCellValueFactory(new PropertyValueFactory<>("heur"));
            idcourColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCours().getId()).asObject());
            iduserColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getUser().getId()).asObject());

            refreshTable();

            modifierColumn.setCellValueFactory(new PropertyValueFactory<>("modifierButton"));
            modifierColumn.setCellFactory(param -> new TableCell<>() {
                private final Button button = new Button("Modifier");
            });

            supprimerColumn.setCellValueFactory(new PropertyValueFactory<>("supprimerButton"));
            supprimerColumn.setCellFactory(param -> new TableCell<>() {
                private final Button button = new Button("Supprimer");

            });
        }

        public void refreshTable() {
            plannings.clear();
            List<planning> planningList = planningService.readAll();
            plannings.addAll(planningList);
            planingTable.setItems(plannings);
        }

        @FXML
        public void reserver (ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReserverUnCour.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);

                PlanifierUnCourController controller = loader.getController();
                controller.setCurrentScene(scene);

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


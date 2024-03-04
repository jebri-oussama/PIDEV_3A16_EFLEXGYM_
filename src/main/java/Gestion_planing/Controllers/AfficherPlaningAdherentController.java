package Gestion_planing.Controllers;

import Gestion_planing.entities.Reservation;
import Gestion_planing.entities.planning;
import Gestion_planing.service.PlanningService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherPlaningAdherentController implements Initializable {
        @FXML
        private TableView<planning> planingTable;
        @FXML
        private TableColumn<planning, Void> ReserverColumn;

        @FXML
        private TableColumn<planning, String> salleColumn;
        @FXML
        private TableColumn<planning, Integer> nbplacemaxColumn;
        @FXML
        private TableColumn<planning, String> dateColumn;
        @FXML
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
            heureColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
            idcourColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId_cour().getId()).asObject());
            iduserColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId_coach().getId()).asObject());
            refreshTable();

            ReserverColumn.setCellFactory(param -> new TableCell<planning, Void>() {
                private final Button button = new Button("Reserver");

                {
                    button.setOnAction(event -> {
                        planning p = getTableView().getItems().get(getIndex());
                        ReserverPlaning(p);
                    });
                }
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setGraphic(button);
                        setText(null);
                    }
                }
            });
        }
        @FXML
        public void ReserverPlaning(planning p) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReserverUnCour.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);

                ReserverUnCourController controller = loader.getController();
                controller.setCurrentScene(scene);
                stage.show();
                refreshTable();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    @FXML
    public void OpenCalendar(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Calendar.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();


    }
    public void refreshTable() {
        plannings.clear();
        List<planning> planningList = planningService.readAll();
        plannings.addAll(planningList);
        planingTable.setItems(plannings);
    }
    }



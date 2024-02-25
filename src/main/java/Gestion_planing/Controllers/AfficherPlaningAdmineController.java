package Gestion_planing.Controllers;

import Gestion_planing.entities.planning;
import Gestion_planing.service.PlanningService;
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

public class AfficherPlaningAdmineController {

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
        @FXML
        private TableColumn<planning, String> idcourColumn;
        @FXML
        private TableColumn<planning, String> iduserColumn;



        private final ObservableList<planning> plannings = FXCollections.observableArrayList();
        private PlanningService planningService;

        public void initialize(URL location, ResourceBundle resources) {
            PlanningService planningService = new PlanningService();

            salleColumn.setCellValueFactory(new PropertyValueFactory<>("salle"));
            nbplacemaxColumn.setCellValueFactory(new PropertyValueFactory<>("nb_place_max"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
            idcourColumn.setCellValueFactory(new PropertyValueFactory<>("id_cour"));
            iduserColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));

            refreshTable();

            modifierColumn.setCellValueFactory(new PropertyValueFactory<>("modifierButton"));
            modifierColumn.setCellFactory(param -> new TableCell<>() {
                private final Button button = new Button("Modifier");

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.setOnAction(event -> {
                            planning p = getTableView().getItems().get(getIndex());
                            openUpdatePlaning(p);
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            });

            supprimerColumn.setCellValueFactory(new PropertyValueFactory<>("supprimerButton"));
            supprimerColumn.setCellFactory(param -> new TableCell<>() {
                private final Button button = new Button("Supprimer");

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.setOnAction(event -> {
                            planning plannings = getTableView().getItems().get(getIndex());
                            deletePlaning(plannings);
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }
            });
        }

        public void refreshTable() {
            plannings.clear();
            List<planning> planingList = planningService.readAll();
            plannings.addAll(planingList);
            planingTable.setItems(plannings);
        }

        @FXML
        public void ajouterplaning(ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlanifierUnCour.fxml"));
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

        private void openUpdatePlaning(planning p) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateUnPlanning.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);

                UpdateCourController controller = loader.getController();
                controller.setCurrentScene(scene);
                controller.initData(p.getId());

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void deletePlaning(planning p) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet abonnement ?");
            alert.setContentText("Cette action est irréversible.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                planningService.delete(p);
                refreshTable();
            }
        }
}



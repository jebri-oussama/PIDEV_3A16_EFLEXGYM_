package Gestion_planing.Controllers;

import Gestion_planing.entities.planning;
import Gestion_planing.service.PlanningService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherPlaningAdmineController  implements Initializable {

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
        iduserColumn.setCellValueFactory(cellData -> {
            planning planning = cellData.getValue();
            IntegerProperty property = new SimpleIntegerProperty();
            if (planning.getId_coach() != null) {
                property.set(planning.getId_coach().getId());
            }
            return property.asObject();
        });
        refreshTable();

        modifierColumn.setCellFactory(param -> new TableCell<planning, Void>() {
            private final Button button = new Button("Modifier");

            {
                button.setOnAction(event -> {
                    planning p = getTableView().getItems().get(getIndex());
                    openUpdatePlaning(p);
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

        supprimerColumn.setCellFactory(param -> new TableCell<planning, Void>() {
            private final Button button = new Button("Supprimer");

            {
                button.setOnAction(event -> {
                    planning p = getTableView().getItems().get(getIndex());
                    deletePlaning(p);
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

    public void refreshTable() {
        plannings.clear();
        List<planning> planningList = planningService.readAll();
        plannings.addAll(planningList);
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



            stage.showAndWait();
            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openUpdatePlaning(planning p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateUnPlanning.fxml"));
            Parent root = loader.load();
            UpdateUnPlaning controller = loader.getController();
            controller.initData(p.getId());
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
            refreshTable();

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

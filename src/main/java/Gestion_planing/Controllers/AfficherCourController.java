package Gestion_planing.Controllers;

import Gestion_planing.entities.TypeCours;
import Gestion_planing.entities.cours;
import Gestion_planing.service.CoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class AfficherCourController implements Initializable {

    @FXML
    private TableView<cours> courTable;
    @FXML
    private TableColumn<cours, Void> modifierColumn;
    @FXML
    private TableColumn<cours, Void> supprimerColumn;

    @FXML
    private TableColumn<cours, String> nomColumn;
    @FXML
    private TableColumn<cours, TypeCours> typeColumn;
    @FXML
    private TableColumn<cours, String> dureeColumn;

    private final ObservableList<cours> cour = FXCollections.observableArrayList();
    private CoursService courService;

    public void initialize(URL location, ResourceBundle resources) {
        // Assign the new instance of CoursService to courService
        courService = new CoursService();

        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        dureeColumn.setCellValueFactory(new PropertyValueFactory<>("duree"));

        refreshTable();

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
                        cours cour = getTableView().getItems().get(getIndex());
                        openUpdateCour(cour);
                    });
                    setGraphic(button);
                    setText(null);
                }
            }
        });

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
                        cours cour = getTableView().getItems().get(getIndex());
                        deleteCour(cour);
                    });
                    setGraphic(button);
                    setText(null);
                }
            }
        });
    }

    public void refreshTable() {
        cour.clear();
        List<cours> courList = courService.readAll();
        cour.addAll(courList);
        courTable.setItems(cour);
    }

    @FXML
    public void ajouterCour(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCour.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            AjouterCoursController controller = loader.getController();
            controller.setCurrentScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openUpdateCour(cours cour) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateCour.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            UpdateCourController controller = loader.getController();
            controller.setCurrentScene(scene);
            controller.initData(cour.getId());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteCour(cours cour) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet abonnement ?");
        alert.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            courService.delete(cour);
            refreshTable();
        }
    }
}

package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import gestion_suivi.entitis.Exercice;
import gestion_suivi.service.Exercice_Service;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class AfficherExerciceController {
    @FXML
    private Button addButton;
    @FXML
    private Button affecterButton;
    @FXML
    private TableView<Exercice> exerciceTableView;
    @FXML
    private TableColumn<Exercice, String> nomColumn;
    @FXML
    private TableColumn<Exercice, String> descriptionColumn;
    @FXML
    private TableColumn<Exercice, Integer> nbrSetColumn;
    @FXML
    private TableColumn<Exercice, String> GroupeColumn;
    @FXML
    private TableColumn<Exercice, Integer> nbrRepColumn;
    @FXML
    private TableColumn<Exercice, String> CategorieColumn;
    @FXML
    private TableColumn<Exercice, String> typeColumn;
    @FXML
    private TableColumn<Exercice, Void> actionsColumn;

    private Exercice_Service exerciceService;

    public AfficherExerciceController() {
        exerciceService = new Exercice_Service();
    }

    @FXML
    public void initialize() {
        // Populate the TableView
        populateTableView();

        // Set cell value factories for each column
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        nbrSetColumn.setCellValueFactory(new PropertyValueFactory<>("nbrDeSet"));
        GroupeColumn.setCellValueFactory(new PropertyValueFactory<>("groupeMusculaire"));
        nbrRepColumn.setCellValueFactory(new PropertyValueFactory<>("nbrDeRepetitions"));
        CategorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorieExercice"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeEquipement"));

        // Set custom cell factory for actions column
        actionsColumn.setCellFactory(createActionCellFactory());

        addButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AjouterExercice.fxml"));
                Scene scene = new Scene(root, 900.0, 600.0);
                Stage stage = (Stage) addButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        affecterButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AffecterExercice.fxml"));
                Scene scene = new Scene(root, 900.0, 600.0);
                Stage stage = (Stage) addButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Callback<TableColumn<Exercice, Void>, TableCell<Exercice, Void>> createActionCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Exercice, Void> call(TableColumn<Exercice, Void> param) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button("Delete");
                    private final Button editButton = new Button("Edit");

                    {
                        deleteButton.setOnAction(event -> {
                            Exercice exercice = getTableView().getItems().get(getIndex());
                            deleteExercice(exercice);
                        });

                        editButton.setOnAction(event -> {
                            Exercice exercice = getTableView().getItems().get(getIndex());
                            editExercice(exercice);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(deleteButton, editButton));
                        }
                    }
                };
            }
        };
    }

    private void deleteExercice(Exercice exercice) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Confirm Delete");
        confirmationAlert.setContentText("Are you sure you want to delete the suivi?");

        // Add OK and Cancel buttons to the confirmation dialog
        confirmationAlert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        // Show the confirmation dialog and wait for user input
        confirmationAlert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                // User clicked OK, proceed with deletion
                boolean isDeleted = exerciceService.delete(exercice);
                if (isDeleted) {
                    // Remove the deleted club from the table view
                    exerciceTableView.getItems().remove(exercice);
                    // Update the filtered data after deletion
                    // filteredData.getSource().remove(club);
                    showAlert("Success", "Suivi deleted successfully.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to delete suivi.", Alert.AlertType.ERROR);
                }
            }
        });
    }

    private void editExercice(Exercice exercice) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterExercice.fxml"));   Parent root = loader.load();
            AjouterExerciceController exerciceController = loader.getController();
            exerciceController.populateFieldsWithExercie(exercice.getId());
            Scene scene = new Scene(root, 1320.0, 660.0);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void populateTableView() {
        List<Exercice> exerciceList = exerciceService.readAll();
        exerciceTableView.getItems().addAll(exerciceList);
    }
}

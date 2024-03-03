package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import gestion_suivi.entitis.Exercice;
import gestion_suivi.service.Exercice_Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    @FXML
    private TextField searchField;

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

        addButton.setOnAction(event -> loadAjouterExerciceScene());
        affecterButton.setOnAction(event -> loadAffecterExerciceScene());
    }

    private void loadAjouterExerciceScene() {
        loadScene("/AjouterExercice.fxml");
    }

    private void loadAffecterExerciceScene() {
        loadScene("/AffecterExercice.fxml");
    }

    private void loadScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root, 900.0, 600.0);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        // Suppression de l'exercice
        boolean isDeleted = exerciceService.delete(exercice);
        if (isDeleted) {
            // Mettre à jour la table après la suppression
            exerciceTableView.getItems().remove(exercice);
            showAlert("Success", "Exercice supprimé avec succès.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Échec de la suppression de l'exercice.", Alert.AlertType.ERROR);
        }
    }

    private void editExercice(Exercice exercice) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterExercice.fxml"));
            Parent root = loader.load();
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

    @FXML
    private void search() {
        populate();
    }

    private void populate() {
        String searchTerm = searchField.getText().trim().toLowerCase();

        ObservableList<Exercice> filteredExercices = FXCollections.observableArrayList(
                exerciceService.readAll().stream()
                        .filter(exercice -> exercice.getNom().toLowerCase().contains(searchTerm))
                        .collect(Collectors.toList())
        );

        if (filteredExercices.isEmpty()) {
            showAlert("Aucun exercice trouvé", "Aucun exercice trouvé avec le nom spécifié.", Alert.AlertType.INFORMATION);
        } else {
            exerciceTableView.setItems(filteredExercices);
        }
    }
}

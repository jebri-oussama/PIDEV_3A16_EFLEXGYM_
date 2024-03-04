package gestion_suivi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import gestion_suivi.entitis.Suivi_Progre;
import gestion_suivi.service.Suivi_Progre_Service;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.List;

public class AfficherSuiviProgresController {
    @FXML
    private TableView<Suivi_Progre> suivitableView;
    @FXML
    private TableColumn<Suivi_Progre, String> nomColumn;
    @FXML
    private TableColumn<Suivi_Progre, String> prenomColumn;
    @FXML
    private TableColumn<Suivi_Progre, Integer> ageColumn;
    @FXML
    private TableColumn<Suivi_Progre, Double> tailleColumn;
    @FXML
    private TableColumn<Suivi_Progre, Double> poidsColumn;
    @FXML
    private TableColumn<Suivi_Progre, Double> tourDeTailleColumn;
    @FXML
    private TableColumn<Suivi_Progre, String> sexeColumn;
    @FXML
    private TableColumn<Suivi_Progre, Void> actionsColumn;

    private Suivi_Progre_Service suiviProgService;

    public AfficherSuiviProgresController() {
        suiviProgService = new Suivi_Progre_Service();
    }

    @FXML
    public void initialize() {

        // Charger les données dans le TableView
        populateTableView();

        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        tailleColumn.setCellValueFactory(new PropertyValueFactory<>("taille"));
        poidsColumn.setCellValueFactory(new PropertyValueFactory<>("poids"));
        tourDeTailleColumn.setCellValueFactory(new PropertyValueFactory<>("tour_de_taille"));
        sexeColumn.setCellValueFactory(new PropertyValueFactory<>("sexe"));

        actionsColumn.setCellFactory(createActionCellFactory());
        // Add selection listener to the table view
        /*suivitableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // A club is selected, show the "View stadiums" button
                viewStadiumsButton.setVisible(true);
            } else {
                // No club is selected, hide the "View stadiums" button
                viewStadiumsButton.setVisible(false);
            }
        });*/
        // Set action for the "View stadiums" button
       /* viewStadiumsButton.setOnAction(event -> {
            Club selectedClub = clubTableView.getSelectionModel().getSelectedItem();
            if (selectedClub != null) {
                openViewStadiums(selectedClub.getId());
            }
        });*/

    }

    private Callback<TableColumn<Suivi_Progre, Void>, TableCell<Suivi_Progre, Void>> createActionCellFactory() {
        return new Callback<TableColumn<Suivi_Progre, Void>, TableCell<Suivi_Progre, Void>>() {
            @Override
            public TableCell<Suivi_Progre, Void> call(TableColumn<Suivi_Progre, Void> param) {
                return new TableCell<>() {
                    //private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        /*editButton.setOnAction(event -> {
                            Suivi_Progre suivi = getTableView().getItems().get(getIndex());
                            //editSuivi(suivi);
                        });*/

                        deleteButton.setOnAction(event -> {
                           Suivi_Progre suivi  = getTableView().getItems().get(getIndex());
                            deleteSuivi(suivi);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            //setGraphic(new HBox(editButton, deleteButton));
                            setGraphic(new HBox(deleteButton));
                        }
                    }
                };
            }
        };
    }

    private void deleteSuivi(Suivi_Progre suivi) {
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
                boolean isDeleted = suiviProgService.delete(suivi);
                if (isDeleted) {
                    // Remove the deleted club from the table view
                    suivitableView.getItems().remove(suivi);
                    // Update the filtered data after deletion
                    // filteredData.getSource().remove(club);
                    showAlert("Success", "Suivi deleted successfully.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to delete suivi.", Alert.AlertType.ERROR);
                }
            }
        });
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void populateTableView() {
        List<Suivi_Progre> suiviProgData = suiviProgService.readAll();
        suivitableView.getItems().addAll(suiviProgData);
    }

    /*private void editClub(Suivi_Progre suivi) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewClub/NewClub.fxml"));
            Parent root = loader.load();
            Suivi_Progre_Service suiviController = loader.getController();
          //  suiviController.populateFieldsWithClubData(club);
            Scene scene = new Scene(root, 1180.0, 655.0);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}

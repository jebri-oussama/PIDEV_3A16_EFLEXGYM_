package gestion_evenement.Controllers;

import gestion_evenement.entities.Evenement;
import gestion_evenement.entities.EventBus;
import gestion_evenement.entities.Type;
import gestion_evenement.service.EvenementService;
import gestion_evenement.service.TypeService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.File;
import java.sql.Date;
import java.time.LocalDateTime;

import java.sql.Timestamp;
import java.util.List;
import javafx.scene.image.Image;

public class AjouterEvenementController {
    @FXML
    private Label lblPlace;


    public ImageView imageView;
    @FXML
    private ComboBox<Type> typeComboBox;
    @FXML
    private TextField txtevent_name;
    @FXML
    private DatePicker datePickerDebut;

    @FXML
    private DatePicker datePickerFin;
    @FXML
    private TextField txtduration;
    private String imagePath;
    @FXML
    private TextField txtplace;
    @FXML
    private void initialize() {
        populateTypeComboBox();
    }

    @FXML
    void addEvenement(ActionEvent event) {
     
        if (!validateInput()) {
            return;
        }

        Type selectedType = typeComboBox.getValue();
        String typeName = selectedType.getTypeName();

        TypeService typeService = new TypeService();
        Type type = typeService.getTypeByName(typeName);
        String event_name = txtevent_name.getText();

        Date date_debut = Date.valueOf(datePickerDebut.getValue());
        Date date_fin = Date.valueOf(datePickerFin.getValue());
        String duration = txtduration.getText();

        String imagePath = this.imagePath;
        String place = txtplace.getText();
        Evenement evenement = new Evenement(type,event_name, date_debut, date_fin,duration, imagePath,place);

        EvenementService evenementService = new EvenementService();
        evenementService.add(evenement);
        EventBus.getInstance().notifyTableRefreshed();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            imageView.setImage(image);
        }

    }

    private boolean validateInput() {
        if (typeComboBox.getValue() == null) {
            displayErrorMessage("Please select a type.");
            return false;
        }
        if (txtevent_name.getText() == null || txtevent_name.getText().isEmpty()) {
            displayErrorMessage("Please type a name for the event.");
            return false;
        }

        if (datePickerDebut.getValue() == null || datePickerFin.getValue() == null) {
            displayErrorMessage("Please select a date for Date de début and Date de fin.");
            return false;
        }

        Date date_debut = Date.valueOf(datePickerDebut.getValue());
        Date date_fin = Date.valueOf(datePickerFin.getValue());

        if (date_debut.before(new java.sql.Date(System.currentTimeMillis()))) {
            displayErrorMessage("Date de début should not be less than today's date.");
            return false;
        }

        if (date_fin.before(date_debut)) {
            displayErrorMessage("Date de fin should not be less than Date de début.");
            return false;
        }

        if (date_debut.equals(date_fin) && txtduration.getText().isEmpty()) {
            displayErrorMessage("If Date de début and Date de fin are equal, Durée should not be empty.");
            return false;
        }

        return true;
    }

    private boolean isValidDate(String value) {
        try {
            Date.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    private void displayErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void populateTypeComboBox() {
        TypeService typeService = new TypeService();
        List<Type> types = typeService.getAllTypes();
        typeComboBox.setItems(FXCollections.observableArrayList(types));
    }

}


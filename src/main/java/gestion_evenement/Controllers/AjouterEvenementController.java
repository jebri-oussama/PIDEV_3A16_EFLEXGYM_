package gestion_evenement.Controllers;

import gestion_evenement.entities.Evenement;
import gestion_evenement.entities.EventBus;
import gestion_evenement.entities.Type;
import gestion_evenement.service.EvenementService;
import gestion_evenement.service.TypeService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;

import java.sql.Timestamp;
import java.util.List;
import javafx.scene.image.Image;

public class AjouterEvenementController {

    public ImageView imageView;
    @FXML
    private ComboBox<Type> typeComboBox;


    @FXML
    private TextField txtdate_debut;

    @FXML
    private TextField txtdate_fin;
    private String imagePath;
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

        Timestamp date_debut = Timestamp.valueOf(txtdate_debut.getText());
        Timestamp date_fin = Timestamp.valueOf(txtdate_fin.getText());

        String imagePath = this.imagePath;
        Evenement evenement = new Evenement(type, date_debut, date_fin, imagePath);

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

        if (!isValidTimestamp(txtdate_debut.getText())) {
            displayErrorMessage("Invalid date format for Date de début. Use yyyy-MM-dd HH:mm:ss");
            return false;
        }

        if (!isValidTimestamp(txtdate_fin.getText())) {
            displayErrorMessage("Invalid date format for Date de fin. Use yyyy-MM-dd HH:mm:ss");
            return false;
        }


        Timestamp date_debut = Timestamp.valueOf(txtdate_debut.getText());
        Timestamp date_fin = Timestamp.valueOf(txtdate_fin.getText());

        if (date_debut.before(Timestamp.valueOf(LocalDateTime.now()))) {
            displayErrorMessage("Date de début should not be less than today's date.");
            return false;
        }


        if (date_fin.before(date_debut)) {
            displayErrorMessage("Date de fin should not be less than Date de début.");
            return false;
        }


        if (date_debut.equals(date_fin) ) {
            displayErrorMessage("If Date de début and Date de fin are equal, Durée should not be empty.");
            return false;
        }

        return true;
    }


    private boolean isValidTimestamp(String value) {
        try {
            Timestamp.valueOf(value);
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


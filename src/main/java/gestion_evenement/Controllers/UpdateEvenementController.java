package gestion_evenement.Controllers;

import gestion_evenement.entities.Evenement;
import gestion_evenement.entities.EventBus;
import gestion_evenement.entities.Type;
import gestion_evenement.service.EvenementService;
import gestion_evenement.service.TypeService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class UpdateEvenementController {

    @FXML
    private TextField txtid;

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

    @FXML
    private ImageView imageView;
    private String imagePath;
    @FXML
    private TextField txtplace;
    private EvenementService evenementService;

    public void initData(int eventId) {
        evenementService = new EvenementService();

        Evenement evenement = evenementService.readById(eventId);
        txtid.setText(String.valueOf(eventId));

        Type eventType = evenement.getType();
        if (eventType != null) {
            TypeService typeService = new TypeService();
            List<Type> types = typeService.readAll();
            typeComboBox.setItems(FXCollections.observableArrayList(types));

            Type selectedType = types.stream()
                    .filter(type -> type.getId() == eventType.getId())
                    .findFirst()
                    .orElse(null);

            typeComboBox.setValue(selectedType);
        }
        txtevent_name.setText(evenement.getEvent_name());
        datePickerDebut.setValue(evenement.getDate_debut().toLocalDate());
        datePickerFin.setValue(evenement.getDate_fin().toLocalDate());
        txtduration.setText((evenement.getDuration()));
        imagePath = evenement.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(imagePath);
            imageView.setImage(image);
        }
        txtplace.setText(evenement.getPlace());
    }




    @FXML
    void updateEvenement(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        int id = Integer.parseInt(txtid.getText());
        Type type = typeComboBox.getValue();
        String event_name = txtevent_name.getText();
        java.sql.Date date_debut = java.sql.Date.valueOf(datePickerDebut.getValue());
        java.sql.Date date_fin = java.sql.Date.valueOf(datePickerFin.getValue());
        String duration = txtduration.getText();
        String place = txtplace.getText();
        Evenement evenement = new Evenement(type,event_name, date_debut,date_fin, duration,imagePath,place);
        evenementService.update(id, evenement);
        EventBus.getInstance().notifyTableRefreshed();

        Stage stage = (Stage) txtid.getScene().getWindow();
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
        if (txtevent_name.getText() == null ){
            displayErrorMessage("Please type a name for the event.");
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
    private void displayErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean isValidDate(String value) {
        try {
            Date.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


}

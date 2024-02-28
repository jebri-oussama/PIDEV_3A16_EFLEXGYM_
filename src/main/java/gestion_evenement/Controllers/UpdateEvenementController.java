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
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.File;
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
    private TextField txtdate_debut;

    @FXML
    private TextField txtdate_fin;
    @FXML
    private ImageView imageView;
    private String imagePath;
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
        txtdate_debut.setText(evenement.getDate_debut().toString());
        txtdate_fin.setText(evenement.getDate_fin().toString());
        imagePath = evenement.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(imagePath);
            imageView.setImage(image);
        }
    }




    @FXML
    void updateEvenement(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        int id = Integer.parseInt(txtid.getText());
        Type type = typeComboBox.getValue();
        String event_name = txtevent_name.getText();
        Timestamp date_debut = Timestamp.valueOf(txtdate_debut.getText());
        Timestamp date_fin = Timestamp.valueOf(txtdate_fin.getText());

        Evenement evenement = new Evenement(type,event_name, date_debut, date_fin, imagePath);
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

        if (date_debut.equals(date_fin)) {
            if (date_fin.toLocalDateTime().toLocalTime().isBefore(date_debut.toLocalDateTime().toLocalTime())) {
                displayErrorMessage("If Date de début and Date de fin are equal, Time should not be empty.");
                return false;
            }
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
    private boolean isValidTimestamp(String value) {
        try {
            Timestamp.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


}

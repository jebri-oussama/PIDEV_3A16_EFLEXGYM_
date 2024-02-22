package gestion_evenement.Controllers;

import gestion_evenement.entities.Evenement;
import gestion_evenement.entities.EventBus;
import gestion_evenement.entities.Type;
import gestion_evenement.service.EvenementService;
import gestion_evenement.service.TypeService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class UpdateEvenementController {

    @FXML
    private TextField txtid;

    @FXML
    private ComboBox<Type> typeComboBox;

    @FXML
    private TextField txtdate_debut;

    @FXML
    private TextField txtdate_fin;

    @FXML
    private TextField txtduree;

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

            // Find the matching type in the comboBox
            Type selectedType = types.stream()
                    .filter(type -> type.getId() == eventType.getId())
                    .findFirst()
                    .orElse(null);

            typeComboBox.setValue(selectedType);
        }

        txtdate_debut.setText(evenement.getDate_debut().toString());
        txtdate_fin.setText(evenement.getDate_fin().toString());
        txtduree.setText(evenement.getDuree());
    }



    @FXML
    void updateEvenement(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        int id = Integer.parseInt(txtid.getText());
        Type type = typeComboBox.getValue();

        Timestamp date_debut = Timestamp.valueOf(txtdate_debut.getText());
        Timestamp date_fin = Timestamp.valueOf(txtdate_fin.getText());
        String duree = txtduree.getText();

        Evenement evenement = new Evenement(type, date_debut, date_fin, duree);
        evenementService.update(id, evenement);
        EventBus.getInstance().notifyTableRefreshed();

        // Close the current stage
        Stage stage = (Stage) txtid.getScene().getWindow();
        stage.close();
    }

    private boolean validateInput() {
        if (typeComboBox.getValue() == null) {
            // No type selected
            System.out.println("Please select a type.");
            return false;
        }

        if (!isValidTimestamp(txtdate_debut.getText())) {
            // Invalid date format for date_debut
            System.out.println("Invalid date format for Date de début. Use yyyy-MM-dd HH:mm:ss");
            return false;
        }

        if (!isValidTimestamp(txtdate_fin.getText())) {
            // Invalid date format for date_fin
            System.out.println("Invalid date format for Date de fin. Use yyyy-MM-dd HH:mm:ss");
            return false;
        }

        if (!isValidDuration(txtduree.getText())) {
            // Invalid duration format
            System.out.println("Invalid duration format. Please enter a value in HH:mm:ss format.");
            return false;
        }

        Timestamp date_debut = Timestamp.valueOf(txtdate_debut.getText());
        Timestamp date_fin = Timestamp.valueOf(txtdate_fin.getText());

        // Validate date_debut is not less than today's date
        if (date_debut.before(Timestamp.valueOf(LocalDateTime.now()))) {
            System.out.println("Date de début should not be less than today's date.");
            return false;
        }

        // Validate date_fin is not less than date_debut
        if (date_fin.before(date_debut)) {
            System.out.println("Date de fin should not be less than Date de début.");
            return false;
        }

        // If date_debut and date_fin are equal, duree should not be null
        if (date_debut.equals(date_fin) && txtduree.getText().equals("00:00:00") ) {
            System.out.println("If Date de début and Date de fin are equal, Durée should not be empty.");
            return false;
        }

        return true;
    }
    private boolean isValidDuration(String duration) {
        // Regex to match the HH:mm:ss format
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
        return duration.matches(regex);
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

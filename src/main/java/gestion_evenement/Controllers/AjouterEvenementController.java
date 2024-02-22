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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDateTime;

import java.sql.Timestamp;
import java.util.List;

public class AjouterEvenementController {

    @FXML
    private ComboBox<Type> typeComboBox;


    @FXML
    private TextField txtdate_debut;

    @FXML
    private TextField txtdate_fin;

    @FXML
    private void initialize() {
        populateTypeComboBox();
    }
    @FXML
    void addEvenement(ActionEvent event) {
        // Validate input
        if (!validateInput()) {
            return;
        }

        Type selectedType = typeComboBox.getValue();
        String typeName = selectedType.getTypeName();

        TypeService typeService = new TypeService();
        Type type = typeService.getTypeByName(typeName); // Implement getTypeByName method in TypeService

        Timestamp date_debut = Timestamp.valueOf(txtdate_debut.getText());
        Timestamp date_fin = Timestamp.valueOf(txtdate_fin.getText());


        Evenement evenement = new Evenement(type, date_debut, date_fin);

        EvenementService evenementService = new EvenementService();
        evenementService.add(evenement);
        EventBus.getInstance().notifyTableRefreshed();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private boolean validateInput() {
        if (typeComboBox.getValue() == null) {

            System.out.println("Please select a type.");
            return false;
        }

        if (!isValidTimestamp(txtdate_debut.getText())) {
            System.out.println("Invalid date format for Date de début. Use yyyy-MM-dd HH:mm:ss");
            return false;
        }

        if (!isValidTimestamp(txtdate_fin.getText())) {
            System.out.println("Invalid date format for Date de fin. Use yyyy-MM-dd HH:mm:ss");
            return false;
        }


        Timestamp date_debut = Timestamp.valueOf(txtdate_debut.getText());
        Timestamp date_fin = Timestamp.valueOf(txtdate_fin.getText());

        if (date_debut.before(Timestamp.valueOf(LocalDateTime.now()))) {
            System.out.println("Date de début should not be less than today's date.");
            return false;
        }


        if (date_fin.before(date_debut)) {
            System.out.println("Date de fin should not be less than Date de début.");
            return false;
        }


        if (date_debut.equals(date_fin) ) {
            System.out.println("If Date de début and Date de fin are equal, Durée should not be empty.");
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



    private void populateTypeComboBox() {
        TypeService typeService = new TypeService(); // Assuming you have a TypeService class
        List<Type> types = typeService.getAllTypes(); // Implement this method in your TypeService
        typeComboBox.setItems(FXCollections.observableArrayList(types));
    }

}


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
        TypeService typeService = new TypeService();
        List<Type> types = typeService.readAll();
        typeComboBox.setItems(FXCollections.observableArrayList(types));
        typeComboBox.setValue(evenement.getType());
        txtdate_debut.setText(evenement.getDate_debut().toString());
        txtdate_fin.setText(evenement.getDate_fin().toString());
        txtduree.setText(evenement.getDuree());
    }

    @FXML
    void updateEvenement(ActionEvent event) {
        int id = Integer.parseInt(txtid.getText());
        Type type = typeComboBox.getValue();

        // Check if a Type is selected
        if (type == null) {
            System.out.println("No type selected.");
            return;
        }

        // Check if the selected Type exists in the database
        TypeService typeService = new TypeService();
        Type existingType = typeService.readById(type.getId());
        if (existingType == null) {
            System.out.println("Selected type does not exist in the database.");
            return;
        }

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




}

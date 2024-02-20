package gestion_communaute.Controllers;

import gestion_communaute.entities.Evenement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import gestion_communaute.service.EvenementService;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;

public class UpdateEvenementController {

    @FXML
    private TextField txtid;

    @FXML
    private TextField txttype;

    @FXML
    private TextField txtdate_debut;

    @FXML
    private TextField txtdate_fin;

    @FXML
    private TextField txtduree;

    public void initData(int eventId) {
        EvenementService evenementService = new EvenementService();
        Evenement evenement = evenementService.readById(eventId);
            txtid.setText(String.valueOf(eventId));
        System.out.println("Received event id: " + txtid.getText());

        txttype.setText(evenement.getType());
            txtdate_debut.setText(evenement.getDate_debut().toString());
            txtdate_fin.setText(evenement.getDate_fin().toString());
            txtduree.setText(evenement.getDuree());

    }

    @FXML
    void updateEvenement(ActionEvent event) {
        int id = Integer.parseInt(txtid.getText());
        String type = txttype.getText();
        Timestamp date_debut = Timestamp.valueOf(txtdate_debut.getText());
        Timestamp date_fin = Timestamp.valueOf(txtdate_fin.getText());
        String duree = txtduree.getText();

        Evenement evenement = new Evenement(type, date_debut, date_fin, duree);
        EvenementService evenementService = new EvenementService();
        evenementService.update(id, evenement);

        // Close the current stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenement.fxml"));
            Parent root = loader.load();
            txttype.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }





}
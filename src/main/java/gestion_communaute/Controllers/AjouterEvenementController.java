package gestion_communaute.Controllers;

import gestion_communaute.entities.Evenement;
import gestion_communaute.service.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Timestamp;

public class AjouterEvenementController {

    @FXML
    private TextField txttype;

    @FXML
    private TextField txtdate_debut;

    @FXML
    private TextField txtdate_fin;

    @FXML
    private TextField txtduree;

    @FXML
    void addEvenement(ActionEvent event) {
        String type = txttype.getText();
        Timestamp date_debut = Timestamp.valueOf(txtdate_debut.getText());
        Timestamp date_fin = Timestamp.valueOf(txtdate_fin.getText());
        String duree = txtduree.getText();

        Evenement evenement = new Evenement( type, date_debut, date_fin, duree);
        EvenementService evenementService = new EvenementService();
        evenementService.add(evenement);

      /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenement.fxml"));
        try {
            Parent root = loader.load();
            AfficherEvenementController ac = loader.getController();
            ac.setRtype(type);
            ac.setRdate_debut(date_debut.toString());
            ac.setRdate_fin(date_fin.toString());
            ac.setRduree(duree);
            ac.setRlist(evenementService.readAll().toString());
            txtnom.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/
    }
}

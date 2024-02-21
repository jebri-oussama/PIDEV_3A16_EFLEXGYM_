package gestion_evenement.Controllers;

import gestion_evenement.entities.Evenement;
import gestion_evenement.entities.EventBus;
import gestion_evenement.entities.Type;
import gestion_evenement.service.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Timestamp;

public class AjouterEvenementController {

    @FXML
    private ComboBox<Type> typeComboBox;


    @FXML
    private TextField txtdate_debut;

    @FXML
    private TextField txtdate_fin;

    @FXML
    private TextField txtduree;

    @FXML
    void addEvenement(ActionEvent event) {
        Type type = typeComboBox.getValue();
        Timestamp date_debut = Timestamp.valueOf(txtdate_debut.getText());
        Timestamp date_fin = Timestamp.valueOf(txtdate_fin.getText());
        String duree = txtduree.getText();

        Evenement evenement = new Evenement(type, date_debut, date_fin, duree);

        EvenementService evenementService = new EvenementService();
        evenementService.add(evenement);
        EventBus.getInstance().notifyTableRefreshed();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }



   /* public void redirectToAfficherEvenement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherEvenement.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}


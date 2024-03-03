package GestionFinance.controller;

import GestionFinance.entites.Abonnement;
import GestionFinance.service.AbonnementService;
import gestion_user.entities.UserSession;
import gestion_user.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;


public class ConsulterAbonnementController {

    @FXML
    private Label typeLabel;

    @FXML
    private Label prixLabel;

    @FXML
    private Label dateDebutLabel;

    @FXML
    private Label dateFinLabel;
    private AbonnementService abonnementService;

    @FXML
    private Label etatLabel;

    private int adherentId; // Adherent ID of the logged-in user

    public void setAdherentId(int adherentId) {
        this.adherentId = adherentId;
    }

    public void initialize() {
        abonnementService = new AbonnementService();
        Abonnement abonnement = abonnementService.readAbonnementForLoggedInUser();

        if (abonnement != null) {
            typeLabel.setText("Type : " + abonnement.getType());
            prixLabel.setText("Prix : " + abonnement.getPrix());
            dateDebutLabel.setText("Date de début : " + abonnement.getDate_debut().format(DateTimeFormatter.ISO_DATE));
            dateFinLabel.setText("Date de fin : " + abonnement.getDate_fin().format(DateTimeFormatter.ISO_DATE));
            etatLabel.setText("État : " + abonnement.getEtat());
        }
    }
}

package GestionFinance.controller;

import GestionFinance.entites.Abonnement;
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

        @FXML
        private Label etatLabel;

        private Abonnement abonnement; // L'abonnement de l'adhérent connecté

        public void initialize() {
            // Afficher les détails de l'abonnement dans l'interface utilisateur
            if (abonnement != null) {
                typeLabel.setText("Type : " + abonnement.getType());
                prixLabel.setText("Prix : " + abonnement.getPrix());
                dateDebutLabel.setText("Date de début : " + abonnement.getDate_debut().format(DateTimeFormatter.ISO_DATE));
                dateFinLabel.setText("Date de fin : " + abonnement.getDate_fin().format(DateTimeFormatter.ISO_DATE));
                etatLabel.setText("État : " + abonnement.getEtat());
            }
        }

        public void setAbonnement(Abonnement abonnement) {
            this.abonnement = abonnement;
            initialize(); // Mettre à jour l'interface utilisateur avec les nouveaux détails d'abonnement
        }
}
package Gestion_planing.Controllers;

import GestionFinance.entites.Abonnement;
import GestionFinance.service.AbonnementService;
import Gestion_planing.entities.planning;
import Gestion_planing.service.PlanningService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SuprimerPlaningController {
        private final PlanningService planningService = new PlanningService();
        private planning p;

        public void setPlaning(planning p) {
            this.p = p;
        }

        @FXML
        private void initialize() {

        }

        @FXML
        private void deletePlaning() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet abonnement ?");
            alert.setContentText("Cette action est irréversible.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                planningService.delete(p);
            }
        }
    }



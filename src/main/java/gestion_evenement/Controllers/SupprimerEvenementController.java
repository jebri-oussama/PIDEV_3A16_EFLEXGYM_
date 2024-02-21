package gestion_evenement.Controllers;

import gestion_evenement.service.EvenementService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SupprimerEvenementController {

    private EvenementService evenementService;

    public SupprimerEvenementController() {
        evenementService = new EvenementService();
    }

    public void supprimerEvenement(int id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this event?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                evenementService.delete(id);
            }
        });
    }
}

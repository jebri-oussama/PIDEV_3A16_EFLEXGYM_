package Controllers;

import service.produitService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DeleteProduitController {

    private produitService produitService;

    public DeleteProduitController() {
        produitService = new produitService();
    }

    public void supprimerproduit(int id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this event?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                produitService.delete(id);
            }
        });
    }
}
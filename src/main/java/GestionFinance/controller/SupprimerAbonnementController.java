package GestionFinance.controller;

import GestionFinance.entites.Abonnement;
import GestionFinance.service.AbonnementService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SupprimerAbonnementController {
    private final AbonnementService abonnementService = new AbonnementService();
    private Abonnement abonnement;

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    @FXML
    private void initialize() {
        // Vous pouvez éventuellement ajouter des éléments d'interface utilisateur ici
    }

    @FXML
    private void deleteAbonnement() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet abonnement ?");
        alert.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            abonnementService.delete(abonnement);
            // Vous pouvez ajouter des actions supplémentaires après la suppression ici
        }
    }
}

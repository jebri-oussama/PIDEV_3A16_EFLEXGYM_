package Gestion_planing.Controllers;
import Gestion_planing.entities.cours;
import Gestion_planing.service.CoursService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SuprimerCourController {
        private final CoursService coursService = new CoursService();
        private cours cour;

        public void setCour(cours cour) {
            this.cour = cour;
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
                coursService.delete(cour);
                // Vous pouvez ajouter des actions supplémentaires après la suppression ici
            }
        }
    }
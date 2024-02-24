package GestionFinance.controller;

import GestionFinance.entites.Abonnement;
import GestionFinance.entites.BilanFinancier;
import GestionFinance.entites.Etat;
import GestionFinance.entites.Type;
import GestionFinance.service.AbonnementService;
import GestionFinance.service.BilanFinancierService;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.time.LocalDate;

public class UpdateAbonnementController {
    private final AbonnementService abonnementService = new AbonnementService();
    private final UserService userService = new UserService();
    private final BilanFinancierService bilanFinancierService = new BilanFinancierService();

    @FXML
    private DatePicker dateDebutId;
    private Scene currentScene;

    @FXML
    private DatePicker dateFinId;

    @FXML
    private TextField etatId;

    @FXML
    private TextField prixId;

    @FXML
    private ComboBox<Integer> adherentId;

    @FXML
    private ComboBox<Integer> bilanFinancierId;

    @FXML
    private TextField typeId;

    private int abonnementId; // Variable pour stocker l'ID de l'abonnement

    public void initData(int abonnementId) {
        this.abonnementId = abonnementId; // Stocker l'ID de l'abonnement
        Abonnement abonnement = abonnementService.readById(abonnementId);
        typeId.setText(abonnement.getType().toString());
        prixId.setText(String.valueOf(abonnement.getPrix()));
        dateDebutId.setValue(abonnement.getDate_debut());
        dateFinId.setValue(abonnement.getDate_fin());
        etatId.setText(abonnement.getEtat().toString());
        adherentId.setValue(abonnement.getId_adherent().getId());
        bilanFinancierId.setValue(abonnement.getId_bilan_financier().getId());
    }

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }

    @FXML
    void update(ActionEvent event) {
        // Récupération des valeurs des champs et des sélecteurs
        String typeString = typeId.getText(); // Utiliser getText() pour les champs de texte
        Type type = Type.valueOf(typeString);
        double prix = Double.parseDouble(prixId.getText());
        LocalDate dateDebut = dateDebutId.getValue();
        LocalDate dateFin = dateFinId.getValue();
        String etatString = etatId.getText(); // Utiliser getText() pour les champs de texte
        Etat etat = Etat.valueOf(etatString);
        int adherentIdSelected = adherentId.getValue();
        int bilanFinancierIdSelected = bilanFinancierId.getValue();

        // Lecture des entités Adherent et BilanFinancier à partir de leur ID
        User user = userService.readById(adherentIdSelected);
        BilanFinancier bilanFinancier = bilanFinancierService.readById(bilanFinancierIdSelected);

        // Création de l'objet Abonnement avec les valeurs récupérées
        Abonnement abonnement = new Abonnement(abonnementId, type, prix, dateDebut, dateFin, etat, user, bilanFinancier);

        // Appel de la méthode update de AbonnementService pour mettre à jour l'abonnement dans la base de données
        abonnementService.update(abonnement);

        // Nettoyage des champs
        clearFields();

        // Fermeture de la fenêtre
        typeId.getScene().getWindow().hide();

        // Redirection vers l'interface Afficher Abonnements
        redirectToAfficherAbonnements();
    }

    private void redirectToAfficherAbonnements() {
        Stage stage = (Stage) currentScene.getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            AfficherAbonnementsController controller = loader.getController();
            controller.refreshTable(); // Actualiser la table des abonnements
            stage.show(); // Assurez-vous d'afficher la nouvelle scène après la mise à jour
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        typeId.clear();
        prixId.clear();
        dateDebutId.getEditor().clear();
        dateFinId.getEditor().clear();
        etatId.clear();
        adherentId.getSelectionModel().clearSelection();
        bilanFinancierId.getSelectionModel().clearSelection();
    }
}

package GestionFinance.controller;

import GestionFinance.entites.Abonnement;
import GestionFinance.entites.BilanFinancier;
import GestionFinance.entites.Etat;
import GestionFinance.entites.Type;
import GestionFinance.service.AbonnementService;
import gestion_user.entities.User;
import gestion_user.entities.Adherent;
import gestion_user.service.AdherentService;
import GestionFinance.service.BilanFinancierService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AjouterAbonnementController {
    private final AbonnementService abonnementService = new AbonnementService();
    private final AdherentService adherentService = new AdherentService();
    private final BilanFinancierService bilanFinancierService = new BilanFinancierService();

    private Scene currentScene;

    @FXML
    private DatePicker dateDebutId;

    @FXML
    private DatePicker dateFinId;

    @FXML
    private TextField etatId;

    @FXML
    private TextField prixId;

    @FXML
    private ComboBox<String> adherentId;

    @FXML
    private ComboBox<String> bilanFinancierId;

    @FXML
    private TextField typeId;

    @FXML
    private Button ajouterId;

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }

    @FXML
    void initialize() {
        try {
            // Récupérez la liste des adhérents et extrayez les identifiants
            List<Adherent> adherents = adherentService.readAll();
            List<String> adherentIds = adherents.stream()
                    .map(Adherent::getId)
                    .map(String::valueOf) // Convertir les entiers en chaînes
                    .collect(Collectors.toList());
            adherentId.setItems(FXCollections.observableArrayList(adherentIds));

            // Récupérez la liste des bilans financiers et extrayez les ID
            List<BilanFinancier> bilansFinanciers = bilanFinancierService.readAll();
            List<String> bilanFinancierIds = bilansFinanciers.stream()
                    .map(BilanFinancier::getId)
                    .map(String::valueOf) // Convertir les entiers en chaînes
                    .collect(Collectors.toList());
            bilanFinancierId.setItems(FXCollections.observableArrayList(bilanFinancierIds));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouter() {
        String typeString = typeId.getText();
        Type type = Type.valueOf(typeString);
        double prix = Double.parseDouble(prixId.getText());
        LocalDate dateDebut = dateDebutId.getValue();
        LocalDate dateFin = dateFinId.getValue();
        String etatString = etatId.getText();
        Etat etat = Etat.valueOf(etatString);
        int adherentIdSelected = Integer.parseInt(adherentId.getValue()); // Convertir la chaîne en entier
        int bilanFinancierIdSelected = Integer.parseInt(bilanFinancierId.getValue()); // Convertir la chaîne en entier

        // Récupérer les objets User et BilanFinancier correspondants aux ID sélectionnés
        User adherent = adherentService.readById(adherentIdSelected);
        BilanFinancier bilanFinancier = bilanFinancierService.readById(bilanFinancierIdSelected);

        // Création de l'abonnement avec les données récupérées
        Abonnement abonnement = new Abonnement(type, prix, dateDebut, dateFin, etat, adherent, bilanFinancier);

        // Ajout de l'abonnement à la base de données
        abonnementService.add(abonnement);

        // Effacer les champs après l'ajout
        clearFields();

        // Rediriger vers l'interface Afficher Abonnements
        redirectToAfficherAbonnements();
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

    private void redirectToAfficherAbonnements() {
        Stage stage = (Stage) currentScene.getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            AfficherAbonnementsController controller = loader.getController();
            controller.refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

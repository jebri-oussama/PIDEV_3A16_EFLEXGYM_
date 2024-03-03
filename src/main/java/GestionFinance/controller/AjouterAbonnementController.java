package GestionFinance.controller;

import GestionFinance.entites.Abonnement;
import GestionFinance.entites.BilanFinancier;
import GestionFinance.entites.Etat;
import GestionFinance.entites.Type;
import GestionFinance.service.AbonnementService;
import gestion_user.entities.User;
import GestionFinance.service.BilanFinancierService;
import gestion_user.service.UserService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AjouterAbonnementController {
    private final AbonnementService abonnementService = new AbonnementService();
    private final UserService userService = new UserService();
    private final BilanFinancierService bilanFinancierService = new BilanFinancierService();

    private Scene currentScene;

    @FXML
    private DatePicker dateDebutId;

    @FXML
    private DatePicker dateFinId;

    @FXML
    private TextField etatId;

    @FXML
    private Button dashboardId;

    @FXML
    private Button bilanFinancierId1;

    @FXML
    private Button abonnementsId;

    @FXML
    private TextField prixId;

    @FXML
    private ComboBox<User> adherentId;

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
            List<User> users = userService.readAll();
            adherentId.setItems(FXCollections.observableArrayList(users));
            adherentId.setConverter(new StringConverter<User>() {
                @Override
                public String toString(User user) {
                    return user.getNom();
                }

                @Override
                public User fromString(String string) {
                    return null;
                }
            });

            List<String> mois = Arrays.asList("Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre");
            bilanFinancierId.setItems(FXCollections.observableArrayList(mois));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouter() {
        if(typeId.getText().isEmpty() || prixId.getText().isEmpty() || dateDebutId.getValue() == null ||
                dateFinId.getValue() == null || etatId.getText().isEmpty() || adherentId.getValue() == null ||
                bilanFinancierId.getValue() == null){
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        try{
            double prix = Double.parseDouble(prixId.getText());
            if (prix <= 0){
                showAlert("Le prix doit être supérieur à zéro");
                return;
            }
        } catch(NumberFormatException e){
            showAlert("Le prix doit être un nombre valide");
            return;
        }

        Type type = Type.valueOf(typeId.getText());
        double prix = Double.parseDouble(prixId.getText());
        LocalDate dateDebut = dateDebutId.getValue();
        LocalDate dateFin = dateFinId.getValue();
        Etat etat = Etat.valueOf(etatId.getText());
        User adherent = adherentId.getValue();
        BilanFinancier bilanFinancier = bilanFinancierService.readById(Integer.parseInt(bilanFinancierId.getValue()));
        Abonnement abonnement = new Abonnement(type, prix, dateDebut, dateFin, etat, adherent, bilanFinancier);

        abonnementService.add(abonnement);

        clearFields();

        redirectToAfficherAbonnements();
    }

    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    @FXML
    void afficherBilanFinancier() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherBilanFinancier.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void afficherAbonnements() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void afficherDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

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

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
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
            List<User> users = userService.readAll();
            List<String> adherentIds = users.stream()
                    .map(User::getId)
                    .map(String::valueOf)
                    .collect(Collectors.toList());
            adherentId.setItems(FXCollections.observableArrayList(adherentIds));


            List<BilanFinancier> bilansFinanciers = bilanFinancierService.readAll();
            List<String> bilanFinancierIds = bilansFinanciers.stream()
                    .map(BilanFinancier::getId)
                    .map(String::valueOf)
                    .collect(Collectors.toList());
            bilanFinancierId.setItems(FXCollections.observableArrayList(bilanFinancierIds));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouter() {
        if(typeId.getText().isEmpty() || prixId.getText().isEmpty() ||dateDebutId.getValue()== null ||
           dateFinId.getValue()== null || etatId.getText().isEmpty()|| adherentId.getValue() == null ||
           bilanFinancierId.getValue()==null){
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        try{
            double prix = Double.parseDouble(prixId.getText());
            if (prix <= 0){
                showAlert("Le prix doit etre supérieur à zéro");
                return;
            }
        }catch(NumberFormatException e){
            showAlert("Le prix doit etre un nombre valide");
            return;
        }






        String typeString = typeId.getText();
        Type type = Type.valueOf(typeString);
        double prix = Double.parseDouble(prixId.getText());
        LocalDate dateDebut = dateDebutId.getValue();
        LocalDate dateFin = dateFinId.getValue();
        String etatString = etatId.getText();
        Etat etat = Etat.valueOf(etatString);
        int adherentIdSelected = Integer.parseInt(adherentId.getValue());
        int bilanFinancierIdSelected = Integer.parseInt(bilanFinancierId.getValue());
        User user = userService.readById(adherentIdSelected);
        BilanFinancier bilanFinancier = bilanFinancierService.readById(bilanFinancierIdSelected);
        Abonnement abonnement = new Abonnement(type, prix, dateDebut, dateFin, etat, user, bilanFinancier);


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
}

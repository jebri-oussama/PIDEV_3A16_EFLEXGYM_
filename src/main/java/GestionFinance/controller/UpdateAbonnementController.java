package GestionFinance.controller;

import GestionFinance.entites.Abonnement;
import GestionFinance.entites.BilanFinancier;
import GestionFinance.entites.Etat;
import GestionFinance.entites.Type;
import GestionFinance.service.AbonnementService;
import GestionFinance.service.BilanFinancierService;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateAbonnementController implements Initializable {
    private final AbonnementService abonnementService = new AbonnementService();
    private final UserService userService = new UserService();
    private final BilanFinancierService bilanFinancierService = new BilanFinancierService();

    @FXML
    private DatePicker dateDebutId;
    private Scene currentScene;

    @FXML
    private DatePicker dateFinId;
    @FXML
    private ComboBox<Type> typeId;

    @FXML
    private ComboBox<Etat> etatId;

    @FXML
    private TextField prixId;

    @FXML
    private ComboBox<Integer> adherentId;

    @FXML
    private ComboBox<Integer> bilanFinancierId;


    private int abonnementId;




    public void initData(int abonnementId) {
        this.abonnementId = abonnementId;
        Abonnement abonnement = abonnementService.readById(abonnementId);

        prixId.setText(String.valueOf(abonnement.getPrix()));
        dateDebutId.setValue(abonnement.getDate_debut());
        dateFinId.setValue(abonnement.getDate_fin());

        adherentId.setValue(abonnement.getId_adherent().getId());
        bilanFinancierId.setValue(abonnement.getId_bilan_financier().getId());
    }

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }

    @FXML
    void update(ActionEvent event) {

        if (typeId.getValue()==null || prixId.getText().isEmpty() || dateDebutId.getValue() == null ||
                dateFinId.getValue() == null || etatId.getValue()==null || adherentId.getValue() == null ||
                bilanFinancierId.getValue() == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }
        try {
            double prix = Double.parseDouble(prixId.getText());
            if (prix <= 0) {
                showAlert("Le prix doit être supérieur à zéro.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Le prix doit être un nombre valide.");
            return;
        }


        String typeString = String.valueOf(typeId.getValue());
        Type type = Type.valueOf(typeString);
        double prix = Double.parseDouble(prixId.getText());
        LocalDate dateDebut = dateDebutId.getValue();
        LocalDate dateFin = dateFinId.getValue();
        String etatString = String.valueOf(etatId.getValue());
        Etat etat = Etat.valueOf(etatString);
        int adherentIdSelected = adherentId.getValue();
        int bilanFinancierIdSelected = bilanFinancierId.getValue();


        User user = userService.readById(adherentIdSelected);
        BilanFinancier bilanFinancier = bilanFinancierService.readById(bilanFinancierIdSelected);


        Abonnement abonnement = new Abonnement(abonnementId, type, prix, dateDebut, dateFin, etat, user, bilanFinancier);

         abonnementService.update(abonnement);


        clearFields();


        typeId.getScene().getWindow().hide();


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
            controller.refreshTable();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        typeId.getSelectionModel().clearSelection();
        prixId.clear();
        dateDebutId.getEditor().clear();
        dateFinId.getEditor().clear();
        etatId.getSelectionModel().clearSelection();
        adherentId.getSelectionModel().clearSelection();
        bilanFinancierId.getSelectionModel().clearSelection();
    }

    public void afficherAbonnements(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afficherDashboard(ActionEvent actionEvent) { try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public void afficherBilanFinancier(ActionEvent actionEvent) { try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherBilanFinancier.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeId.setItems(FXCollections.observableArrayList(Type.values()));

        // Remplir la ComboBox etatId avec les valeurs de l'énumération Etat
        etatId.setItems(FXCollections.observableArrayList(Etat.values()));
    }
}

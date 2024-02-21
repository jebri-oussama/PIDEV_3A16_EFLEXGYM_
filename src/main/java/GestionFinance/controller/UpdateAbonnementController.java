package GestionFinance.controller;

import GestionFinance.entites.Abonnement;
import GestionFinance.entites.BilanFinancier;
import GestionFinance.entites.Etat;
import GestionFinance.entites.Type;
import GestionFinance.service.AbonnementService;
import gestion_user.entities.Adherent;
import gestion_user.service.AdherentService;
import GestionFinance.service.BilanFinancierService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class UpdateAbonnementController {
    private final AbonnementService abonnementService = new AbonnementService();
    private final AdherentService adherentService = new AdherentService();
    private final BilanFinancierService bilanFinancierService = new BilanFinancierService();

    @FXML
    private DatePicker dateDebutId;

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

    public void initData(int abonnementId) {
        Abonnement abonnement = abonnementService.readById(abonnementId);
        typeId.setText(abonnement.getType().toString());
        prixId.setText(String.valueOf(abonnement.getPrix()));
        dateDebutId.setValue(abonnement.getDate_debut());
        dateFinId.setValue(abonnement.getDate_fin());
        etatId.setText(abonnement.getEtat().toString());
        adherentId.setValue(abonnement.getId_adherent().getId());
        bilanFinancierId.setValue(abonnement.getId_bilan_financier().getId());
    }

    @FXML
    void update(ActionEvent event) {
        String typeString = typeId.getText();
        Type type = Type.valueOf(typeString);
        double prix = Double.parseDouble(prixId.getText());
        LocalDate dateDebut = dateDebutId.getValue();
        LocalDate dateFin = dateFinId.getValue();
        String etatString = etatId.getText();
        Etat etat = Etat.valueOf(etatString);
        int adherentIdSelected = adherentId.getValue();
        int bilanFinancierIdSelected = bilanFinancierId.getValue();

        Adherent adherent = adherentService.readById(adherentIdSelected);
        BilanFinancier bilanFinancier = bilanFinancierService.readById(bilanFinancierIdSelected);

        Abonnement abonnement = new Abonnement(type, prix, dateDebut, dateFin, etat, adherent, bilanFinancier);

        abonnementService.update(abonnement);

        clearFields();
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

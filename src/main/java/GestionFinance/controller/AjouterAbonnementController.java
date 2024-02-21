package GestionFinance.controller;

import GestionFinance.entites.Abonnement;
import GestionFinance.entites.BilanFinancier;
import GestionFinance.entites.Etat;
import GestionFinance.entites.Type;
import GestionFinance.service.AbonnementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterAbonnementController {
    private final AbonnementService as = new AbonnementService();


    @FXML
    private DatePicker dateDebutDP;

    @FXML
    private DatePicker dateFinDP;

    @FXML
    private TextField etatTF;

    @FXML
    private TextField idadherentTF;

    @FXML
    private TextField idbilanTF;

    @FXML
    private TextField prixTF;

    @FXML
    private TextField typeTF;


    @FXML
    void ajouter(ActionEvent event) {

        String typeString = typeTF.getText();
        Type type = Type.valueOf(typeString);
        double prix = Double.parseDouble(prixTF.getText());
        LocalDate dateDebut = dateDebutDP.getValue();
        LocalDate dateFin = dateFinDP.getValue();
        String etatString = etatTF.getText();
        Etat etat = Etat.valueOf(etatString);
        int idAdherent = Integer.parseInt(idadherentTF.getText());
        int idBilan = Integer.parseInt(idbilanTF.getText());
        // Adherent adherent = new Adherent(idAdherent);
        BilanFinancier bilanFinancier = new BilanFinancier(idBilan);

        // Abonnement abonnement = new Abonnement(type, prix, dateDebut, dateFin, etat, /*adherent,*/ bilanFinancier);
        // as.add(abonnement);

        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/AfficherPersonne.fxml"));
       /* try {
            Parent root = loader.load();
            AfficherAbonnementsController ac = loader.getController();
            ac.setrAdherent(String.valueOf(idAdherent));
            ac.setrBilan(String.valueOf(idBilan));
            ac.setrDateDebut(dateDebut.toString());
            ac.setrDateFin(dateFin.toString());
            ac.setrEtat(etatString);
            ac.setrPrix(String.valueOf(prix));
            ac.setrType(typeString);
            ac.setrList(as.readAll().toString());


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }*/
    }



}

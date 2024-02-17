package GestionFinance.controller;

import GestionFinance.service.AbonnementService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AfficherAbonnementsController {

    private final AbonnementService as = new AbonnementService();

    @FXML
    private TextField rAdherent;

    @FXML
    private TextField rBilan;

    @FXML
    private TextField rDateDebut;

    @FXML
    private TextField rDateFin;

    @FXML
    private TextField rEtat;

    @FXML
    private TextField rList;

    @FXML
    private TextField rPrix;

    @FXML
    private TextField rType;

    public void setrAdherent(String rAdherentText) {
        this.rAdherent.setText(rAdherentText);
    }

    public void setrBilan(String rBilanText) {
        this.rBilan.setText(rBilanText);
    }

    public void setrDateDebut(String rDateDebutText) {
        this.rDateDebut.setText(rDateDebutText);
    }

    public void setrDateFin(String rDateFinText) {
        this.rDateFin.setText(rDateFinText);
    }

    public void setrEtat(String rEtatText) {
        this.rEtat.setText(rEtatText);
    }

    public void setrList(String rListText) {
        this.rList.setText(rListText);
    }

    public void setrPrix(String rPrixText) {
        this.rPrix.setText(rPrixText);
    }

    public void setrType(String rTypeText) {
        this.rType.setText(rTypeText);
    }
}

package GestionFinance.controller;

import GestionFinance.entites.Abonnement;
import GestionFinance.service.AbonnementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AjouterAbonnementController {
    private final AbonnementService as = new AbonnementService();


    @FXML
    private TextField datedebutTF;

    @FXML
    private TextField datefinTF;

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
        as.add(new Abonnement(typeTF.getText(),prixTF.getText(),));

    }

}

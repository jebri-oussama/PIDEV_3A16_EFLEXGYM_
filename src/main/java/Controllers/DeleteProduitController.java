package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import service.produitService;

import java.io.IOException;

public class DeleteProduitController {

    @FXML
    private TextField txtid;
    @FXML
    private TextField rnom;

    @FXML
    private TextField rimage;

    @FXML
    private TextField rprix;

    @FXML
    private TextField rquantite;

    @FXML
    private TextField rdescription;

    @FXML
    private TextField rcategorie;

    @FXML
    private TextField rid_bilan_financier;

    @FXML
    private TextField rid_admin;
    @FXML
    private TextField rlist;
    @FXML
    void deleteProduit(ActionEvent event) {

        int id = Integer.parseInt(txtid.getText());
        produitService ps = new produitService();
        ps.delete(id);
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/AfficherProduit.fxml"));
        try {
            Parent root = loader.load();
            AfficherProduitController ac = loader.getController();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    }


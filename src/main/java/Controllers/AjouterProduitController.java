package Controllers;

import entities.categorie;
import entities.produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.produitService;

public class AjouterProduitController {

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtimage;

    @FXML
    private TextField txtprix;

    @FXML
    private TextField txtquantite;

    @FXML
    private TextField txtdescription;

    @FXML
    private TextField txtcategorie; // Change this to TextField

    @FXML
    private TextField txtid_bilan_financier;

    @FXML
    private TextField txtid_admin;

    @FXML
    void addProduit(ActionEvent event) {
        String nom = txtnom.getText();
        String image = txtimage.getText();
        float prix = Float.parseFloat(txtprix.getText());
        int quantite = Integer.parseInt(txtquantite.getText());
        String description = txtdescription.getText();
        int categoryId = Integer.parseInt(txtcategorie.getText());
        categorie category = new categorie(categoryId,null,description);
        int id_bilan_financier = Integer.parseInt(txtid_bilan_financier.getText());
        int id_admin = Integer.parseInt(txtid_admin.getText());
        produit p = new produit(nom, image, prix, quantite, description, category, id_bilan_financier, id_admin);
        produitService ps = new produitService();
        ps.add(p);
    }
}

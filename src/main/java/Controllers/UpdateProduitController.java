package Controllers;

import GestionFinance.entites.BilanFinancier;
import gestion_produit.entities.categorie;
import gestion_produit.entities.produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import gestion_produit.service.produitService;

public class UpdateProduitController {
    @FXML
    private TextField txtid;
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
    private TextField txtcategorie;

    @FXML
    private TextField txtid_bilan_financier;

    @FXML
    private TextField txtid_admin;

    @FXML
    void updateProduit(ActionEvent event) {
        int id = Integer.parseInt(txtid.getText());
        String nom = txtnom.getText();
        String image = txtimage.getText();
        float prix = Float.parseFloat(txtprix.getText());
        int quantite = Integer.parseInt(txtquantite.getText());
        String description = txtdescription.getText();
        int categoryId = Integer.parseInt(txtcategorie.getText());
        categorie category = new categorie(categoryId,null,null);
        int bilanid = Integer.parseInt(txtid_bilan_financier.getText());
        BilanFinancier bilan = new BilanFinancier(bilanid,null,null,0,0,0,0,0,0);
        int id_admin = Integer.parseInt(txtid_admin.getText());
        produit p = new produit(nom, image, prix, quantite, description, category, bilan, id_admin);
        produitService ps = new produitService();
        ps.update(id,p);
      /*  FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/AfficherProduit.fxml"));
       /* try {
            Parent root = loader.load();
            AfficherProduitController ac = loader.getController();
            ac.setRnom(nom);
            ac.setRimage(image);
            ac.setRprix(Float.toString(prix));
            ac.setRquantite(Integer.toString(quantite));
            ac.setRdescription(description);
            ac.setRcategorie(category.toString());
            ac.setRid_bilan_financier(Integer.toString(id_bilan_financier));
            ac.setRid_admin(Integer.toString(id_admin));
            ac.setRlist(ps.readAll().toString());
            txtnom.getScene().setRoot(root);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/
    }
}



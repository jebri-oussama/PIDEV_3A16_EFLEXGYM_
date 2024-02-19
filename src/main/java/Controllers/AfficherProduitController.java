package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AfficherProduitController {
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

    public void setRnom(String rnom) {
        this.rnom.setText(rnom);
    }

    public void setRimage(String rimage) {
        this.rimage.setText(rimage);
    }

    public void setRprix(String rprix) {
        this.rprix.setText(rprix);
    }
    public void setRquantite(String rquantite) {
        this.rquantite.setText(rquantite);
    }
    public void setRdescription(String rdescription) {
        this.rdescription.setText(rdescription);
    }
    public void setRcategorie(String rcategorie) {
        this.rcategorie.setText(rcategorie);
    }
    public void setRid_bilan_financier(String rid_bilan_financier) {
        this.rid_bilan_financier.setText(rid_bilan_financier);
    }
    public void setRid_admin(String rid_admin) {
        this.rid_admin.setText(rid_admin);
    }
    public void setRlist(String rlist) {
        this.rlist.setText(rlist);
    }
}
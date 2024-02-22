package gestion_evenement.Controllers;

import gestion_evenement.entities.Type;
import gestion_evenement.entities.EventBus;
import gestion_evenement.entities.Type;

import gestion_evenement.service.TypeService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.control.TextField;
import javafx.stage.Stage;



import java.util.List;

public class AjouterTypeController {

    @FXML
    private TextField txttypeName;


    @FXML
    void addType(ActionEvent event) {
        if (!validateInput()) {
            return;
        }
        String typeName = txttypeName.getText();



        Type type = new Type(typeName);

        TypeService typeService = new TypeService();
        typeService.add(type);
        EventBus.getInstance().notifyTableRefreshed();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private boolean validateInput() {
        if (txttypeName.getText() == null) {
            System.out.println("Please insert a type.");
            return false;
        }
    return true;}

}


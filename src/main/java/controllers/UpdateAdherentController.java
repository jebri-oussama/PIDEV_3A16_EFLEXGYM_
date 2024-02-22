package controllers;

import gestion_user.entities.Role;
import gestion_user.entities.Sexe;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.DataSource;

import java.sql.Date;

public class UpdateAdherentController {
    @FXML
    private Button Confirm;

    @FXML
    private DatePicker dateNaissance;

    @FXML
    private TextField email;

    @FXML
    private TextField id_adherent;

    @FXML
    private TextField mot_de_passe;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField sexe;
    @FXML
    private RadioButton rbA;

    @FXML
    private RadioButton rbC;
    @FXML
    private Label labelBilan;

    @FXML
    private Label labelSalaire;
    @FXML
    private ToggleGroup RTG;
    @FXML
    private TextField Salaire;
    @FXML
    private TextField idBilan;



    public void initData(User selectedAdherent) {
        id_adherent.setText(String.valueOf(selectedAdherent.getId()));
        nom.setText(selectedAdherent.getNom());
        prenom.setText(selectedAdherent.getPrenom());
        mot_de_passe.setText(selectedAdherent.getMot_de_passe());
        email.setText(selectedAdherent.getEmail());
        dateNaissance.getValue();
        sexe.setText(selectedAdherent.getSexe().toString());
        if(selectedAdherent.getRole().toString().equals("Adherent")){
                rbA.fire();}
        else if (selectedAdherent.getRole().toString().equals("Coach")){
            rbC.fire();}


    }

        @FXML
        void handleUpdate(ActionEvent event) {


        UserService as = new UserService();
        int adherentId = Integer.parseInt(id_adherent.getText());
        String adherentNom = nom.getText();
        String adherentPrenom = prenom.getText();
        String adherentMotDePasse = mot_de_passe.getText();
        String adherentEmail = email.getText();
        Date adherentDateNaissance = Date.valueOf(dateNaissance.getValue().toString());
        String SexeString = sexe.getText();
        Sexe sexe1 = Sexe.valueOf(SexeString);
        String selectedRole = getSelectedRole();
            if ("Coach".equals(selectedRole)){
              Integer  id_bilan = Integer.valueOf(idBilan.getText());
               Double salaire = Double.valueOf(Salaire.getText());
                User a1 = new User(adherentId, adherentNom, adherentPrenom, adherentMotDePasse, adherentEmail, adherentDateNaissance, sexe1, Role.valueOf(selectedRole),salaire,id_bilan);
                as.updateAdherent(adherentId,a1);
                // Implement logic for the update action when confirming changes
                Stage stage = (Stage) Confirm.getScene().getWindow();
                stage.close();
            } else
        // Get the values from the text fields

            { User a1 = new User(adherentId, adherentNom, adherentPrenom, adherentMotDePasse, adherentEmail, adherentDateNaissance, sexe1, Role.valueOf(selectedRole));
        as.updateAdherent(adherentId,a1);
        // Implement logic for the update action when confirming changes
        Stage stage = (Stage) Confirm.getScene().getWindow();
        stage.close();
            }
    }

    private String getSelectedRole() {
        if (rbA.isSelected()) {
            return "Adherent";
        } else if (rbC.isSelected()) {

            return "Coach";
        } else {
            // Handle the case when neither is selected
            return "";
        }
    }


    @FXML
    void handleRoleSelection(ActionEvent event) {
        updateAdditionalFieldsVisibility();
    }

    private void updateAdditionalFieldsVisibility() {
        String selectedRole = getSelectedRole();
        boolean isCoachSelected = "Coach".equals(selectedRole);

        labelBilan.setVisible(isCoachSelected);
        idBilan.setVisible(isCoachSelected);
        labelSalaire.setVisible(isCoachSelected);
        Salaire.setVisible(isCoachSelected);
    }
    @FXML
    void handleRoleSelectionAD(ActionEvent event) {
        updateAdditionalFieldsVisibility1();
    }

    private void updateAdditionalFieldsVisibility1() {
        String selectedRole = getSelectedRole();
        boolean isCoachSelected = "Adherent".equals(selectedRole);

        labelBilan.setVisible(false);
        idBilan.setVisible(false);
        labelSalaire.setVisible(false);
        Salaire.setVisible(false);
    }

    // Add methods for handling the update action...
}

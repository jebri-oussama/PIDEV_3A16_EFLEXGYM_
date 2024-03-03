package controllers;

import GestionFinance.service.BilanFinancierService;
import gestion_user.entities.ProfileUpdateEvent;
import gestion_user.entities.Role;
import gestion_user.entities.Sexe;
import gestion_user.entities.User;
import gestion_user.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.DataSource;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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
    private RadioButton rbAdmin;


    @FXML
    private Label labelBilan;

    @FXML
    private Label labelSalaire;
    @FXML
    private ToggleGroup RTG;
    @FXML
    private TextField Salaire;
    @FXML
    private ChoiceBox<Integer> idBilanChoiceBox;
    @FXML
    private ToggleGroup sexeGroup;

    @FXML
    private RadioButton rbFemelle;

    @FXML
    private RadioButton rbMale;
    @FXML
    private Button refresh;



    public void initData(User selectedAdherent) {
        id_adherent.setText(String.valueOf(selectedAdherent.getId()));
        nom.setText(selectedAdherent.getNom());
        prenom.setText(selectedAdherent.getPrenom());
        mot_de_passe.setText(selectedAdherent.getMot_de_passe());
        email.setText(selectedAdherent.getEmail());
        dateNaissance.setValue(LocalDate.parse(selectedAdherent.getFormattedDateNaissance()));

        if(selectedAdherent.getSexe().toString().equals("male")){
            rbMale.fire();
        } else {
            rbFemelle.fire(); }

        if(selectedAdherent.getRole().toString().equals("Adherent")){
                rbA.fire();}
        else if (selectedAdherent.getRole().toString().equals("Coach")){
            rbC.fire();
            initializeIdBilanChoiceBox();
            idBilanChoiceBox.setValue(Integer.valueOf(selectedAdherent.getBilanFinancier()));            Salaire.setText(String.valueOf(Double.valueOf(selectedAdherent.getSalaire())));
        }else{rbAdmin.fire();}


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
            Sexe sexe1 = getSelectedSexe();
        String selectedRole = getSelectedRole();
            if ("Coach".equals(selectedRole)){
              Integer  id_bilan = idBilanChoiceBox.getValue();
               Double salaire = Double.valueOf(Salaire.getText());
                User a1 = new User(adherentId, adherentNom, adherentPrenom, adherentMotDePasse, adherentEmail, adherentDateNaissance, sexe1, Role.valueOf(selectedRole),salaire,id_bilan);
                as.updateAdherent(adherentId,a1);
                // Implement logic for the update action when confirming changes


                Stage stage = (Stage) Confirm.getScene().getWindow();

                stage.close();

            } else if ("Adherent".equals(selectedRole))
        // Get the values from the text fields

            { User a1 = new User(adherentId, adherentNom, adherentPrenom, adherentMotDePasse, adherentEmail, adherentDateNaissance, sexe1, Role.valueOf(selectedRole));
        as.updateAdherent(adherentId,a1);
        // Implement logic for the update action when confirming changes

        Stage stage = (Stage) Confirm.getScene().getWindow();
        stage.close();
            }else if ("Admin".equals(selectedRole)) {
                User a1 = new User(adherentId, adherentNom, adherentPrenom, adherentMotDePasse, adherentEmail, adherentDateNaissance, sexe1, Role.valueOf(selectedRole));
                as.updateAdherent(adherentId,a1);
                // Implement logic for the update action when confirming changes

                Stage stage = (Stage) Confirm.getScene().getWindow();
                stage.close();
            }
    }

    private void openAdherentControllerStage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Adherent.fxml"));
        try {
            Parent root = loader.load();
            AdherentController adherentController = loader.getController();
            adherentController.initialize();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestion des Users");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Sexe getSelectedSexe() {
        RadioButton selectedRadioButton = (RadioButton) sexeGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            return Sexe.valueOf(selectedRadioButton.getText());
        } else {
            // Handle the case when no RadioButton is selected
            return null;
        }
    }

    private String getSelectedRole() {
        if (rbA.isSelected()) {
            return "Adherent";
        } else if (rbC.isSelected()) {

            return "Coach";
        }else if (rbAdmin.isSelected()){
            return "Admin";
        }
        else {
            // Handle the case when neither is selected
            return "";
        }
    }


    @FXML
    void handleRoleSelection(ActionEvent event) {
        updateAdditionalFieldsVisibility();
        if (rbC.isSelected()) {
            initializeIdBilanChoiceBox();
        }
    }

    private void updateAdditionalFieldsVisibility() {
        String selectedRole = getSelectedRole();
        boolean isCoachSelected = "Coach".equals(selectedRole);

        labelBilan.setVisible(isCoachSelected);
        idBilanChoiceBox.setVisible(isCoachSelected);
        labelSalaire.setVisible(isCoachSelected);
        Salaire.setVisible(isCoachSelected);
    }
    @FXML
    void handleRoleSelectionAD(ActionEvent event) {
        updateAdditionalFieldsVisibility1();
    }

    @FXML
    void handleRoleSelectedAdmin(ActionEvent event){
        updateAdditionalFieldsVisibility2();
    }

    private void updateAdditionalFieldsVisibility1() {
        String selectedRole = getSelectedRole();
        boolean isCoachSelected = "Adherent".equals(selectedRole);

        labelBilan.setVisible(false);
        idBilanChoiceBox.setVisible(false);
        labelSalaire.setVisible(false);
        Salaire.setVisible(false);
    }
    private void updateAdditionalFieldsVisibility2() {
        String selectedRole = getSelectedRole();
        boolean isCoachSelected = "Admin".equals(selectedRole);

        labelBilan.setVisible(false);
        idBilanChoiceBox.setVisible(false);
        labelSalaire.setVisible(false);
        Salaire.setVisible(false);
    }

    private void initializeIdBilanChoiceBox() {
        // Fetch the list of BilanFinancier IDs and add them to the choice box
        List<Integer> idBilanFinanciers = BilanFinancierService.getAllIdBilanFinancier();
        idBilanChoiceBox.getItems().setAll(idBilanFinanciers);

        // Optionally, set a default value if needed
        if (!idBilanFinanciers.isEmpty()) {
            idBilanChoiceBox.setValue(idBilanFinanciers.get(0));
        }
    }

}

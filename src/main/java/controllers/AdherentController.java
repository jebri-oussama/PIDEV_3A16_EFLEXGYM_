package controllers;



import gestion_user.entities.Role;
import gestion_user.entities.Sexe;
import gestion_user.entities.User;
import gestion_user.service.UserService;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.stage.Stage;
import utils.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class AdherentController {
    @FXML
    private TextField id_adherent;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField mot_de_passe;

    @FXML
    private TextField email;

    @FXML
    private DatePicker dateNaissance;

    @FXML
    private TextField sexe;

    @FXML
    private Button ajout;



    @FXML
    private Button refreshButton;


    @FXML
    void handleRefresh(ActionEvent event) {
        adherentList.clear(); // Clear existing data
        readAllAdherents();   // Reload data from the database
    }


    @FXML
    private TableView<User> list;

    @FXML
    private TableColumn<User, String> colId;

    @FXML
    private TableColumn<User, String> colNom;

    @FXML
    private TableColumn<User, String> colPrenom;

    @FXML
    private TableColumn<User, String> colMotDePasse;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colDateNaissance;

    @FXML
    private TableColumn<User, String> colSexe;

    @FXML
    private TableColumn<User, String> colRole;

    @FXML
    private TableColumn<User, Void> colUpdate;

    @FXML
    private TableColumn<User, Void> colDelete;

    @FXML
    private TableColumn<User, String> colBilan;

    @FXML
    private TableColumn<User, String> colSalaire;

    @FXML
    private RadioButton rbAdherent;

    @FXML
    private Group role;

    @FXML
    private RadioButton rbCoach;


    @FXML
    private TextField id_bilan;

    @FXML
    private Label labelBilan;

    @FXML
    private Label labelSalaire;

    @FXML
    private TextField salaire;

    private ObservableList<User> adherentList = FXCollections.observableArrayList();

    // Add methods to handle Adherent form interactions
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;


    private String getSelectedRole() {
        if (rbAdherent.isSelected()) {
            return "Adherent";
        } else if (rbCoach.isSelected()) {

            return "Coach";
        } else {
            // Handle the case when neither is selected
            return "";
        }
    }


    @FXML
    void ajouterAdherent(ActionEvent event) {


        conn = DataSource.getInstance().getCnx();
        UserService as = new UserService();

        as.readAllAdherent();
        Integer idBilan= null;
        Double Salaire =null;

        //int adherentId = Integer.parseInt(id_adherent.getText());
        String adherentNom = nom.getText();
        String adherentPrenom = prenom.getText();
        String adherentMotDePasse = mot_de_passe.getText();
        String adherentEmail = email.getText();
        Date adherentDateNaissance = Date.valueOf(dateNaissance.getValue().toString());
        String SexeString = sexe.getText();
        Sexe sexe1 = Sexe.valueOf(SexeString);
        String selectedRole = getSelectedRole();
        if ("Coach".equals(selectedRole)){
             idBilan = Integer.valueOf(id_bilan.getText());
            Salaire = Double.valueOf(salaire.getText());
            User a1 = new User(adherentNom, adherentPrenom, adherentMotDePasse, adherentEmail, adherentDateNaissance, sexe1,Role.valueOf(selectedRole),Salaire,idBilan);
            as.addAdherent(a1);
            adherentList.add(a1);

        }
        // Get the values from the text fields
        User a1 = new User(adherentNom, adherentPrenom, adherentMotDePasse, adherentEmail, adherentDateNaissance, sexe1,Role.valueOf(selectedRole));
        as.addAdherent(a1);
        adherentList.add(a1);

    };
    @FXML
    void handleRoleSelection(ActionEvent event) {
        updateAdditionalFieldsVisibility();
    }

    private void updateAdditionalFieldsVisibility() {
        String selectedRole = getSelectedRole();
        boolean isCoachSelected = "Coach".equals(selectedRole);

        labelBilan.setVisible(isCoachSelected);
        id_bilan.setVisible(isCoachSelected);
        labelSalaire.setVisible(isCoachSelected);
        salaire.setVisible(isCoachSelected);
    }
    @FXML
    void handleRoleSelectionAD(ActionEvent event) {
        updateAdditionalFieldsVisibility1();
    }

    private void updateAdditionalFieldsVisibility1() {
        String selectedRole = getSelectedRole();
        boolean isCoachSelected = "Adherent".equals(selectedRole);

        labelBilan.setVisible(false);
        id_bilan.setVisible(false);
        labelSalaire.setVisible(false);
        salaire.setVisible(false);
    }


    @FXML
    public void initialize() {
        initializeTableView();
        readAllAdherents();
        initializeRefreshButton();
    }

    private void initializeRefreshButton() {
    }

    private void initializeTableView() {
        // Initialize TableView columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colMotDePasse.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDateNaissance.setCellValueFactory(cellData -> {
            StringProperty property = new SimpleStringProperty();
            User adherent = cellData.getValue();
            property.setValue(adherent.getFormattedDateNaissance());
            return property;
        });
        colSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colBilan.setCellValueFactory(new PropertyValueFactory<>("id_bilan_financier"));
        colSalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));

        // Set TableView items
        list.setItems(adherentList);
        addButtonColumns();

    }



        private void addButtonColumns() {

            colUpdate.setCellFactory(param -> new TableCell<>() {
                private final Button updateButton = new Button("Update");

                {
                    updateButton.setOnAction(event -> {
                        User selectedAdherent = getTableView().getItems().get(getIndex());
                        openUpdatePage(selectedAdherent);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(updateButton);
                    }                }
            });

        colDelete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    User adherent = getTableView().getItems().get(getIndex());
                    // Handle delete action for the selected adherent
                    handleDeleteAction(adherent);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });



    }

    private void handleDeleteAction(User adherent) {
        // Implement your logic for delete action
        // You may show a confirmation dialog before deleting
        adherentList.remove(adherent);
        UserService as = new UserService();
        as.deleteAdherent(adherent.getId());
        System.out.println("Delete Action for Adherent ID: " + adherent.getId());
    }


        private void readAllAdherents() {
        // Read all Adherents from the database and populate the TableView
        List<User> adherents = new UserService().readAllAdherent();
        adherentList.addAll(adherents);

    }

    private void clearInputFields() {
        id_adherent.clear();
        nom.clear();
        prenom.clear();
        mot_de_passe.clear();
        email.clear();
        dateNaissance.getEditor().clear();
        sexe.clear();


    }

    private void openUpdatePage(User selectedAdherent) {

            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/UpdateAdherent.fxml"));
        try {
            Parent root = loader.load();

            UpdateAdherentController updateController = loader.getController();
            updateController.initData(selectedAdherent);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}






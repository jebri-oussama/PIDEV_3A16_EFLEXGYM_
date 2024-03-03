package Controllers;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gestion_produit.entities.type;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import GestionFinance.entites.BilanFinancier;
import gestion_produit.entities.categorie;
import gestion_produit.entities.produit;
import gestion_produit.service.produitService;
import utils.DataSource;

public class AfficherProduitController implements Initializable {

    @FXML
    private TableView<produit> tableViewProduit;

    @FXML
    private TableColumn<produit, Integer> idColumn;

    @FXML
    private TableColumn<produit, String> nomColumn;

    @FXML
    private TableColumn<produit, Integer> prixColumn;

    @FXML
    private TableColumn<produit, Integer> quantiteColumn;

    @FXML
    private TableColumn<produit, String> descriptionColumn;

    @FXML
    private TableColumn<produit, String> imageColumn;
    @FXML
    private TableColumn<produit, type> categorieColumn;

    @FXML
    private TableColumn<produit, Integer> idbilanfinancierColumn;

    @FXML
    private TableColumn<produit, String> idadminColumn;

    @FXML
    private TableColumn<produit, Void> deleteColumn;

    @FXML
    private TableColumn<produit, Void> selectColumn;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtnom;


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
    private ComboBox<Integer> comboBoxBilan;

    @FXML
    private ToggleButton chkAlimentaire;

    @FXML
    private ToggleButton chkVestimentaire;
@FXML
private ImageView imageView;
    private produitService produitService;
    private Connection conn;
    private PreparedStatement pst;

    private final ObservableList<produit> produits = FXCollections.observableArrayList();
    private String image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        produitService = new produitService();
        imageColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<produit, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<produit, String> param) {
                return new SimpleStringProperty(param.getValue().getImage());
            }
        });

        imageColumn.setCellFactory(column -> {
            return new TableCell<produit, String>() {
                private final ImageView imageView = new ImageView();

                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(imageView);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                }

                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);
                    if (empty || imagePath == null || imagePath.isEmpty()) {
                        imageView.setImage(null);
                    } else {
                        imageView.setImage(new Image(imagePath));
                    }
                }
            };
        });



        setupTableColumns();
        refreshTable();
        initializeChoiceBox();
    }

    public AfficherProduitController() {
        conn = DataSource.getInstance().getCnx();
    }

    private void initializeChoiceBox() {
        List<Integer> bilanIds = fetchBilanIdsFromDatabase();
        comboBoxBilan.setItems(FXCollections.observableArrayList(bilanIds));
    }

    private List<Integer> fetchBilanIdsFromDatabase() {
        List<Integer> bilanIds = new ArrayList<>();

        try {
            Connection connection = DataSource.getInstance().getCnx();
            String query = "SELECT id FROM bilan_financier";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                bilanIds.add(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bilanIds;
    }

    private void setupTableColumns() {
       // idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        categorieColumn.setCellValueFactory(data -> {
            type categoryType = data.getValue().getCategorie().getType();
            return new SimpleObjectProperty(categoryType);
        });
        idbilanfinancierColumn.setCellValueFactory(data -> {
            int bilanid = data.getValue().getId_bilan_financier().getId();
            return new SimpleIntegerProperty(bilanid).asObject();
        });
        idadminColumn.setCellValueFactory(new PropertyValueFactory<>("id_admin"));


        deleteColumn.setCellFactory(createDeleteButtonCellFactory());
        selectColumn.setCellFactory(createSelectButtonCellFactory());
    }

    private Callback<TableColumn<produit, Void>, TableCell<produit, Void>> createDeleteButtonCellFactory() {
        return new Callback<TableColumn<produit, Void>, TableCell<produit, Void>>() {
            @Override
            public TableCell<produit, Void> call(final TableColumn<produit, Void> param) {
                return new TableCell<produit, Void>() {
                    private final Button deleteButton = new Button("Supprimer");

                    {
                        deleteButton.setOnAction((ActionEvent event) -> {
                            produit produit = getTableView().getItems().get(getIndex());
                            supprimerProduit(produit.getId());
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
                };
            }
        };
    }

    private Callback<TableColumn<produit, Void>, TableCell<produit, Void>> createSelectButtonCellFactory() {
        return new Callback<TableColumn<produit, Void>, TableCell<produit, Void>>() {
            @Override
            public TableCell<produit, Void> call(final TableColumn<produit, Void> param) {
                return new TableCell<produit, Void>() {
                    private final Button selectButton = new Button("Sélectionner");

                    {
                        selectButton.setOnAction((ActionEvent event) -> {
                            produit produit = getTableView().getItems().get(getIndex());
                            updateSelectedProductFields(produit);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(selectButton);
                        }
                    }
                };
            }
        };
    }


    private void supprimerProduit(int id) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this product?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                produitService.delete(id);
                refreshTable();
            }
        });
    }

    private void updateSelectedProductFields(produit produit) {
        txtId.setText(String.valueOf(produit.getId()));
        txtnom.setText(produit.getNom());
        txtprix.setText(String.valueOf(produit.getPrix()));
        txtquantite.setText(String.valueOf(produit.getQuantite()));
        txtdescription.setText(produit.getDescription());

        ToggleButton toggleButton = getToggleButtonForCategoryType(produit.getCategorie().getType());
        if (toggleButton != null) {
            toggleButton.setSelected(true);
        }
        int bilanId = produit.getId_bilan_financier().getId();

        // Set the ComboBox value to the BilanFinancier ID
        comboBoxBilan.setValue(bilanId);

        txtid_admin.setText(String.valueOf(produit.getId_admin()));
        // Set the image
        image = produit.getImage();
        Image img = new Image(image);
        imageView.setImage(img);
    }
    private ToggleButton getToggleButtonForCategoryType(type categoryType) {
        if (categoryType == type.alimentaire) {
            return chkAlimentaire;
        } else if (categoryType == type.vestimentaire) {
            return chkVestimentaire;
        }
        return null;
    }


    @FXML
    public void updateProduit(ActionEvent event) {
        produit selectedProduit = tableViewProduit.getSelectionModel().getSelectedItem();
        int id = Integer.parseInt(txtId.getText());
        String nom = txtnom.getText();
        float prix = Float.parseFloat(txtprix.getText());
        int quantite = Integer.parseInt(txtquantite.getText());
        String description = txtdescription.getText();
        int categoryId;
        if (chkAlimentaire.isSelected()) {
            categoryId = 7;
        } else if (chkVestimentaire.isSelected()) {
            categoryId = 6;
        } else {
            showAlert(AlertType.ERROR, "Erreur", "Aucune catégorie sélectionnée", "Veuillez sélectionner une catégorie.");
            return;
        }
        categorie category = new categorie(categoryId, null, null);
        Integer selectedBilanId = comboBoxBilan.getValue();
        BilanFinancier bilan = new BilanFinancier(selectedBilanId, null, null, 0, 0, 0, 0, 0, 0);
        int id_admin = Integer.parseInt(txtid_admin.getText());
        // Retrieve image from the field
        String updatedImage = image;
        produit updatedProduit = new produit(nom, updatedImage, prix, quantite, description, category, bilan, id_admin);
        produitService.update(id, updatedProduit);
        txtId.setText("");
        clearFields();
        refreshTable();
    }


    @FXML
    void addProduit() throws SQLException {

        if (txtnom.getText().isEmpty()  || txtprix.getText().isEmpty() ||
                txtquantite.getText().isEmpty() || txtdescription.getText().isEmpty() ||
                txtid_admin.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Champ(s) vide(s)", "Veuillez remplir tous les champs.");
            return;
        }
        float prix;
        try {
            prix = Float.parseFloat(txtprix.getText());
            if (prix < 0) {
                showAlert(AlertType.ERROR, "Erreur", "Prix invalide", "Le prix ne peut pas être négatif.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Erreur", "Prix invalide", "Veuillez saisir un prix valide.");
            return;
        }
        int quantite;
        try {
            quantite = Integer.parseInt(txtquantite.getText());
            if (quantite < 0) {
                showAlert(AlertType.ERROR, "Erreur", "Quantité invalide", "La quantité ne peut pas être négative.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Erreur", "Quantité invalide", "Veuillez saisir une quantité valide.");
            return;
        }

        // Validate and parse the admin ID
        int id_admin;
        try {
            id_admin = Integer.parseInt(txtid_admin.getText());
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Erreur", "ID administrateur invalide", "Veuillez saisir un ID administrateur valide.");
            return;
        }


        int categoryId;
        if (chkAlimentaire.isSelected()) {
            categoryId = 7;
        } else if (chkVestimentaire.isSelected()) {
            categoryId = 6;
        } else {
            showAlert(AlertType.ERROR, "Erreur", "Aucune catégorie sélectionnée", "Veuillez sélectionner une catégorie.");
            return;
        }
        Integer selectedBilan = comboBoxBilan.getValue();
        if (selectedBilan == null || selectedBilan == 0) {
            showAlert(AlertType.ERROR, "Erreur", "Aucun bilan financier sélectionné", "Veuillez sélectionner un bilan financier.");
            return;
        }
        String image=this.image;
        categorie category = new categorie(categoryId, null, null);
        BilanFinancier bilan = new BilanFinancier(selectedBilan, null, null, 0, 0, 0, 0, 0, 0);
        produit p = new produit(txtnom.getText(), image, prix, quantite, txtdescription.getText(), category, bilan, id_admin);

        // Call the service to add the product
        produitService.add(p);
        clearFields();
        refreshTable();
    }




    @FXML
    void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            image= selectedFile.toURI().toString();
            Image image1 = new Image(image);
            imageView.setImage(image1);
        }

    }
    private void showAlert(AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void clearFields() {
        txtId.setText("");
        txtnom.setText("");
        txtprix.setText("");
        txtquantite.setText("");
        txtdescription.setText("");
imageView.setImage(null);
        txtid_admin.setText("");
        comboBoxBilan.getSelectionModel().clearSelection();
    }

    private void refreshTable() {
        produits.clear();
        List<produit> produitList = produitService.readAll();
        produits.addAll(produitList);
        tableViewProduit.setItems(produits);
    }
}

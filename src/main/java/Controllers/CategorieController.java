package Controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import gestion_produit.service.categorieService;
import gestion_produit.entities.type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import gestion_produit.entities.categorie;
import utils.DataSource;

public class CategorieController implements Initializable {

    @FXML
    private TableView<categorie> tableViewCategorie;

    @FXML
    private TableColumn<categorie, Integer> idColumn;

    @FXML
    private TableColumn<categorie, type> typeColum;

    @FXML
    private TableColumn<categorie, String> descriptionColumn;
    @FXML
    private TableColumn<categorie, Void> deleteColumn;

    @FXML
    private TableColumn<categorie, Void> selectColumn;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txttype;

    @FXML
    private TextField Txtdescription;


    @FXML
    private ComboBox<Integer> comboBoxBilan;

    private categorieService categorieService;
    private Connection conn;
    private PreparedStatement pst;

    private final ObservableList<categorie> categories = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categorieService = new categorieService();

        setupTableColumns();
        refreshTable();
    }

    public CategorieController() {
        conn = DataSource.getInstance().getCnx();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColum.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Set cell value factory of deleteColumn to null
        deleteColumn.setCellValueFactory(null);

        deleteColumn.setCellFactory(createDeleteButtonCellFactory());
        selectColumn.setCellFactory(createSelectButtonCellFactory());
    }

    private Callback<TableColumn<categorie, Void>, TableCell<categorie, Void>> createDeleteButtonCellFactory() {
        return new Callback<TableColumn<categorie, Void>, TableCell<categorie, Void>>() {
            @Override
            public TableCell<categorie, Void> call(final TableColumn<categorie, Void> param) {
                return new TableCell<categorie, Void>() {
                    private final Button deleteButton = new Button("Supprimer");

                    {
                        deleteButton.setOnAction((ActionEvent event) -> {
                            categorie categorie = getTableView().getItems().get(getIndex());
                            supprimercategorie(categorie.getId());
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

    private Callback<TableColumn<categorie, Void>, TableCell<categorie, Void>> createSelectButtonCellFactory() {
        return new Callback<TableColumn<categorie, Void>, TableCell<categorie, Void>>() {
            @Override
            public TableCell<categorie, Void> call(final TableColumn<categorie, Void> param) {
                return new TableCell<categorie, Void>() {
                    private final Button selectButton = new Button("Sélectionner");

                    {
                        selectButton.setOnAction((ActionEvent event) -> {
                            categorie categorie = getTableView().getItems().get(getIndex());
                            updateSelectedCategorieFields(categorie);
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



    private void supprimercategorie(int id) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this product?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                categorieService.delete(id);
                refreshTable();
            }
        });
    }

    private void updateSelectedCategorieFields(categorie categorie) {
        txtId.setText(String.valueOf(categorie.getId()));
        txttype.setText(categorie.getType().toString());
        Txtdescription.setText(categorie.getDescription());
    }

    @FXML
    public void updatecategorie(ActionEvent event) {
        categorie selectedcategorie = tableViewCategorie.getSelectionModel().getSelectedItem();
        int id = Integer.parseInt(txtId.getText());
        type type;
        type = null;
        type = type.valueOf(txttype.getText());
        String description = Txtdescription.getText();
        categorie updatedCategorie = new categorie(id, type, description);
        categorieService.update(id, updatedCategorie);
        txtId.setText("");
        clearFields();
        refreshTable();
    }


    @FXML
    void addCategorie() throws SQLException {
        if (txttype.getText().isEmpty() || Txtdescription.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Champ(s) vide(s)", "Veuillez remplir tous les champs.");
            return;
        }
        int id = Integer.parseInt(txtId.getText());
        String typeText = txttype.getText().toLowerCase();
        type categoryType = null;
        if (typeText.equals("alimentaire") || typeText.equals("vestimentaire")) {
            categoryType = type.valueOf(typeText.toUpperCase());
        } else {
            showAlert(AlertType.ERROR, "Erreur", "Type invalide", "Le type doit être 'alimentaire' ou 'vestimentaire'.");
            return;
        }
        String description = Txtdescription.getText();
        categorie c = new categorie(id, categoryType, description);
        categorieService.add(c);
        clearFields();
        refreshTable();
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
        txttype.setText("");
        Txtdescription.setText("");
    }

    private void refreshTable() {
        categories.clear();
        List<categorie> produitList = categorieService.readAll();
        categories.addAll(produitList);
        tableViewCategorie.setItems(categories);
    }
}

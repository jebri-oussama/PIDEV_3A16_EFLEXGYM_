package Controllers;

import gestion_produit.entities.panier;
import gestion_produit.service.panierService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.DataSource;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class panierController implements Initializable {
    private final ObservableList<panier> paniers = FXCollections.observableArrayList();
    private panierService ps;

    @FXML
    private TableView<panier> panierTable;

    @FXML
    private Label subtotalLabel;

    @FXML
    private TableColumn<panier, Integer> idCol;

    @FXML
    private TableColumn<panier, String> nomCol;

    @FXML
    private TableColumn<panier, Float> prixCol;

    @FXML
    private TableColumn<panier, Void> deleteCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        deleteCol.setCellFactory(createDeleteButtonCellFactory());
        ps = new panierService();
        updateSubtotalLabel();
        refreshTable();
    }

    private Callback<TableColumn<panier, Void>, TableCell<panier, Void>> createDeleteButtonCellFactory() {
        return new Callback<TableColumn<panier, Void>, TableCell<panier, Void>>() {
            @Override
            public TableCell<panier, Void> call(final TableColumn<panier, Void> param) {
                return new TableCell<panier, Void>() {
                    private final Button deleteButton = new Button("Supprimer");

                    {
                        deleteButton.setOnAction((ActionEvent event) -> {
                            panier panier = getTableView().getItems().get(getIndex());
                            supprimerProduit(panier.getId());
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

    private void supprimerProduit(int id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this product?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                ps.delete(id);
                refreshTable();
            }
        });
    }

    @FXML
    private void goBackToProducts(ActionEvent event) {
        Stage stage = (Stage) panierTable.getScene().getWindow();
        stage.close();
    }

    public void closeConnection() {
        try {
            Connection conn = DataSource.getInstance().getCnx();
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private float calculateTotalPrice() {
        float totalPrice = 0.0f;
        for (panier p : paniers) {
            totalPrice += p.getPrix();
        }
        return totalPrice;
    }
    @FXML
    private void updateSubtotalLabel() {
        subtotalLabel.setText("Subtotal: $ " + calculateTotalPrice());
    }

    public void refreshTable() {
        paniers.clear();
        paniers.addAll(ps.readAll());
        panierTable.setItems(paniers);
        updateSubtotalLabel();

    }

    @FXML
    private void openPaymentForm(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/paymentForm.fxml"));
            Parent root = loader.load();

            // Get the controller
            PaymentController paymentFormController = loader.getController();
            Stage panierStage = (Stage) panierTable.getScene().getWindow();
            panierStage.close();
            // You might need to pass data to the payment form controller if necessary
            paymentFormController.setAmountToPay(calculateTotalPrice());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Payment");
            stage.show();
            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void gotomap(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/userInterface.fxml"));
            Parent root = loader.load();

            // Get the controller
            userInterfaceController userInterfaceController = loader.getController();
            Stage panierStage = (Stage) panierTable.getScene().getWindow();
            panierStage.close();


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("your location");
            stage.show();
            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
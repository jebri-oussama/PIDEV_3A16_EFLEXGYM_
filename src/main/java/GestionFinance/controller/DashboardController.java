package GestionFinance.controller;

import GestionFinance.pdf.PdfGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.fx.ChartViewer;
import GestionFinance.entites.BilanFinancier;
import GestionFinance.service.BilanFinancierService;
import gestion_user.service.UserService;
import gestion_user.entities.Role;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Text adherentsText;

    @FXML
    private Text coachsText;
    @FXML
    private Button dashboardId;
    @FXML
    private Button bilanFinancierId;
    @FXML
    private Button abonnementsId;
    @FXML
    private Button covidId;
    @FXML
    void afficherBilanFinancier() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherBilanFinancier.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void afficherAbonnements() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void afficherDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private ChartViewer bilanChartViewer;

    @FXML
    private ChartViewer oscillationsChartViewer;
    @FXML
    private AnchorPane root;

    @FXML
    private Button generatePDFButton;
    private UserService userService;
    private BilanFinancierService bilanFinancierService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService = new UserService();
        bilanFinancierService = new BilanFinancierService();
        try {
            updateAdherents(userService.countByRole(Role.Adherent));
            updateCoachs(userService.countByRole(Role.Coach));
            initializeBilanFinancierChart();
            initializeOscillationsChart();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        root = (AnchorPane) adherentsText.getParent();
        generatePDFButton.setOnAction(event -> generatePDF());
    }


    private void initializeBilanFinancierChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        List<BilanFinancier> bilans = bilanFinancierService.readAll();
        for (BilanFinancier bilan : bilans) {
            LocalDate date = bilan.getDate_debut();
            Month month = date.getMonth();
            String mois = month.getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH);
            dataset.addValue(bilan.getProfit(), "Profit", mois);
        }


        JFreeChart chart = ChartFactory.createBarChart(
                "Bilan Financier",
                "Mois",
                "Profit",
                dataset);
        bilanChartViewer.setChart(chart);
    }

    private void initializeOscillationsChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<BilanFinancier> bilans = bilanFinancierService.readAll();
        for (BilanFinancier bilan : bilans) {
            LocalDate date = bilan.getDate_debut();
            Month month = date.getMonth();
            String mois = month.getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH);

            double revenusAbonnements = bilan.recupererRevenuAbonnements();
            double revenusProduits = bilan.recupererRevenusProduits();

            dataset.addValue(revenusAbonnements, "Revenus Abonnements", mois);
            dataset.addValue(revenusProduits, "Revenus Produits", mois);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Oscillations des Revenus",
                "Mois",
                "Revenus",
                dataset);
        oscillationsChartViewer.setChart(chart);
    }


    private void updateAdherents(int count) {
        adherentsText.setText(String.valueOf(count));
    }

    private void updateCoachs(int count) {
        coachsText.setText(String.valueOf(count));
    }

    private void generatePDF() {
        String outputPath = "dashboard.pdf";
        PdfGenerator.generatePdf(root, outputPath);
    }

    public void getCovidData(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Covid.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

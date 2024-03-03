package GestionFinance.controller;

import GestionFinance.pdf.PdfGenerator;
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
    private Button generatePDFButton; // Ajout du bouton pour générer le PDF

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

        // Assurez-vous que root est correctement initialisé avant d'appeler generatePDF()
        root = (AnchorPane) adherentsText.getParent();
        generatePDFButton.setOnAction(event -> generatePDF());
    }


    private void initializeBilanFinancierChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Charger les données du service pour le bilan financier
        List<BilanFinancier> bilans = bilanFinancierService.readAll();
        for (BilanFinancier bilan : bilans) {
            LocalDate date = bilan.getDate_debut();
            Month month = date.getMonth();
            String mois = month.getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH);
            dataset.addValue(bilan.getProfit(), "Profit", mois);
        }

        // Créer le graphique à barres pour le bilan financier
        JFreeChart chart = ChartFactory.createBarChart(
                "Bilan Financier", // Titre du graphique
                "Mois", // Axe X
                "Profit", // Axe Y
                dataset); // Données

        // Afficher le graphique dans ChartViewer
        bilanChartViewer.setChart(chart);
    }

    private void initializeOscillationsChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Charger les données du service pour les oscillations des revenus
        List<BilanFinancier> bilans = bilanFinancierService.readAll();
        for (BilanFinancier bilan : bilans) {
            LocalDate date = bilan.getDate_debut();
            Month month = date.getMonth();
            String mois = month.getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH);
            // Call methods to retrieve revenues
            double revenusAbonnements = bilan.recupererRevenuAbonnements();
            double revenusProduits = bilan.recupererRevenusProduits();

            dataset.addValue(revenusAbonnements, "Revenus Abonnements", mois);
            dataset.addValue(revenusProduits, "Revenus Produits", mois);
        }

        // Créer le graphique en courbes pour les oscillations des revenus
        JFreeChart chart = ChartFactory.createLineChart(
                "Oscillations des Revenus", // Titre du graphique
                "Mois", // Axe X
                "Revenus", // Axe Y
                dataset); // Données

        // Afficher le graphique dans ChartViewer
        oscillationsChartViewer.setChart(chart);
    }


    private void updateAdherents(int count) {
        adherentsText.setText(String.valueOf(count));
    }

    private void updateCoachs(int count) {
        coachsText.setText(String.valueOf(count));
    }

    private void generatePDF() {
        String outputPath = "dashboard.pdf"; // Chemin où enregistrer le fichier PDF
        PdfGenerator.generatePdf(root, outputPath); // Passer le nœud racine de la scène
    }

}

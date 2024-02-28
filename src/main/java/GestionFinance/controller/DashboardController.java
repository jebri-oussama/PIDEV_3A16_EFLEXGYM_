package GestionFinance.controller;

import GestionFinance.entites.BilanFinancier;
import GestionFinance.service.BilanFinancierService;
import gestion_user.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import gestion_user.entities.Role;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.fx.ChartViewer;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Text adherentsText;

    @FXML
    private Text coachsText;

    @FXML
    private ChartViewer chartViewer; // Utilisation de ChartViewer pour afficher le graphique

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initializeBilanFinancierChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Charger les données du service
        List<BilanFinancier> bilans = bilanFinancierService.readAll();
        for (BilanFinancier bilan : bilans) {
            dataset.addValue(bilan.getProfit(), "Profit", String.valueOf(bilan.getId()));
        }

        // Créer le graphique
        JFreeChart chart = ChartFactory.createBarChart(
                "Bilan Financier", // Titre du graphique
                "ID", // Axe X
                "Profit", // Axe Y
                dataset); // Données

        // Définir le graphique dans le ChartViewer
        chartViewer.setChart(chart);
    }

    private void updateAdherents(int count) {
        adherentsText.setText(String.valueOf(count));
    }

    private void updateCoachs(int count) {
        coachsText.setText(String.valueOf(count));
    }
}

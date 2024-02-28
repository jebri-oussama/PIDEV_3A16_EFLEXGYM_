package GestionFinance.controller;

import GestionFinance.entites.BilanFinancier;
import GestionFinance.service.BilanFinancierService;
import gestion_user.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import gestion_user.entities.Role;
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
    private BarChart<String, Number> bilanFinancierChart;

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
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        bilanFinancierChart = new BarChart<>(xAxis, yAxis);
        List<BilanFinancier> bilans = bilanFinancierService.readAll();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (BilanFinancier bilan : bilans) {
            series.getData().add(new XYChart.Data<>(String.valueOf(bilan.getId()), bilan.getProfit()));
        }
        bilanFinancierChart.getData().add(series);
    }


    private void updateAdherents(int count) {
        adherentsText.setText(String.valueOf(count));
    }

    private void updateCoachs(int count) {
        coachsText.setText(String.valueOf(count));
    }
}

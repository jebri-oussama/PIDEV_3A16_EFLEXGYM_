package gestion_suivi.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class StatistiqueController {


    @FXML
    private LineChart<Number, Number> bmiChart;

    @FXML
    private Rectangle faibleIcon;

    @FXML
    private Rectangle normalIcon;

    @FXML
    private Rectangle eleveIcon;

    public void initialize() {
        // Set up Y-axis (IMC categories)
        NumberAxis yAxis = new NumberAxis(0, 2, 1); // Center Y value
        yAxis.setLabel("IMC Category");
        yAxis.setTickLabelsVisible(true); // Show tick labels
        yAxis.setTickMarkVisible(true); // Show tick marks
        yAxis.setTickUnit(1); // Adjust as needed

        // Create BMI chart
        bmiChart.setTitle("Votre statistique");
        bmiChart.setCreateSymbols(true);
        bmiChart.getData().add(new XYChart.Series<>());
        bmiChart.setLegendVisible(false);
        bmiChart.setAnimated(false);

        // Set Y-axis
        bmiChart.setVerticalGridLinesVisible(false);
        bmiChart.setVerticalZeroLineVisible(false);
        bmiChart.getYAxis().setTickLabelsVisible(true); // Show tick labels
        bmiChart.getYAxis().setTickMarkVisible(true); // Show tick marks

        // Set colors for icons
        faibleIcon.setFill(Color.RED);
        normalIcon.setFill(Color.ORANGE);
        eleveIcon.setFill(Color.BLUE);
    }

    public void populateEvaluationStatistics(double imc) {
        // Determine category for the provided IMC value
        double category = getCategory(imc);

        // Add the IMC data point to the chart
        XYChart.Series<Number, Number> series = bmiChart.getData().get(0);
        series.getData().add(new XYChart.Data<>(0.252, category)); // Set X-value to 0.5
        series.getData().add(new XYChart.Data<>(0.252, imc));
        // Set line color based on category
        if (category == 0) {
            series.getNode().setStyle("-fx-stroke: red;");
        } else if (category == 1) {
            series.getNode().setStyle("-fx-stroke: orange;");
        } else {
            series.getNode().setStyle("-fx-stroke: blue;");
        }
    }

    // Method to determine BMI category based on thresholds
    private double getCategory(double imc) {
        if (imc < 19) {
            return 0; // Faible
        } else if (imc < 25) {
            return 1; // Normal
        } else {
            return 2; // Élevé
        }
    }
}

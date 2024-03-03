package GestionFinance.controller;

import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.json.JSONObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CovidController {

    @FXML
    private BorderPane chartPane;

    @FXML
    private Button fetchDataButton;

    @FXML
    private void initialize() {
        fetchDataButton.setOnAction(this::getData);
    }

    private void getData(ActionEvent event) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://covid-19.dataflowkit.com/v1/TUNISIA"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Log the API response
            System.out.println("API Response: " + response.body());

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.body());

            // Log the parsed JSON data
            System.out.println("Parsed JSON Data: " + jsonResponse);

            // Create a bar chart using JFreeChart
            JFreeChart chart = createBarChart(jsonResponse);

            // Display the chart
            displayChart(chart);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private JFreeChart createBarChart(JSONObject data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(getValueAsInt(data, "Total Cases_text"), "Cases", "Total Cases");
        dataset.addValue(getValueAsInt(data, "New Cases_text"), "Cases", "New Cases");
        dataset.addValue(getValueAsInt(data, "Total Deaths_text"), "Deaths", "Total Deaths");
        dataset.addValue(getValueAsInt(data, "New Deaths_text"), "Deaths", "New Deaths");
        dataset.addValue(getValueAsInt(data, "Total Recovered_text"), "Recovered", "Total Recovered");
        dataset.addValue(getValueAsInt(data, "Active Cases_text"), "Active Cases", "Active Cases");

        return ChartFactory.createBarChart("COVID-19 Statistics", "Category", "Value", dataset);
    }

    private int getValueAsInt(JSONObject data, String key) {
        String value = data.optString(key);
        if (value.isEmpty() || value.equals("N/A")) {
            return 0; // Return 0 for empty or "N/A" values
        } else {
            // Remove commas and parse as integer
            value = value.replaceAll(",", "");
            return Integer.parseInt(value);
        }
    }

    private void displayChart(JFreeChart chart) {
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(new ChartPanel(chart));

        // Clear previous chart and display the new one
        chartPane.setCenter(swingNode);
    }
}

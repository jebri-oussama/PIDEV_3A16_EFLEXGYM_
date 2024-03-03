package gestion_evenement;

import gestion_evenement.entities.Evenement;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp extends Application {

    private static final String API_KEY = "b8bddb9d6a6442d8ffc85697230418a0";

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Evenement evenement = getEventData(); // Assuming you have a method to get the event data

        if (evenement != null) {
            String city = evenement.getPlace();

            try {
                // Make API request to get weather data
                String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                JSONObject weatherObject = weatherArray.getJSONObject(0);
                String weatherDescription = weatherObject.getString("description");

                Label cityLabel = new Label("City: " + city);
                Label weatherLabel = new Label("Weather: " + weatherDescription);

                root.getChildren().addAll(cityLabel, weatherLabel);

            } catch (IOException e) {
                e.printStackTrace();
                Label errorLabel = new Label("Error fetching weather data");
                root.getChildren().add(errorLabel);
            }
        } else {
            Label errorLabel = new Label("No event data found");
            root.getChildren().add(errorLabel);
        }

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Weather App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Evenement getEventData() {
        // Implement your method to retrieve event data from the database or wherever it's stored
        // Return the Evenement object
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

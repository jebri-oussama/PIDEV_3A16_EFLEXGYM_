package gestion_suivi.controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuoteController {

    @FXML
    private Label quoteLabel;

    @FXML
    private Label authorLabel;

    @FXML
    void fetchNewQuote(ActionEvent event) {
        try {
            // Créer une URL pour l'API Quotables
            URL url = new URL("https://api.quotable.io/random");

            // Ouvrir une connexion HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Lire la réponse de l'API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Convertir la réponse en objet JSON
            JSONObject jsonObject = new JSONObject(response.toString());

            // Extraire la citation et l'auteur de la réponse JSON
            String quote = jsonObject.getString("content");
            String author = jsonObject.getString("author");

            // Mettre à jour les labels avec la citation et l'auteur
            quoteLabel.setText("Citation : " + quote);
            authorLabel.setText("Auteur : " + author);

            // Fermer la connexion
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            quoteLabel.setText("Erreur lors de la récupération de la citation.");
            authorLabel.setText("");
        }
    }
}
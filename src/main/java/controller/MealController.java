package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class MealController {

    @FXML
    private TextField searchTextField;

    @FXML
    private Label mealNameLabel;

    @FXML
    private Label ingredientsLabel;

    @FXML
    private Label instructionsLabel;

    private static final String API_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";

    private final HttpClient httpClient = HttpClients.createDefault();

    @FXML
    private void handleSearchButton() {
        String mealName = searchTextField.getText();
        searchMeal(mealName);
    }

    public void searchMeal(String mealName) {
        try {
            String apiUrl = API_URL + mealName;
            HttpGet request = new HttpGet(apiUrl);
            String responseBody = EntityUtils.toString(httpClient.execute(request).getEntity());

            JSONObject json = new JSONObject(responseBody);

            if (json.has("meals")) {
                JSONArray meals = json.getJSONArray("meals");
                if (meals.length() > 0) {
                    JSONObject meal = meals.getJSONObject(0);
                    mealNameLabel.setText(meal.getString("strMeal"));
                    ingredientsLabel.setText(meal.getString("strIngredient1") + ", " + meal.getString("strIngredient2"));
                    instructionsLabel.setText(meal.getString("strInstructions"));
                } else {
                    mealNameLabel.setText("Meal not found");
                    ingredientsLabel.setText("");
                    instructionsLabel.setText("");
                }
            } else {
                mealNameLabel.setText("Invalid response format");
                ingredientsLabel.setText("");
                instructionsLabel.setText("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package gestion_suivi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
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

    @FXML
    private ImageView mealImageView;

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

                    String imageUrl = meal.getString("strMealThumb");
                    Image image = new Image(imageUrl);
                    mealImageView.setImage(image);
                } else {
                    showAlert("Meal n'est pas ajouté", "il n ya pas le plat que vpus chercher.");
                    clearLabelsAndImageView();
                }
            } else {
                showAlert("Invalid Response Format", "The response from the server was in an invalid format.");
                clearLabelsAndImageView();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            showAlert("le plat n'existe pas", "le plat n'esxiste pas.");
            clearLabelsAndImageView();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearLabelsAndImageView() {
        mealNameLabel.setText("");
        ingredientsLabel.setText("");
        instructionsLabel.setText("");
        mealImageView.setImage(null);
    }
}

package gestion_evenement.Controllers;

import gestion_evenement.QRCodeGenerator;
import gestion_evenement.entities.Evenement;
import gestion_evenement.service.EvenementService;
import gestion_evenement.service.ParticipationService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ParticiperController implements Initializable {

    @FXML
    private FlowPane eventContainer;
    private int selectedEventId;
    @FXML
    private Pagination pagination;
    private EvenementService evenementService;
    private static final String ACCOUNT_SID = "AC8c0d46d6a42b027f40aafca7cb712bd2";
    private static final String AUTH_TOKEN = "adaf7a80490a5fe576d18a07a2443c51";
    private static final String TWILIO_NUMBER = "12768810802";
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
    private static final int ITEMS_PER_PAGE = 4;
    private int userId;

    public void initData(int userId) {
        this.userId = userId;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        evenementService = new EvenementService();

        List<Evenement> evenements = evenementService.readAll();

        int pageCount = (int) Math.ceil((double) evenements.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);

        pagination.setPageFactory(pageIndex -> {
            eventContainer.getChildren().clear();

            int startIndex = pageIndex * ITEMS_PER_PAGE;
            int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, evenements.size());
            List<Evenement> sublist = evenements.subList(startIndex, endIndex);

            for (Evenement evenement : sublist) {

                VBox eventBox = new VBox(5);
                eventBox.getStyleClass().add("event-box");

                ImageView imageView = new ImageView();
                imageView.setFitWidth(265);
                imageView.setFitHeight(265);
                imageView.getStyleClass().add("event-image");
                try {
                    String imagePath = evenement.getImagePath();
                    Image image = new Image(imagePath);
                    imageView.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Label eventNameLabel = new Label(evenement.getEvent_name());
                eventNameLabel.getStyleClass().add("event-name");
                Button participerButton = new Button("Participer");
                participerButton.setOnAction(event -> {
                    // Create a confirmation dialog
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Participation");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to participate in this event?");
                    ParticipationService participationService = new ParticipationService();
                    int selectedEventId = participationService.getNumberOfParticipants(evenement.getId()) + 1;
                    // Show the confirmation dialog and wait for user response
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            // User clicked OK, proceed with adding participation
                            participationService.addParticipation(evenement.getId(), userId);

                            // Generate QR code
                            String qrData = "You are the " + selectedEventId + " participant";
                            ImageView qrCodeImageView = QRCodeGenerator.generateQRCodeImage(qrData, 200, 200);

                            // Show QR code in a new stage
                            Stage qrCodeStage = new Stage();
                            qrCodeStage.initModality(Modality.APPLICATION_MODAL);
                            qrCodeStage.setTitle("QR Code");
                            VBox qrCodeBox = new VBox(10);
                            qrCodeBox.setPadding(new Insets(20));
                            qrCodeBox.getChildren().add(qrCodeImageView);
                            Scene qrCodeScene = new Scene(qrCodeBox, 300, 300);
                            qrCodeStage.setScene(qrCodeScene);
                            qrCodeStage.show();
                        }
                    });
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Enter Number");
                    dialog.setHeaderText(null);
                    dialog.setContentText("Please enter your number:");

                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(number -> {
                        // Send the message using the messaging API
                        sendMessage(number, selectedEventId);
                    });

                });

                Button detailsButton = new Button("Details");
                detailsButton.setOnAction(event -> {
                    showEventDetailsPopup(evenement);
                });

                HBox buttonBox = new HBox(5);
                buttonBox.getChildren().addAll(participerButton, detailsButton);

                eventBox.getChildren().addAll(imageView, eventNameLabel, buttonBox);
                eventContainer.getChildren().add(eventBox);

            }

            return new VBox(); // Return any Node, since the content is already added to eventContainer
        });
    }


    private JSONObject getWeatherForEventDate(JSONArray weatherData, LocalDate eventDate) {
        for (int i = 0; i < weatherData.length(); i++) {
            JSONObject weather = weatherData.getJSONObject(i);
            long timestamp = weather.getLong("dt");
            LocalDate date = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
            if (date.equals(eventDate)) {
                return weather;
            }
        }
        return null;
    }

    private void showEventDetailsPopup(Evenement evenement) {
        Stage detailsStage = new Stage();
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.setTitle("Event Details");

        VBox detailsBox = new VBox(10);
        detailsBox.setPadding(new Insets(20));

        Label eventNameLabel = new Label(evenement.getEvent_name());
        eventNameLabel.setStyle("-fx-font-size: 24px;");

        Label typeNameLabel = new Label("Type: " + evenement.getType().getTypeName());
        Label dateDebutLabel = new Label("Start Date: " + evenement.getDate_debut().toString());
        Label dateFinLabel = new Label("End Date: " + evenement.getDate_fin().toString());

        // Retrieve the number of participants for the event directly
        ParticipationService participationService = new ParticipationService();
        int numberOfParticipants = participationService.getNumberOfParticipants(evenement.getId());
        Label nbrDeParticipantLabel = new Label("Number of Participants: " + numberOfParticipants);

        detailsBox.getChildren().addAll(eventNameLabel, typeNameLabel, dateDebutLabel, dateFinLabel, nbrDeParticipantLabel);
        String city = evenement.getPlace(); // Replace with the event's city
        LocalDate eventDate = evenement.getDate_debut().toLocalDate();

        String apiKey = "b8bddb9d6a6442d8ffc85697230418a0"; // Replace with your OpenWeatherMap API key
        String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + apiKey;
        try {
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

            // Parse the JSON response to extract weather details
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray weatherData = jsonResponse.getJSONArray("list");
            JSONObject weather = getWeatherForEventDate(weatherData, eventDate);
            if (weather != null) {
                JSONObject main = weather.getJSONObject("main");

                double temperatureKelvin = main.getDouble("temp");
                double temperatureCelsius = temperatureKelvin - 273.15;
                String formattedTemperature = String.format("%.2f", temperatureCelsius);

                Label weatherLabel = new Label("Temperature on Event Day: " + formattedTemperature + "°C");

                detailsBox.getChildren().add(weatherLabel);
            } else {
                Label errorLabel = new Label("Weather data not found for the event date");
                detailsBox.getChildren().add(errorLabel);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error fetching weather data");
            detailsBox.getChildren().add(errorLabel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Scene detailsScene = new Scene(detailsBox, 300, 200);
        detailsStage.setScene(detailsScene);
        detailsStage.show();
    }

    private void sendMessage(String number, int selectedEventId) {
        System.out.println(selectedEventId);
        if (!isValidPhoneNumber(number)) {
            showAlert("Invalid Phone Number", "Please enter a valid phone number.");
            return;
        }

        Message message = Message.creator(
                new PhoneNumber(number),
                new PhoneNumber(TWILIO_NUMBER),
                "You are the " + selectedEventId + " participant"
        ).create();

        showAlert("Message Sent", "Message sent to " + number);
    }

    private boolean isValidPhoneNumber(String number) {
        // Check if the number is not null and matches the E.164 format
        return number != null && number.matches("^\\+[1-9]\\d{1,14}$");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

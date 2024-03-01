package gestion_evenement.Controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import gestion_evenement.QRCodeGenerator;
import gestion_evenement.entities.Evenement;
import gestion_evenement.service.EvenementService;
import gestion_evenement.service.ParticipationService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ParticiperController implements Initializable {

    @FXML
    private FlowPane eventContainer;

    private EvenementService evenementService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        evenementService = new EvenementService();

        List<Evenement> evenements = evenementService.readAll();

        for (Evenement evenement : evenements) {
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

                // Show the confirmation dialog and wait for user response
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // User clicked OK, proceed with adding participation
                        ParticipationService participationService = new ParticipationService();
                        participationService.addParticipation(evenement.getId());

                        // Generate QR code
                        String qrData = "You are the " + (participationService.getNumberOfParticipants(evenement.getId())) + " participant";
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
        ParticipationService participationService=new ParticipationService();
        int numberOfParticipants = participationService.getNumberOfParticipants(evenement.getId());
        Label nbrDeParticipantLabel = new Label("Number of Participants: " + numberOfParticipants);

        detailsBox.getChildren().addAll(eventNameLabel, typeNameLabel, dateDebutLabel, dateFinLabel, nbrDeParticipantLabel);

        Scene detailsScene = new Scene(detailsBox, 300, 200);
        detailsStage.setScene(detailsScene);
        detailsStage.show();
    }
}

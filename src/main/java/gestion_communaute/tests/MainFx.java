package gestion_communaute.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/AfficherEvenement.fxml")); // Changer le chemin du fichier FXML
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("AfficherEvenement"); // Changer le titre de la fenêtre
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
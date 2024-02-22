package Gestion_planing.Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class AjouterCourController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loder = new FXMLLoader(getClass().getResource("/AjouerCour"));
        try {
            Parent root = loder.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

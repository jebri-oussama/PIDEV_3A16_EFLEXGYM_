package Gestion_planing.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class MainFX extends Application {

  /*  public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/as=AfficherCour.fxml")); // Changer le chemin du fichier FXML
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("a affiche"); // Changer le titre de la fenêtre
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }*/
public void start(Stage primaryStage) throws Exception {
    // Charger le fichier FXML
    Parent root = FXMLLoader.load(getClass().getResource("/AfficherPlaningAdherent.fxml"));

    // Créer la scène
    Scene scene = new Scene(root);

    // Définir la scène sur la fenêtre principale
    primaryStage.setScene(scene);
    primaryStage.setTitle("Afficher Cour"); // Titre de la fenêtre

    // Afficher la fenêtre
    primaryStage.show();
}

    public static void main(String[] args) {
        launch(args);
    }
}


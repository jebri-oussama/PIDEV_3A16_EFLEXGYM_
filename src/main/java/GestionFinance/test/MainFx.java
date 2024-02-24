package GestionFinance.test;

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
    public void start(Stage primaryStage) throws IOException {
       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAbonnement.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajouter Abonnement");
        primaryStage.show();*/

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterBilanFinancier.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajouter Bilan Financier");
        primaryStage.show();*/
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateBilanFinancier.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mise à jour Bilan Financier");
        primaryStage.show();*/

     /*   FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Afficher Abonnements");
            primaryStage.show();*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherBilanFinancier.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bilan Financier");
        primaryStage.show();

    }
}

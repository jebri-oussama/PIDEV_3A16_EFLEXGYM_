package GestionFinance.test;

import GestionFinance.controller.ConsulterAbonnementController;
import GestionFinance.entites.Abonnement;
import GestionFinance.service.AbonnementService;
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

  /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Afficher Abonnements");
            primaryStage.show();*/
    /*    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherBilanFinancier.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bilan Financier");
        primaryStage.show();
       /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
        primaryStage.show();*/

      /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConsulterAbonnement.fxml"));
        Parent root = loader.load();

        // Récupérez l'abonnement de la base de données (simulé pour le test)
        AbonnementService abonnementService = new AbonnementService();
        Abonnement abonnement = abonnementService.readById(2); // Remplacez idAbonnement par l'ID de l'abonnement à tester

        // Récupérez le contrôleur de l'interface
        ConsulterAbonnementController controller = loader.getController();

        // Passez l'abonnement récupéré au contrôleur
        controller.setAbonnement(abonnement);

        // Affichez l'interface
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Consulter Abonnement");
        primaryStage.show();
    }*/
     FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAbonnements.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bilan Financier");
        primaryStage.show();

    }
   /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/Covid.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bilan Financier");
        primaryStage.show();*/


}

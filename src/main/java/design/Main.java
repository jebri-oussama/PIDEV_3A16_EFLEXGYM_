package design;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {


    /* Parent root = FXMLLoader.load(getClass().getResource("/Formulaire.fxml"));
     Scene scene = new Scene(root, 1100, 600);

     // Load the CSS file
   scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

     primaryStage.setTitle("Mes Mesures");
     Image icon = new Image(getClass().getResourceAsStream("/logo.png"));

     // Set the icon
     primaryStage.getIcons().add(icon);
     primaryStage.setScene(scene);
     primaryStage.show();*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Formulaire.fxml"));
        Scene scene = new Scene(root, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Affichage Suivi Prog");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

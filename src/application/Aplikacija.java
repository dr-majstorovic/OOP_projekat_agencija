package application;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Aplikacija extends Application{

    public static void main(String[] args) {
        Database.init();
        launch(args);
        System.out.println("kraj");
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("aplikacija.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("GoTravel");
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

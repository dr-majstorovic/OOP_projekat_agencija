package application;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Aplikacija extends Application{

    public static ArrayList<Scene> nazad = new ArrayList<>(), naprijed = new ArrayList<>();

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
            nazad.add(scene);
            stage.setScene(scene);
            stage.setTitle("GoTravel");
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

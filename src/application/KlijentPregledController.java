package application;

import classes.Klijent;
import classes.Rezervacija;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class KlijentPregledController {

    public Klijent klijent;
    Parent root;
    Scene scene;
    Stage stage;

    @FXML
    Label potrosenoLabel, doplatitiLabel;

    @FXML
    ListView<Rezervacija> listaRezervacija;

    @FXML
    Button dugmeAktivne, dugmeProtekle, dugmeOtkazane;

    public void setKlijent(Klijent k){
        klijent = k;
    }

    public void nazad(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/klijent.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        KlijentController klijentController = loader.getController();
        klijentController.setKorisnik(klijent);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

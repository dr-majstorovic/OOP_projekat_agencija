package application;

import classes.Klijent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class KlijentController {

    public static Klijent klijent;
    Parent root;
    Scene scene;
    Stage stage;

    @FXML
    Label ime, prezime, username;

    public void getKorisnik(Klijent k){
        klijent = k;
        ime.setText(k.getIme());
        prezime.setText(k.getPrezime());
        username.setText(k.getKorisnickoIme());
    }

    public void promjenaLozinke(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/promjena-lozinke.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void odjava(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/aplikacija.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void novaRezervacija(ActionEvent event){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/klijent-rezervacija.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        KlijentRezervacijaController krc = loader.getController();
        krc.proslijedi(klijent);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void pregledajRezervacije(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/klijent-pregled.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        KlijentRezervacijaController krc = loader.getController();
        krc.proslijedi(klijent);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

package application;

import classes.Klijent;
import classes.Korisnik;
import classes.Rezervacija;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class KlijentController implements Initializable {

    public static Klijent klijent;
    Parent root;
    Scene scene;
    Stage stage;

    @FXML
    Label ime, prezime, username, obavjestenje1, obavjestenje2;

    public void setKorisnik(Klijent k){
        klijent = k;
        ime.setText(klijent.getIme());
        prezime.setText(klijent.getPrezime());
        username.setText(klijent.getKorisnickoIme());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        obavjestenje1.setVisible(false);
        obavjestenje2.setVisible(false);

        for(Rezervacija r: Rezervacija.all){
            if(r.getKlijent().equals(klijent) && r.getOtkazana().equals("ne") && r.getAranzman().getDatumPolaska().isBefore(LocalDate.now().plusDays(17))){
                obavjestenje1.setVisible(true);
                break;
            }
        }
    }

    public void promjenaLozinke(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/promjena-lozinke.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PromjenaLozinkeController PLC = loader.getController();
        PLC.setKorisnik(klijent);

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
        krc.setKlijent(klijent);

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

        KlijentPregledController kpc = loader.getController();
        kpc.setKlijent(klijent);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

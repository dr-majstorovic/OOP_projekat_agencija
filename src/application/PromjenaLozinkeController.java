package application;

import classes.Klijent;
import classes.Korisnik;
import database.Write;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PromjenaLozinkeController {

    Parent root;
    Scene scene;
    Stage stage;

    public Korisnik korisnik;

    @FXML
    PasswordField trenutna, nova1, nova2;
    @FXML
    Label greskaLabel;

    public void setKorisnik(Korisnik k){
        korisnik = k;
    }

    public void potvrdi(ActionEvent event){
        String trentutnaS = trenutna.getText();
        String nova1S = nova1.getText();
        String nova2S = nova2.getText();
        greskaLabel.setText("");
        String greska = "";

        if(!trentutnaS.equals(korisnik.getLozinka())){
            greska += "Neispravna lozinka. ";
        }
        if(!nova1S.equals(nova2S)){
            greska += "Nove lozinke se ne podudaraju. ";
        }else if(nova1S.length() < 6){
            greska += "Nova lozinka mora imati bar 6 karaktera. ";
        }

        if(korisnik.getClass().getSimpleName().equals("Admin")){
            if(nova1S.equals("12345678"))
                greska += "Ne možete izabrati ovu lozinku.";
        }

        if(greska.isEmpty()){
            korisnik.setLozinka(nova1S);
            try {
                Write.updateLozinka(korisnik);
            } catch (SQLException e) {
                System.out.println("Greska u bazi pri promjeni lozinke.");
                e.printStackTrace();
                greskaLabel.setText("Greska u bazi podataka.");
            }
            AlertBox.display("Uspješno ste promijenili lozinku");
            String fxml = "../fxml/" + korisnik.getClass().getSimpleName().toLowerCase() + ".fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(korisnik.getClass().getSimpleName().equals("Klijent")){
                KlijentController klijentController = loader.getController();
                klijentController.setKorisnik(KlijentController.klijent);
            }else{
                KlijentController klijentController = loader.getController();
                klijentController.setKorisnik(KlijentController.klijent);
            }

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }else{
            greskaLabel.setText(greska);
        }

    }

    public void nazad(ActionEvent event){

        String fxml = "../fxml/" + korisnik.getClass().getSimpleName().toLowerCase() + ".fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(korisnik.getClass().getSimpleName().equals("Klijent")){
            KlijentController klijentController = loader.getController();
            klijentController.setKorisnik(KlijentController.klijent);
        }else{
            KlijentController klijentController = loader.getController();
            klijentController.setKorisnik(KlijentController.klijent);
        }

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

package application;

import classes.Korisnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AplikacijaController {

    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label greska;

    public void dugmePrijava(){

        for (classes.Korisnik k: Korisnik.all){
            if(k.getKorisnickoIme().equals(username.getText()) && k.getLozinka().equals(password.getText())){
                System.out.println("pronadjen");
                break;
            }
        }
        greska.setVisible(true);
    }

    public void dugmeRegistracija(){

    }
}

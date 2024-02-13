package application;

import classes.Korisnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AplikacijaController {

    Parent root;
    Scene scene;
    Stage stage;

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

    public void dugmeRegistracija(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("registracija.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

package application;

import classes.Admin;
import classes.Klijent;
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

    public void dugmePrijava(ActionEvent event){
        greska.setVisible(false);
        for (classes.Korisnik k: Korisnik.all){
            if(k.getKorisnickoIme().equals(username.getText()) && k.getLozinka().equals(password.getText())){

                FXMLLoader loader = null;

                if(k.getClass().getSimpleName().equals("Klijent")){
                    loader = new FXMLLoader(getClass().getResource("../fxml/klijent.fxml"));
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    KlijentController klijentController = loader.getController();
                    klijentController.setKorisnik((Klijent) k);
                }else{
                    if(k.getLozinka().equals("12345678")){
                        loader = new FXMLLoader(getClass().getResource("../fxml/promjena-lozinke.fxml"));
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        PromjenaLozinkeController PLC = loader.getController();
                        PLC.setKorisnik(k);
                    }else{
                        loader = new FXMLLoader(getClass().getResource("../fxml/admin.fxml"));
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        AdminController adminController = loader.getController();
                        adminController.setKorisnik((Admin) k);
                    }
                }


                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            }
        }
        greska.setVisible(true);
    }

    public void dugmeRegistracija(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/registracija.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}

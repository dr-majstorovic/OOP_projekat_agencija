package application;

import classes.Admin;
import classes.BankovniRacun;
import classes.Klijent;
import classes.Korisnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class NoviAdminController {

    public static Admin admin;

    Parent root;
    Scene scene;
    Stage stage;

    @FXML
    TextField ime, prezime, username;
    @FXML
    Label greska;

    public void setKorisnik(Admin a){
        admin = a;
    }

    public void otkazi(ActionEvent event){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/admin.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AdminController ac = loader.getController();
        ac.setKorisnik(admin);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void registracija(ActionEvent event){
        greska.setText("");
        String porukaGreske = "";
        if(ime.getText().isEmpty() ||
                prezime.getText().isEmpty() ||
                username.getText().isEmpty()){
            porukaGreske += "Sva polja moraju biti popunjena. ";
        }else{
            for(Korisnik k: Korisnik.all){
                if(k.getKorisnickoIme().equals(username.getText())){
                    porukaGreske += "Korisničko ime zauzeto. ";
                    break;
                }
            }
        }

        if(porukaGreske.isEmpty()){
            Admin a = new Admin(-1, ime.getText(), prezime.getText(), username.getText(), "12345678");
            try{
                database.Write.writeAdmin(a);
                AlertBox.display("Registracija uspješna.");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/admin.fxml"));
                root = loader.load();

                AdminController ac = loader.getController();
                ac.setKorisnik(admin);

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (SQLException e) {
                greska.setText("Greška sa bazom. Pokušajte ponovo.");
                System.out.println("Greska sa bazom.");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Greska pri registraciji.");
                e.printStackTrace();
            }
        }else{
            greska.setText(porukaGreske);
        }
    }
}

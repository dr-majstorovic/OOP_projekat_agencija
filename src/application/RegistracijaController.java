package application;

import classes.BankovniRacun;
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
import java.sql.SQLException;

public class RegistracijaController {

    Parent root;
    Scene scene;
    Stage stage;

    @FXML
    TextField ime, prezime, brTel, jmbg, brRacuna, username;
    @FXML
    PasswordField pass1, pass2;
    @FXML
    Label greska;

    public void registracija(ActionEvent event){
        greska.setText("");
        String porukaGreske = "";
        BankovniRacun racun = null;
        if(ime.getText().isEmpty() ||
                prezime.getText().isEmpty() ||
                brTel.getText().isEmpty() ||
                jmbg.getText().isEmpty() ||
                brRacuna.getText().isEmpty() ||
                username.getText().isEmpty()){
            porukaGreske += "Sva polja moraju biti popunjena. ";
        }else{
        if (!(brTel.getText().matches("[0-9]+")))
            porukaGreske += "Nevažeći broj telefona. ";
        for(BankovniRacun br: BankovniRacun.all){
            if (br.getJMBG().equals(jmbg.getText()) && br.getBrojRacuna().equals(brRacuna.getText())){
                racun = br;
                break;
            }
            if(br.getBrojRacuna().equals(brRacuna.getText())){
                porukaGreske += "Nevažeći JMBG. ";
                break;
            }
            if(br.getJMBG().equals(jmbg.getText())){
                porukaGreske += "Nevažeći broj računa. ";
                break;
            }
        }

        for(Korisnik k: Korisnik.all){
            if(k.getKorisnickoIme().equals(username.getText())){
                porukaGreske += "Korisničko ime zauzeto. ";
                break;
            }
        }

        if(!pass1.getText().equals(pass2.getText())){
            porukaGreske += "Lozinke se ne podudaraju. ";
        }

        if(pass1.getText().length() < 6)
            porukaGreske += "Lozinka mora imati bar 6 karaktera. ";

        }

        if(porukaGreske.isEmpty()){
            Klijent klijent = new Klijent(-1, ime.getText(), prezime.getText(), username.getText(), pass1.getText(), brTel.getText(), jmbg.getText(), brRacuna.getText());
            try{
                database.Write.writeKlijent(klijent);
                AlertBox.display("Uspješno ste se registrovali.", ":)");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/aplikacija.fxml"));
                root = loader.load();

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

    public void pocetak(ActionEvent event){

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

}

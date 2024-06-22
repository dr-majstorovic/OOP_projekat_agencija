package application;

import classes.Aranzman;
import classes.BankovniRacun;
import classes.Klijent;
import classes.Rezervacija;
import database.Write;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class KlijentPregledController implements Initializable {

    private Klijent klijent;
    Parent root;
    Scene scene;
    Stage stage;

    ArrayList<Rezervacija> rezervacije = new ArrayList<>();
    Rezervacija izabranaRezervacija = null;

    @FXML
    Label potrosenoLabel, doplatitiLabel, objasnjenje;

    @FXML
    ListView<Rezervacija> listaRezervacija;

    @FXML
    Button dugmeAktivne, dugmeProtekle, dugmeOtkazane;

    @FXML
    GridPane aranzmanInfo;

    @FXML
    Label labelNaziv, labelDestinacija, labelPrevoz, labelPolazak, labelDolazak, labelSmjestaj;

    @FXML
    AnchorPane rezervacijaAction;

    @FXML
    Label labelCijena, labelUplaceno, labelDoplatiti, greska;

    @FXML
    TextField fieldIznos;

    @FXML
    PasswordField lozinka;

    public void setKlijent(Klijent k){
        klijent = k;
        pokupiRezervacije(rezervacije);
        racunajPotrosenoDoplatiti();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        objasnjenje.setVisible(false);
        aranzmanInfo.setVisible(false);
        rezervacijaAction.setVisible(false);
        listaRezervacija.getSelectionModel().selectedItemProperty().addListener(((observableValue, rezervacija, t1) -> {
            izabranaRezervacija = listaRezervacija.getSelectionModel().getSelectedItem();
            if(izabranaRezervacija == null)
                return;

            aranzmanInfo.setVisible(true);
            greska.setVisible(false);

            labelNaziv.setText(izabranaRezervacija.getAranzman().getNaziv());
            labelDestinacija.setText(izabranaRezervacija.getAranzman().getDestinacija());
            labelPrevoz.setText(izabranaRezervacija.getAranzman().getPrevoz().toString());
            labelPolazak.setText(izabranaRezervacija.getAranzman().getDatumPolaska().toString());
            labelDolazak.setText(izabranaRezervacija.getAranzman().getDatumDolaska().toString());

            if(izabranaRezervacija.getAranzman().getSmjestaj() == null)
                labelSmjestaj.setText("N/A");
            else
                labelSmjestaj.setText(izabranaRezervacija.getAranzman().getSmjestaj().toString());

            if(izabranaRezervacija.getOtkazana().equals("da") || izabranaRezervacija.getAranzman().getDatumPolaska().isBefore(LocalDate.now()))
                return;
            rezervacijaAction.setVisible(true);

            labelCijena.setText(Double.toString(izabranaRezervacija.getUkupnaCijena()));
            labelUplaceno.setText(Double.toString(izabranaRezervacija.getPlaceno()));
            labelDoplatiti.setText(Double.toString(izabranaRezervacija.getUkupnaCijena() - izabranaRezervacija.getPlaceno()));
        }));
    }

    // metoda koja sakuplja u jednu listu sve rezervacije izabranog korisnika
    private void pokupiRezervacije(ArrayList<Rezervacija> lista){
        for(Rezervacija r: Rezervacija.all)
            if(r.getKlijent().getId() == klijent.getId()) {
                lista.add(r);
            }
    }

    private void racunajPotrosenoDoplatiti(){
        double potroseno = 0.0;
        double doplatiti = 0.0;

        if(rezervacije != null)
            for(Rezervacija r: rezervacije){
                if(r.getOtkazana().equals("da"))
                    continue;
                potroseno += r.getPlaceno();
                doplatiti += r.getUkupnaCijena();
            }
        doplatiti = doplatiti - potroseno;

        potrosenoLabel.setText("" + potroseno);
        doplatitiLabel.setText("" + doplatiti);
    }

    public void dugmeAktivne(ActionEvent event){
        objasnjenje.setVisible(true);
        aranzmanInfo.setVisible(false);
        ArrayList<Rezervacija> aktivneRezervacije = new ArrayList<>();

        for (Rezervacija r: rezervacije){
            if(r.getOtkazana().equals("ne") && r.getAranzman().getDatumPolaska().isAfter(LocalDate.now())){
                aktivneRezervacije.add(r);
            }
        }

        listaRezervacija.getItems().clear();
        listaRezervacija.getItems().addAll(aktivneRezervacije);
    }

    public void dugmeProtekle(ActionEvent event){
        objasnjenje.setVisible(false);
        aranzmanInfo.setVisible(false);
        rezervacijaAction.setVisible(false);
        aranzmanInfo.setVisible(false);

        ArrayList<Rezervacija> protekleRezervacije = new ArrayList<>();

        for (Rezervacija r: rezervacije){
            if(r.getOtkazana().equals("ne") && r.getAranzman().getDatumPolaska().isBefore(LocalDate.now())){
                protekleRezervacije.add(r);
            }
        }

        listaRezervacija.getItems().clear();
        listaRezervacija.getItems().addAll(protekleRezervacije);
    }

    public void dugmeOtkazane(ActionEvent event){
        objasnjenje.setVisible(false);
        aranzmanInfo.setVisible(false);
        rezervacijaAction.setVisible(false);
        aranzmanInfo.setVisible(false);

        ArrayList<Rezervacija> otkazaneRezervacije = new ArrayList<>();

        for (Rezervacija r: rezervacije){
            if(r.getOtkazana().equals("da")){
                otkazaneRezervacije.add(r);
            }
        }

        listaRezervacija.getItems().clear();
        listaRezervacija.getItems().addAll(otkazaneRezervacije);
    }

    public void uplati(ActionEvent event){
        double iznos;
        try{
            iznos = Double.parseDouble(fieldIznos.getText());
        }catch (NumberFormatException nfe){
            greska.setText("Unesite ispravnu brojevnu vrijednost");
            greska.setVisible(true);
            return;
        }

        String password = lozinka.getText();
        if(!password.equals(klijent.getLozinka())){
            greska.setText("Pogrešna lozinka");
            greska.setVisible(true);
            return;
        }

        if(iznos <= 0.0 || iznos > izabranaRezervacija.getUkupnaCijena()-izabranaRezervacija.getPlaceno()){
            greska.setText("Unesite važeći novčani iznos.");
            greska.setVisible(true);
            return;
        }

        BankovniRacun racun = BankovniRacun.getFromBrojRacuna(klijent.getBrojRacuna());
        BankovniRacun agencija = BankovniRacun.getFromBrojRacuna("1234567887654321");
        if(iznos > racun.getStanje()){
            greska.setText("Nemate dovoljno novca na računu");
            greska.setVisible(true);
            return;
        }

        racun.setStanje(racun.getStanje()-iznos);
        agencija.setStanje(agencija.getStanje()+iznos);

        izabranaRezervacija.setPlaceno(izabranaRezervacija.getPlaceno() + iznos);

        try{
            Write.updateBankovniRacun(racun);
            Write.updateBankovniRacun(agencija);
            Write.updatePlacenaCijena(izabranaRezervacija);
        }catch (SQLException sqle){
            greska.setText("Greška sa bazom.");
            greska.setVisible(true);
            sqle.printStackTrace();
            return;
        }

        AlertBox.display("Transakcija uspješna\nTrenutno stanje na vašem računu:\n" + racun.getStanje(), "OK");

        racunajPotrosenoDoplatiti();
        labelUplaceno.setText(Double.toString(izabranaRezervacija.getPlaceno()));
        labelDoplatiti.setText(Double.toString(izabranaRezervacija.getUkupnaCijena() - izabranaRezervacija.getPlaceno()));

        greska.setVisible(false);
    }

    public void otkazi(ActionEvent event){

        try{
            izabranaRezervacija.setOtkazana("da");

            BankovniRacun racun = BankovniRacun.getFromBrojRacuna(klijent.getBrojRacuna());
            BankovniRacun agencija = BankovniRacun.getFromBrojRacuna("1234567887654321");

            if(izabranaRezervacija.getAranzman().getDatumPolaska().isBefore(LocalDate.now().plusWeeks(2))){
                double razlika = izabranaRezervacija.getPlaceno() - izabranaRezervacija.getUkupnaCijena()/2;

                racun.setStanje(racun.getStanje() + razlika);
                agencija.setStanje(agencija.getStanje() - razlika);

                izabranaRezervacija.setPlaceno(izabranaRezervacija.getPlaceno() - razlika);
            }else{
                racun.setStanje(racun.getStanje() + izabranaRezervacija.getPlaceno());
                agencija.setStanje(agencija.getStanje() - izabranaRezervacija.getPlaceno());

                izabranaRezervacija.setPlaceno(0.0);
            }

            database.Write.updateOtkazana(izabranaRezervacija);
            database.Write.updateBankovniRacun(racun);
            database.Write.updateBankovniRacun(agencija);
            AlertBox.display("Rezervacija uspješno otkazana.\nTrenutno stanje na vašem računu:\n" + racun.getStanje(), ":)");
            listaRezervacija.getItems().clear();

        }catch (SQLException SQLe){
            AlertBox.display("Greška u bazi podataka.\nOtkazivanje neuspješno.", ":(");
            SQLe.printStackTrace();
        }
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

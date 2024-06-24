package application;

import classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPregledController implements Initializable {

    private Admin admin;
    Parent root;
    Scene scene;
    Stage stage;

    @FXML
    Label labelZarada,
            labelDug,
            greska,
            labelNaziv,
            labelDestinacija,
            labelPrevoz,
            labelPolazak,
            labelDolazak,
            labelCijena,
            labelSmjestaj,
            labelIme,
            labelUplatio,
            labelDuguje,
            labelBrTel;

    @FXML
    ChoiceBox<Aranzman> aranzmanBox;

    @FXML
    ChoiceBox<Klijent> klijentiBox;

    @FXML
    Button obrisiButton;

    @FXML
    Pane klijentiPane;

    Aranzman izabraniAranzman = null;
    Klijent izabraniKlijent = null;

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setZaradaDug();
        labelSmjestaj.setText("");
        greska.setVisible(false);
        obrisiButton.setVisible(false);
        klijentiPane.setVisible(false);
        aranzmanBox.getItems().addAll(Aranzman.all);
        aranzmanBox.getSelectionModel().selectedItemProperty().addListener(((observableValue, aranzman, t1) -> {
            izabraniAranzman = aranzmanBox.getSelectionModel().getSelectedItem();
            if(izabraniAranzman == null)
                return;
            obrisiButton.setVisible(true);
            labelNaziv.setText(izabraniAranzman.getNaziv());
            labelDestinacija.setText(izabraniAranzman.getDestinacija());
            labelPrevoz.setText(izabraniAranzman.getPrevoz().toString());
            labelPolazak.setText(izabraniAranzman.getDatumPolaska().toString());
            labelDolazak.setText(izabraniAranzman.getDatumDolaska().toString());
            labelCijena.setText(Double.toString(izabraniAranzman.getCijena()));
            if(izabraniAranzman.getSmjestaj() == null)
                labelSmjestaj.setText("N/A");
            else
                labelSmjestaj.setText(izabraniAranzman.getSmjestaj().toString());

            klijentiPane.setVisible(true);
            labelIme.setText("");
            labelUplatio.setText("");
            labelDuguje.setText("");
            labelBrTel.setText("");
            klijentiBox.getItems().clear();
            klijentiBox.getItems().addAll(getKlijenti());
            klijentiBox.getSelectionModel().selectedItemProperty().addListener(((observableValue1, klijent, t11) -> {
                izabraniKlijent = klijentiBox.getSelectionModel().getSelectedItem();
                if(izabraniKlijent == null)
                    return;
                labelIme.setText(izabraniKlijent.getIme() + " " + izabraniKlijent.getPrezime());
                Rezervacija rezervacija = null;

                for (Rezervacija r: Rezervacija.all)
                    if(r.getAranzman().equals(izabraniAranzman) && r.getKlijent().equals(izabraniKlijent)){
                        rezervacija = r;
                        break;
                    }
                assert rezervacija != null;
                labelUplatio.setText(Double.toString(rezervacija.getPlaceno()));
                labelDuguje.setText(Double.toString(rezervacija.getUkupnaCijena() - rezervacija.getPlaceno()));
                labelBrTel.setText(izabraniKlijent.getBrojTelefona());
            }));

        }));
    }

    private void setZaradaDug(){
        double zarada = 0.0, dug = 0.0;

        for (Rezervacija r: Rezervacija.all){
            zarada += r.getPlaceno();
            if(r.getOtkazana().equals("ne"))
                dug += r.getUkupnaCijena() - r.getPlaceno();
        }

        labelZarada.setText(String.format("%.2f", zarada));
        labelDug.setText(String.format("%.2f", dug));
    }

    private ArrayList<Klijent> getKlijenti(){
        ArrayList<Klijent> klijenti = new ArrayList<>();
        for (Rezervacija r: Rezervacija.all){
            if(r.getAranzman().equals(izabraniAranzman) && r.getOtkazana().equals("ne")){
                klijenti.add(r.getKlijent());
            }
        }
        return klijenti;
    }

    public void obrisiAranzman(ActionEvent event) {
        greska.setVisible(false);
        double izgubljeno = 0.0;
        List<Rezervacija> temp = List.copyOf(Rezervacija.all);
        for (Rezervacija r: temp){
            if (r.getAranzman().equals(izabraniAranzman)){
                izgubljeno += r.getPlaceno();
                Obavjestenje obavjestenje = new Obavjestenje(0, r.getKlijent(), r.getAranzman().toString(), r.getPlaceno());
                try {
                    BankovniRacun racun = BankovniRacun.getFromJMBG(r.getKlijent().getJMBG());
                    BankovniRacun agencija = BankovniRacun.getFromJMBG("1102541293");

                    racun.setStanje(racun.getStanje() + r.getPlaceno());
                    agencija.setStanje(agencija.getStanje() - r.getPlaceno());

                    database.Write.updateBankovniRacun(racun);
                    database.Write.updateBankovniRacun(agencija);
                    database.Write.writeObavjestenje(obavjestenje);
                    Rezervacija.all.remove(r);
                } catch (SQLException e) {
                    System.out.println("Greska u bazi");
                    e.printStackTrace();
                    greska.setText("Greska u bazi");
                    greska.setVisible(true);
                } catch (Exception ostalo) {
                    System.out.println("uh");
                    ostalo.printStackTrace();
                }
            }
        }
        AlertBox.display("Aranžman uspješno obrisan. Agencija je izgubila ukupno " + izgubljeno
                + " KM.", "OK");
        Aranzman.all.remove(izabraniAranzman);
        try {
            database.Write.deleteAranzman(izabraniAranzman);
        } catch (SQLException e) {
            System.out.println("Greska u bazi");
            e.printStackTrace();
            greska.setText("Greska u bazi");
            greska.setVisible(true);
        }
        aranzmanBox.getSelectionModel().clearSelection();
        aranzmanBox.getItems().clear();
        aranzmanBox.getItems().addAll(Aranzman.all);
        obrisiButton.setVisible(false);
        labelNaziv.setText("");
        labelDestinacija.setText("");
        labelPrevoz.setText("");
        labelPolazak.setText("");
        labelDolazak.setText("");
        labelCijena.setText("");
        labelSmjestaj.setText("");
    }

    public void nazad(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/admin.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AdminController adminController = loader.getController();
        adminController.setKorisnik(admin);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

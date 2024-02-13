package application;

import classes.*;
import database.Write;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import classes.Aranzman;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class KlijentRezervacijaController implements Initializable {

    @FXML
    ListView<Aranzman> listaAranzmana;
    @FXML
    Label labelNaziv, labelDestinacija, labelPrevoz, labelPolazak, labelDolazak, labelCijena, labelSmjestaj, greska;
    @FXML
    ChoiceBox<Integer> brZvjezdica;
    @FXML
    ChoiceBox<Soba> vrstaSobe;
    @FXML
    ComboBox<String> destinacija;
    @FXML
    TextField cijenaLow, cijenaHigh;
    @FXML
    ChoiceBox<Prevoz> prevoz;
    @FXML
    DatePicker dateLow, dateHigh;

    Parent root;
    Stage stage;
    Scene scene;

    public Klijent klijent;
    Aranzman izabraniAranzman;

    int ivp = 1; // izlet vs putovanje -> i = 1, p = 2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaAranzmana.getItems().addAll(Aranzman.all);
        listaAranzmana.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Aranzman>() {
            @Override
            public void changed(ObservableValue<? extends Aranzman> observableValue, Aranzman aranzman, Aranzman t1) {
                izabraniAranzman = listaAranzmana.getSelectionModel().getSelectedItem();
                greska.setText("");
                labelNaziv.setText(izabraniAranzman.getNaziv());
                labelDestinacija.setText(izabraniAranzman.getDestinacija());
                labelPrevoz.setText(izabraniAranzman.getPrevoz().toString());
                labelPolazak.setText(izabraniAranzman.getDatumPolaska().toString());
                labelDolazak.setText(izabraniAranzman.getDatumDolaska().toString());
                labelCijena.setText(""+izabraniAranzman.getCijena());
                if(izabraniAranzman.getSmjestaj().equals(null))
                    labelSmjestaj.setText(izabraniAranzman.getSmjestaj().toString());
                else
                    labelSmjestaj.setText("N/A");
            }
        });

        brZvjezdica.getItems().addAll(1, 2, 3, 4, 5);
        vrstaSobe.getItems().addAll(Soba.JEDNOKREVETNA, Soba.DVOKREVETNA, Soba.TROKREVETNA, Soba.APARTMAN);
        Set<String> destinacijeL = new HashSet<>();
        for(Aranzman a: Aranzman.all){
            destinacijeL.add(a.getDestinacija());
        }
        destinacija.getItems().addAll(destinacijeL);
        prevoz.getItems().addAll(Prevoz.AVION, Prevoz.AUTOBUS, Prevoz.SAMOSTALAN);

    }

    public void proslijedi(Klijent k){
        klijent = k;
    }

    public void izborIzlet(ActionEvent event){
        ivp = 1;
        listaAranzmana.getItems().clear();
        listaAranzmana.getItems().addAll(Aranzman.izleti);
    }

    public void izborPutovanje(ActionEvent event){
        ivp = 2;
        listaAranzmana.getItems().clear();
        listaAranzmana.getItems().addAll(Aranzman.putovanja);
    }

    public void sortPolazak(ActionEvent event){
        KomparatorDatumPolaska komparator = new KomparatorDatumPolaska();
        listaAranzmana.getItems().clear();
        if (ivp == 1){
            Aranzman.izleti.sort(komparator);
            listaAranzmana.getItems().addAll(Aranzman.izleti);
        }else{
            Aranzman.putovanja.sort(komparator);
            listaAranzmana.getItems().addAll(Aranzman.izleti);
        }
    }

    public void sortCijena(ActionEvent event){
        KomparatorCijena komparator = new KomparatorCijena();
        listaAranzmana.getItems().clear();
        if (ivp == 1){
            Aranzman.izleti.sort(komparator);
            listaAranzmana.getItems().addAll(Aranzman.izleti);
        }else{
            Aranzman.putovanja.sort(komparator);
            listaAranzmana.getItems().addAll(Aranzman.izleti);
        }
    }

    public void rezervisi(ActionEvent event){
        if(BankovniRacun.getFromJMBG(klijent.getJMBG()).getStanje() < izabraniAranzman.getCijena()/2){
            greska.setText("Nemate dovljno novca na računu.");
        }else{
            Rezervacija rezervacija = new Rezervacija(klijent, izabraniAranzman, izabraniAranzman.getCijena()/2);
            BankovniRacun agencija = BankovniRacun.getFromJMBG("1102541293");
            agencija.setStanje(agencija.getStanje() + izabraniAranzman.getCijena()/2);
            try {
                Write.writeRezervacija(rezervacija);
            } catch (SQLException e) {
                greska.setText("Greska sa bazom.");
                return;
            }
            AlertBox.display("Rezervacija uspješna.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("klijent.fxml"));
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

    public void nazad(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("klijent.fxml"));
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

    public void filtriraj(ActionEvent event){
        ArrayList<Aranzman> spisak;
        ArrayList<Aranzman> filtrirano = new ArrayList<>();
        if(ivp == 1)
            spisak = Aranzman.izleti;
        else
            spisak = Aranzman.putovanja;

        boolean brz = false, des = false, cl, ch, vs = false, p = false, dl = false, dh = false;

        Integer brZ = brZvjezdica.getValue();
        if(brZ != null)
            brz = true;
        String dest = "" + destinacija.getSelectionModel().getSelectedItem();
        if(!dest.isEmpty())
            des = true;
        Double cL = null;
        try{
            cL = Double.parseDouble(cijenaLow.getText());
            cl = true;
        }catch (NumberFormatException nfe){
            cl = false;
        }
        Double cH = null;
        try{
            cH = Double.parseDouble(cijenaHigh.getText());
            ch = true;
        }catch (NumberFormatException nfe){
            ch = false;
        }

        if(cL != null && cH != null && cL > cH){
            Double temp = cL;
            cL = cH;
            cH = temp;
        }

        Soba vrSobe = vrstaSobe.getValue();
        if(vrSobe != null)
            vs = true;

        Prevoz prev = prevoz.getValue();
        if(prev != null)
            p = true;

        LocalDate dL = dateLow.getValue(), dH = dateHigh.getValue(), td;
        if(dL != null)
            dl = true;
        if(dH != null)
            dh = true;

        for(Aranzman a:spisak){
            if(brz){
                if(a.getSmjestaj().getBrojZvjezdica() != brZ)
                    continue;
            }
            if(des){
                if(!a.getDestinacija().equals(dest))
                    continue;
            }
            if (cl){
                if(a.getCijena() < cL)
                    continue;
            }
            if(ch){
                if(a.getCijena() > cH)
                    continue;
            }
            if(vs){
                if(!a.getSmjestaj().getVrstaSobe().equals(vrSobe))
                    continue;
            }
            if(p){
                if(!a.getPrevoz().equals(prev))
                    continue;
            }
            if(dl){
                if(a.getDatumPolaska().isBefore(dL))
                    continue;
            }
            if(dh){
                if(a.getDatumPolaska().isAfter(dH))
                    continue;
            }
            filtrirano.add(a);
        }
        listaAranzmana.getItems().clear();
        listaAranzmana.getItems().addAll(filtrirano);
    }

}

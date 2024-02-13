package application;

import classes.Aranzman;
import classes.KomparatorCijena;
import classes.KomparatorDatumPolaska;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import classes.Aranzman;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KlijentRezervacijaController implements Initializable {

    @FXML
    ListView<Aranzman> listaAranzmana;
    @FXML
    Label labelNaziv, labelDestinacija, labelPrevoz, labelPolazak, labelDolazak, labelCijena, labelSmjestaj;

    Aranzman izabraniAranzman;

    int ivp = 1; // izlet vs putovanje -> i = 1, p = 2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaAranzmana.getItems().addAll(Aranzman.all);
        listaAranzmana.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Aranzman>() {
            @Override
            public void changed(ObservableValue<? extends Aranzman> observableValue, Aranzman aranzman, Aranzman t1) {
                izabraniAranzman = listaAranzmana.getSelectionModel().getSelectedItem();

                labelNaziv.setText(izabraniAranzman.getNaziv());
                labelDestinacija.setText(izabraniAranzman.getDestinacija());
                labelPrevoz.setText(izabraniAranzman.getPrevoz().toString());
                labelPolazak.setText(izabraniAranzman.getDatumPolaska().toString());
                labelDolazak.setText(izabraniAranzman.getDatumDolaska().toString());
                labelCijena.setText(""+izabraniAranzman.getCijena());
                labelSmjestaj.setText(izabraniAranzman.getSmjestaj().toString());

            }
        });
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
}

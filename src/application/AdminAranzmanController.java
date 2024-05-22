package application;

import classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminAranzmanController implements Initializable {

    public Admin admin;
    Parent root;
    Scene scene;
    Stage stage;

    @FXML
    Label greska, labelDP, labelCPN, labelBZS, labelTP, labelVS, labelNS;

    @FXML
    TextField naziv, destinacija, cijena, cijenaPN, nazivSmjestaja;

    @FXML
    DatePicker datumPolaska, datumPovratka;

    @FXML
    ChoiceBox<String> vrstaBox;

    @FXML
    ChoiceBox<Prevoz> prevozBox;

    @FXML
    ChoiceBox<Integer> zvjezdicaBox;

    @FXML
    ChoiceBox<Soba> sobaBox;

    @FXML
    Button gotovo;

    public void setAdmin(Admin a){
        admin = a;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        vrstaBox.getItems().addAll("izlet", "putovanje");
        vrstaBox.getSelectionModel().select("putovanje");
        vrstaBox.getSelectionModel().selectedItemProperty().addListener(((observableValue, s, t1) -> {
            if(vrstaBox.getSelectionModel().getSelectedItem().equals("putovanje")){
                datumPovratka.setVisible(true);
                cijenaPN.setVisible(true);
                prevozBox.setVisible(true);
                nazivSmjestaja.setVisible(true);
                zvjezdicaBox.setVisible(true);
                sobaBox.setVisible(true);
                labelDP.setVisible(true);
                labelCPN.setVisible(true);
                labelTP.setVisible(true);
                labelVS.setVisible(true);
                labelBZS.setVisible(true);
                labelNS.setVisible(true);
            }else {
                datumPovratka.setVisible(false);
                cijenaPN.setVisible(false);
                prevozBox.setVisible(false);
                nazivSmjestaja.setVisible(false);
                zvjezdicaBox.setVisible(false);
                sobaBox.setVisible(false);
                labelDP.setVisible(false);
                labelCPN.setVisible(false);
                labelTP.setVisible(false);
                labelVS.setVisible(false);
                labelBZS.setVisible(false);
                labelNS.setVisible(false);

            }
        }));

        prevozBox.getItems().addAll(Prevoz.AUTOBUS, Prevoz.AVION, Prevoz.SAMOSTALAN);
        zvjezdicaBox.getItems().addAll(1, 2, 3, 4, 5);
        sobaBox.getItems().addAll(Soba.APARTMAN, Soba.JEDNOKREVETNA, Soba.DVOKREVETNA, Soba.TROKREVETNA);
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

    public void dodajAranzman(ActionEvent event) {
        greska.setVisible(false);
        String vrst = vrstaBox.getValue();
        if(vrst.equals("putovanje")){
            try{
                String nazi = naziv.getText();
                String dest = destinacija.getText();
                LocalDate dpolaska = datumPolaska.getValue(), dpovratka = datumPovratka.getValue();
                Double cije = Double.parseDouble(cijena.getText()), cijepn = Double.parseDouble(cijenaPN.getText());
                Prevoz tipp = prevozBox.getValue();
                String nazsmj = nazivSmjestaja.getText();
                Integer brzvj = zvjezdicaBox.getValue();
                Soba vrsobe = sobaBox.getValue();

                Smjestaj smjestaj = new Smjestaj(-1, nazsmj, brzvj, vrsobe,cijepn);

                database.Write.writeSmjestaj(smjestaj);

                Aranzman aranzman = new Aranzman(-1, nazi, dest, tipp, dpolaska, dpovratka, cije, smjestaj);

                database.Write.writeAranzman(aranzman);
                AlertBox.display("Uspješno ste dodali aranžman \"" + aranzman + "\"");
            }catch (Exception e){
                greska.setText("Neuspješno. Provjerite podatke i pokušajte ponovo.");
                greska.setVisible(true);
                e.printStackTrace();
            }

        }else{
            try{
                String nazi = naziv.getText();
                String dest = destinacija.getText();
                LocalDate dpolaska = datumPolaska.getValue();
                Double cije = Double.parseDouble(cijena.getText());
                Prevoz tipp = Prevoz.SAMOSTALAN;

                Aranzman aranzman = new Aranzman(-1, nazi, dest, tipp, dpolaska, dpolaska, cije, null);
                database.Write.writeAranzman(aranzman);

                AlertBox.display("Uspješno ste dodali aranžman \"" + aranzman + "\"");
            }catch (Exception e){
                greska.setText("Neuspješno. Provjerite podatke i pokušajte ponovo.");
                greska.setVisible(true);
                e.printStackTrace();
            }
        }

    }
}

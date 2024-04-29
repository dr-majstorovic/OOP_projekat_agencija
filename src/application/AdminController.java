package application;

import classes.Admin;

import classes.Korisnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public Admin admin;
    Parent root;
    Scene scene;
    Stage stage;

    @FXML
    Label ime, prezime, username, brAdmina;

    public void setKorisnik(Admin a){
        admin = a;
        ime.setText(admin.getIme());
        prezime.setText(admin.getPrezime());
        username.setText(admin.getKorisnickoIme());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        brAdmina.setText(Integer.toString(brojAdmina()));
    }

    private int brojAdmina(){
        int n = 0;
        for (Korisnik k: Korisnik.all){
            if(k.getClass().getSimpleName().equals("Admin"))
                n++;
        }
        return n;
    }

    public void pregledajRezervacije(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/admin-pregled.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AdminPregledController apc = loader.getController();
        apc.setAdmin(admin);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void noviAranzman(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/admin-aranzman.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AdminAranzmanController aac = loader.getController();
        aac.setAdmin(admin);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void noviAdmin(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/novi-admin.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        NoviAdminController nac = loader.getController();
        nac.setKorisnik(admin);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void promjenaLozinke(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/promjena-lozinke.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PromjenaLozinkeController PLC = loader.getController();
        PLC.setKorisnik(admin);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void odjava(ActionEvent event){
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

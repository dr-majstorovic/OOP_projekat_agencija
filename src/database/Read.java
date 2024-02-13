package database;
import classes.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Read {

    public static Connection connection;

    public static void read() throws SQLException{
        readBankovniRacun();
        readAdmin();
        readKlijent();
        readSmjestaj();
        readAranzman();
        readRezervacija();
    }

    public static void readAdmin() throws SQLException {

        String upit = "SELECT * FROM admin;";
        Statement iskaz = connection.createStatement();
        ResultSet rezultat = iskaz.executeQuery(upit);

        while(rezultat.next()){
            int id = rezultat.getInt("id");
            String ime = rezultat.getString("ime");
            String prezime = rezultat.getString("prezime");
            String korime = rezultat.getString("korisnicko_ime");
            String lozinka = rezultat.getString("lozinka");

            new Admin(id, ime, prezime, korime, lozinka);
        }
        iskaz.close();

    }

    public static void readKlijent() throws SQLException{
        String upit = "SELECT * FROM klijent;";
        Statement iskaz = connection.createStatement();
        ResultSet rezultat = iskaz.executeQuery(upit);

        while(rezultat.next()){
            int id = rezultat.getInt("id");
            String ime = rezultat.getString("ime");
            String prezime = rezultat.getString("prezime");
            String korime = rezultat.getString("korisnicko_ime");
            String lozinka = rezultat.getString("lozinka");
            String jmbg = rezultat.getString("jmbg");
            String brRacuna = rezultat.getString("broj_racuna");
            String brTel = rezultat.getString("broj_telefona");

            new Klijent(id, ime, prezime,korime, lozinka, brTel, jmbg, brRacuna);
        }

    }

    public static void readBankovniRacun() throws SQLException {
        String upit = "SELECT * FROM bankovni_racun;";
        Statement iskaz = connection.createStatement();
        ResultSet rezultat = iskaz.executeQuery(upit);

        while(rezultat.next()){
            String brRacuna = rezultat.getString("broj_racuna");
            String jmbg = rezultat.getString("jmbg");
            Double stanje = rezultat.getDouble("stanje");

            new BankovniRacun(brRacuna, jmbg, stanje);
        }
    }

    public static void readSmjestaj() throws SQLException {
        String upit = "SELECT * FROM smjestaj;";
        Statement iskaz = connection.createStatement();
        ResultSet rezultat = iskaz.executeQuery(upit);

        while(rezultat.next()){
            int id = rezultat.getInt("id");
            String naziv = rezultat.getString("naziv");
            int zvjezdica = rezultat.getInt("broj_zvjezdica");
            classes.Soba vrsta = Soba.odOznake(rezultat.getString("vrsta_sobe"));
            double cijenapn = rezultat.getDouble("cijena_po_nocenju");

            new Smjestaj(id, naziv, zvjezdica, vrsta, cijenapn);
        }
    }

    public static void readAranzman() throws SQLException{
        String upit = "SELECT * FROM aranzman;";
        Statement iskaz = connection.createStatement();
        ResultSet rezultat = iskaz.executeQuery(upit);

        while(rezultat.next()){
            int id = rezultat.getInt("id");
            String naziv = rezultat.getString("naziv_putovanja");
            String destinacija = rezultat.getString("destinacija");
            Prevoz prevoz = Prevoz.odOznake(rezultat.getString("prevoz"));
            LocalDate dPolaska = rezultat.getDate("datum_polaska").toLocalDate();
            LocalDate dDolaska = rezultat.getDate("datum_dolaska").toLocalDate();
            double cijena = rezultat.getDouble("cijena_aranzmana");
            Smjestaj smjestaj = null;
            if(!dPolaska.isEqual(dDolaska))
                smjestaj = Smjestaj.getFromID(rezultat.getInt("Smjestaj_id"));

            new Aranzman(id, naziv, destinacija, prevoz, dPolaska, dDolaska, cijena, smjestaj);
        }
    }

    public static void readRezervacija() throws SQLException {
        String upit = "SELECT * FROM aranzman;";
        Statement iskaz = connection.createStatement();
        ResultSet rezultat = iskaz.executeQuery(upit);

        while(rezultat.next()){
            Klijent klijent = (Klijent)Korisnik.getFromID(rezultat.getInt("Klijent_id"), "Klijent");
            Aranzman aranzman = Aranzman.getFromID(rezultat.getInt("Aranzman_id"));
            double uCijena = rezultat.getDouble("ukupna_cijena");
            double pCijena = rezultat.getDouble("placena_cijena");

            new Rezervacija(klijent, aranzman, uCijena, pCijena);
        }
    }


}

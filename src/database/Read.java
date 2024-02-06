package database;
import classes.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Read {

    public static void read(Connection connection) throws SQLException{
        readAdmin(connection);
        readKlijent(connection);
        readBankovniRacun(connection);
        readSmjestaj(connection);
        readAranzman(connection);
        readRezervacija(connection);
    }

    public static void readAdmin(Connection connection) throws SQLException {

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

    public static void readKlijent(Connection connection) throws SQLException{
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

    public static void readBankovniRacun(Connection connection) throws SQLException {
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

    public static void readSmjestaj(Connection connection) throws SQLException {
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

    public static void readAranzman(Connection connection){

    }

    public static void readRezervacija(Connection connection){

    }


}

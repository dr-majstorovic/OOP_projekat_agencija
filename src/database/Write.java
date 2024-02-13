package database;

import classes.Admin;
import classes.Aranzman;
import classes.Klijent;
import classes.Korisnik;

import java.sql.*;

public class Write {

    public static Connection connection;

    public static void writeKlijent(Klijent klijent) throws SQLException{
        String upit = "INSERT INTO klijent (ime, prezime, broj_telefona, jmbg, broj_racuna, korisnicko_ime, lozinka)" +
                "VALUES ('" + klijent.getIme() + "', '" + klijent.getPrezime() + "', '" + klijent.getBrojTelefona() +
                "', '" + klijent.getJMBG() + "', '" + klijent.getBrojRacuna() + "', '" + klijent.getKorisnickoIme() +
                "', '" + klijent.getLozinka() + ");";
        Statement iskaz = connection.createStatement();
        iskaz.execute(upit);
        upit = "SELECT MAX(id) FROM klijent;";
        ResultSet rezultat = iskaz.executeQuery(upit);
        int id = 0;
        if(rezultat.next())
            id = rezultat.getInt(1);
        klijent.setId(id);
    }

    public static void writeAdmin(Admin admin) throws SQLException{
        String upit = "INSERT INTO admin (ime, prezime, korisnicko_ime, lozinka) VALUES ('" + admin.getIme() +
                "', '" + admin.getPrezime() + "', '" + admin.getKorisnickoIme() + "', '" + admin.getLozinka() + "');";
        Statement iskaz = connection.createStatement();
        iskaz.execute(upit);
        upit = "SELECT MAX(id) FROM admin;";
        ResultSet rezultat = iskaz.executeQuery(upit);
        int id = 0;
        if(rezultat.next())
            id = rezultat.getInt(1);
        admin.setId(id);
    }

    public static void writeAranzman(Aranzman aranzman) throws SQLException{
        String upit = "INSERT INTO aranzman (naziv_putovanja, destinacija, prevoz, datum_polaska, datum_dolaska, cijena_aranzmana, smjestaj_id) " +
                "VALUES ('" + aranzman.getNaziv() + "', '" + aranzman.getDestinacija() + "', '" + aranzman.getPrevoz().name() +
                "', " + Date.valueOf(aranzman.getDatumPolaska()) + ", " + Date.valueOf(aranzman.getDatumDolaska()) +
                ", " + aranzman.getCijena() + ", " + aranzman.getSmjestaj().getId() + ");";
        Statement iskaz = connection.createStatement();
        iskaz.execute(upit);
        int id = 0;
        upit = "SELECT MAX(id) FROM aranzman;";
        ResultSet rezultat = iskaz.executeQuery(upit);
        if(rezultat.next())
            id = rezultat.getInt(1);
        aranzman.setId(id);
    }
}

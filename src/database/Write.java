package database;

import classes.Admin;
import classes.Klijent;
import classes.Korisnik;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Write {

    public static void writeKlijent(Klijent klijent, Connection connection) throws SQLException{
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

    public static void writeAdmin(Admin admin, Connection connection) throws SQLException{
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
}

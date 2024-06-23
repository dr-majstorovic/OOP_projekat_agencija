package database;

import classes.*;

import java.sql.*;

public class Write {

    public static Connection connection;

    public static void writeKlijent(Klijent klijent) throws SQLException{
        String upit = "INSERT INTO klijent (ime, prezime, broj_telefona, jmbg, broj_racuna, korisnicko_ime, lozinka)" +
                "VALUES ('" + klijent.getIme() + "', '" + klijent.getPrezime() + "', '" + klijent.getBrojTelefona() +
                "', '" + klijent.getJMBG() + "', '" + klijent.getBrojRacuna() + "', '" + klijent.getKorisnickoIme() +
                "', '" + klijent.getLozinka() + "');";
        Statement iskaz = connection.createStatement();
        iskaz.executeUpdate(upit);
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
        iskaz.executeUpdate(upit);
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
                "', '" + Date.valueOf(aranzman.getDatumPolaska()) + "', '" + Date.valueOf(aranzman.getDatumDolaska()) +
                "', " + aranzman.getCijena() + ", ";
        if(aranzman.getSmjestaj() == null){
            upit += "NULL);";
        }else {
            upit += aranzman.getSmjestaj().getId() + ");";
        }
        Statement iskaz = connection.createStatement();
        iskaz.executeUpdate(upit);
        int id = 0;
        upit = "SELECT MAX(id) FROM aranzman;";
        ResultSet rezultat = iskaz.executeQuery(upit);
        if(rezultat.next())
            id = rezultat.getInt(1);
        aranzman.setId(id);
    }

    public static void writeSmjestaj(Smjestaj smjestaj) throws SQLException{
        String upit = "INSERT INTO smjestaj (naziv, broj_zvjezdica, vrsta_sobe, cjena_po_nocenju) VALUES ('" +
                smjestaj.getNaziv() + "', " + smjestaj.getBrojZvjezdica() + ", '" + smjestaj.getVrstaSobe() + "', " + smjestaj.getCijenaPN() + ");";
        Statement iskaz = connection.createStatement();
        iskaz.executeUpdate(upit);
        int id = 0;
        upit = "SELECT MAX(id) FROM smjestaj;";
        ResultSet rezultat = iskaz.executeQuery(upit);
        if(rezultat.next())
            id = rezultat.getInt(1);
        smjestaj.setId(id);
    }

    public static void updateLozinka(Korisnik korisnik) throws SQLException{
        String tabela = (korisnik.getClass().getSimpleName().equals("Klijent")) ? "klijent" : "admin";

        String upit = "UPDATE " + tabela + " SET lozinka = '" + korisnik.getLozinka() + "' WHERE id = " + korisnik.getId();
        Statement iskaz = connection.createStatement();
        iskaz.executeUpdate(upit);
    }

    public static void writeRezervacija(Rezervacija rezervacija) throws SQLException{
        String upit = "INSERT INTO rezervacija VALUES (" + rezervacija.getKlijent().getId() + ", " + rezervacija.getAranzman().getId() +
                ", " + rezervacija.getUkupnaCijena() + ", " + rezervacija.getPlaceno() + ", '" + rezervacija.getOtkazana() + "');";
        Statement iskaz = connection.createStatement();
        iskaz.executeUpdate(upit);
    }

    public static void updateOtkazana(Rezervacija rezervacija) throws SQLException{
        String upit = "UPDATE rezervacija SET otkazana = '" + rezervacija.getOtkazana() + "' WHERE Klijent_id = " +
                rezervacija.getKlijent().getId() + " AND Aranzman_id = " + rezervacija.getAranzman().getId() + ";";

        Statement iskaz = connection.createStatement();
        iskaz.executeUpdate(upit);

        upit = "UPDATE rezervacija SET placena_cijena = " + rezervacija.getPlaceno() + " WHERE Klijent_id = " +
                rezervacija.getKlijent().getId() + " AND Aranzman_id = " + rezervacija.getAranzman().getId() + ";";

        iskaz.executeUpdate(upit);
    }

    public static void deleteAranzman(Aranzman aranzman) throws SQLException{
        Statement iskaz = connection.createStatement();
        iskaz.executeUpdate("DELETE FROM rezervacija WHERE Aranzman_id = " + aranzman.getId() + ";");
        iskaz.executeUpdate("DELETE FROM aranzman WHERE id = " + aranzman.getId() + ";");
    }

    public static void updatePlacenaCijena(Rezervacija rezervacija) throws SQLException{
        String upit = "UPDATE rezervacija SET placena_cijena = " + rezervacija.getPlaceno() + " WHERE Klijent_id = "
                + rezervacija.getKlijent().getId() + " AND Aranzman_id = " + rezervacija.getAranzman().getId() + ";";
        Statement iskaz = connection.createStatement();
        iskaz.executeUpdate(upit);
    }

    public static void updateBankovniRacun(BankovniRacun bankovniRacun) throws SQLException{
        String upit = "UPDATE bankovni_racun SET stanje = " + bankovniRacun.getStanje() + " WHERE broj_racuna = '"
                + bankovniRacun.getBrojRacuna() + "';";

        Statement iskaz = connection.createStatement();
        iskaz.executeUpdate(upit);
    }

    public static void writeObavjestenje(Obavjestenje obavjestenje) throws SQLException{
        String upit = "INSERT INTO obavjestenje (klijent_id, aranzman_id, iznos) VALUES ("
                + obavjestenje.getKlijent().getId() + ", " + obavjestenje.getAranzman().getId() + ", "
                + obavjestenje.getIznos() + ");";

        Statement iskaz = connection.createStatement();
        iskaz.executeUpdate(upit);

        upit = "SELECT MAX(id) FROM obavjestenje;";
        ResultSet rezultat = iskaz.executeQuery(upit);
        int id = 0;
        if(rezultat.next())
            id = rezultat.getInt(1);
        obavjestenje.setId(id);
    }

    public static void deleteObavjestenje(Obavjestenje obavjestenje) throws  SQLException{
        String upit = "DELETE FROM obavjestenje WHERE id = " + obavjestenje.getId() + ";";

        Statement iskaz = connection.createStatement();
        iskaz.executeUpdate(upit);
    }
}

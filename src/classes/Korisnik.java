package classes;

import java.util.ArrayList;

public class Korisnik {

    protected String ime, prezime, korisnickoIme, lozinka;
    public static ArrayList<Korisnik> all;

    public Korisnik(String ime, String prezime, String korisnickoIme, String lozinka){
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }
}

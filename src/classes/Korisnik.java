package classes;

import java.util.ArrayList;

public class Korisnik {

    protected int id;
    protected String ime, prezime, korisnickoIme, lozinka;
    public static ArrayList<Korisnik> all;

    public Korisnik(int id, String ime, String prezime, String korisnickoIme, String lozinka) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public int getId() {
        return id;
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

    public void setId(int id) { this.id = id; }

    public static Korisnik getFromID(int id, String klasa) {
        for(Korisnik x: all){
            if(x.getId() == id && klasa.equals(x.getClass().getSimpleName()))
                return x;
        }
        return null;
    }
}

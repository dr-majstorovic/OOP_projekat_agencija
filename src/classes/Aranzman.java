package classes;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class Aranzman {

    private int id;
    private String naziv;
    private String destinacija;
    private Prevoz prevoz;
    private LocalDate datumPolaska, datumDolaska;
    private double cijena;
    private Smjestaj smjestaj;
    public static ArrayList<Aranzman> all;

    public Aranzman(int id, String naziv, String destinacija, Prevoz prevoz, LocalDate datumPolaska, LocalDate datumDolaska, double cijena, Smjestaj smjestaj) {
        this.id = id;
        this.naziv = naziv;
        this.destinacija = destinacija;
        this.prevoz = prevoz;
        this.datumPolaska = datumPolaska;
        this.datumDolaska = datumDolaska;
        this.cijena = cijena;
        this.smjestaj = smjestaj;
        all.add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getNaziv() {
        return naziv;
    }

    public String getDestinacija() {
        return destinacija;
    }

    public Prevoz getPrevoz() {
        return prevoz;
    }

    public LocalDate getDatumPolaska() {
        return datumPolaska;
    }

    public LocalDate getDatumDolaska() {
        return datumDolaska;
    }

    public double getCijena() {
        return cijena;
    }

    public Smjestaj getSmjestaj() {
        return smjestaj;
    }

    public int getTrajanje() {
        return Period.between(datumPolaska, datumDolaska).getDays();
    }

    public static Aranzman getFromID(int id) {
        for(Aranzman x: all){
            if(x.getId() == id)
                return x;
        }
        return null;
    }
}

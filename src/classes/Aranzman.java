package classes;

import java.time.LocalDate;
import java.time.Period;

public class Aranzman {

    private int id;
    private String naziv;
    private String destinacija;
    private Prevoz prevoz;
    private LocalDate datumPolaska, datumDolaska;
    private double cijena;
    private Smjestaj smjestaj;

    public Aranzman(int id, String naziv, String destinacija, Prevoz prevoz, LocalDate datumPolaska, LocalDate datumDolaska, double cijena, Smjestaj smjestaj) {
        this.id = id;
        this.naziv = naziv;
        this.destinacija = destinacija;
        this.prevoz = prevoz;
        this.datumPolaska = datumPolaska;
        this.datumDolaska = datumDolaska;
        this.cijena = cijena;
        this.smjestaj = smjestaj;
    }

    public int getId() {
        return id;
    }

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
}

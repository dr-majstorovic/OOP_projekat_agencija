package classes;

import java.util.ArrayList;

public class Rezervacija {

    private Klijent klijent;
    private Aranzman aranzman;
    private double ukupnaCijena, placeno;
    public static ArrayList<Rezervacija> all = new ArrayList<>();

    public Rezervacija(Klijent klijent, Aranzman aranzman, double ukupnaCijena, double placeno) {
        this.klijent = klijent;
        this.aranzman = aranzman;
        this.ukupnaCijena = ukupnaCijena;
        this.placeno = placeno;
        all.add(this);
    }

    public Rezervacija(Klijent klijent, Aranzman aranzman, double placeno){
        this(klijent, aranzman, racunajCijena(aranzman), placeno);
    }

    private static double racunajCijena(Aranzman aranzman){
        return aranzman.getCijena() + aranzman.getSmjestaj().getCijenaPN() * aranzman.getTrajanje();
    }

    public Klijent getKlijent() {
        return klijent;
    }

    public Aranzman getAranzman() {
        return aranzman;
    }

    public double getUkupnaCijena() {
        return ukupnaCijena;
    }

    public double getPlaceno() {
        return placeno;
    }

}

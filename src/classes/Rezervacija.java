package classes;

import java.util.ArrayList;

public class Rezervacija {

    private Klijent klijent;
    private Aranzman aranzman;
    private double ukupnaCijena, placeno;
    private String otkazana;
    public static ArrayList<Rezervacija> all = new ArrayList<>();

    public Rezervacija(Klijent klijent, Aranzman aranzman, double ukupnaCijena, double placeno, String otkazana) {
        this.klijent = klijent;
        this.aranzman = aranzman;
        this.ukupnaCijena = ukupnaCijena;
        this.placeno = placeno;
        this.otkazana = otkazana;
        all.add(this);
    }

    public Rezervacija(Klijent klijent, Aranzman aranzman, double placeno, String otkazana){
        this(klijent, aranzman, racunajCijena(aranzman), placeno, otkazana);
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

    public String getOtkazana() { return otkazana; }
}

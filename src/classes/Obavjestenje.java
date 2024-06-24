package classes;

import java.util.ArrayList;

public class Obavjestenje {

    private int id;
    private Klijent klijent;
    private String aranzman;
    private double iznos;
    public static ArrayList<Obavjestenje> all = new ArrayList<>();

    public Obavjestenje(int id, Klijent klijent, String aranzman, double iznos){
        this.id = id;
        this.klijent = klijent;
        this.aranzman = aranzman;
        this.iznos = iznos;
        all.add(this);
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Klijent getKlijent() { return klijent; }

    public String getAranzman() { return aranzman; }

    public double getIznos() { return iznos; }

    @Override
    public String toString() {
        return "Vaša rezervacija za aranžman < " + aranzman + " > je otkazana.\nNa vaš račun je vraćeno " + iznos
                + " KM.\nNovo stanje na računu: " + BankovniRacun.getFromJMBG(klijent.getJMBG()).getStanje()
                + "\nIzvinjavamo se zbog neprijatnosti.";
    }
}

package classes;

import java.util.ArrayList;

public class Obavjestenje {

    private int id;
    private Klijent klijent;
    private Aranzman aranzman;
    private double iznos;
    public static ArrayList<Obavjestenje> all = new ArrayList<>();

    public Obavjestenje(int id, Klijent klijent, Aranzman aranzman, double iznos){
        this.id = id;
        this.klijent = klijent;
        this.aranzman = aranzman;
        this.iznos = iznos;
        all.add(this);
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Klijent getKlijent() { return klijent; }

    public Aranzman getAranzman() { return aranzman; }

    public double getIznos() { return iznos; }

    @Override
    public String toString() {
        return "Vaša rezervacija za aranžman < " + aranzman + " > je otkazana. Na vaš račun je vraćeno " + iznos
                + " KM.\nIzvinjavamo se zbog neprijatnosti.";
    }
}

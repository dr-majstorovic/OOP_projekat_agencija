package classes;

import java.sql.SQLException;
import java.time.LocalDate;
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
        if(LocalDate.now().isAfter(aranzman.getDatumPolaska().minusDays(14)) &&
                otkazana.equals("ne") && placeno < aranzman.getUkupnaCijena()){
            otkazana = "da";
            BankovniRacun racun = BankovniRacun.getFromJMBG(klijent.getJMBG());
            BankovniRacun agencija = BankovniRacun.getFromJMBG("1102541293");

            racun.setStanje(racun.getStanje() + placeno - racunajCijena(aranzman) / 2);
            agencija.setStanje(agencija.getStanje() - placeno + racunajCijena(aranzman) /2);
            placeno = placeno - racunajCijena(aranzman) / 2;

            this.placeno = placeno;
            this.otkazana = otkazana;
            try{
                database.Write.updateBankovniRacun(racun);
                database.Write.updateBankovniRacun(agencija);
                database.Write.updatePlacenaCijena(this);
                database.Write.updateOtkazana(this);
            } catch (SQLException e) {
                System.out.println("Greska u bazi");
            }
        }
    }

    private static double racunajCijena(Aranzman aranzman){
        if(aranzman.getDatumPolaska().equals(aranzman.getDatumDolaska()))
            return aranzman.getCijena();
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

    public void setOtkazana(String otkazana) { this.otkazana = otkazana; }

    public void setPlaceno(double placeno) { this.placeno = placeno; }

    @Override
    public String toString() {
        if(LocalDate.now().isAfter(aranzman.getDatumPolaska().minusDays(3)))
            return "* " + aranzman;
        else
            return aranzman.toString();
    }
}

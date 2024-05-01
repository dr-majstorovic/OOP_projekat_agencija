package classes;

import java.util.ArrayList;

public class BankovniRacun {

    private String brojRacuna, JMBG;
    private Double stanje;
    public static ArrayList<BankovniRacun> all = new ArrayList<>();

    public BankovniRacun(String brojRacuna, String JMBG, Double stanje){
        this.brojRacuna = brojRacuna;
        this.JMBG = JMBG;
        this.stanje = stanje;
        all.add(this);
    }

    public String getBrojRacuna() {
        return brojRacuna;
    }

    public String getJMBG() {
        return JMBG;
    }

    public Double getStanje() {
        return stanje;
    }

    public void  setStanje(Double stanje) { this.stanje = stanje; }

    public static BankovniRacun getFromBrojRacuna(String br) {
        for(BankovniRacun x: all){
            if(x.getBrojRacuna().equals(br))
                return x;
        }
        return null;
    }

    public static BankovniRacun getFromJMBG(String br) {
        for(BankovniRacun x: all){
            if(x.getJMBG().equals(br))
                return x;
        }
        return null;
    }
}

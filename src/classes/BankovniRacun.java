package classes;

public class BankovniRacun {

    private String brojRacuna, JMBG;
    private Double stanje;

    public BankovniRacun(String brojRacuna, String JMBG, Double stanje){
        this.brojRacuna = brojRacuna;
        this.JMBG = JMBG;
        this.stanje = stanje;
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
}

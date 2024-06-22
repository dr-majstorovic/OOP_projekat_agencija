package classes;

import java.time.LocalDate;

public class Filteri {

    public Aranzman aranzman;

    public Filteri(Aranzman aranzman){
        this.aranzman = aranzman;
    }

    public boolean brojZvjezdica(Integer brZvjezdica){
        if(brZvjezdica == 0)
            return true;
        if(aranzman.getSmjestaj() == null)
            return false;
        return aranzman.getSmjestaj().getBrojZvjezdica() == brZvjezdica;
    }

    public boolean destinacija(String destinacija){
        if(destinacija == "")
            return true;
        return aranzman.getDestinacija().equals(destinacija);
    }

    public boolean minimalnaCijena(Double minCijena){
        return aranzman.getUkupnaCijena() >= minCijena;
    }

    public boolean maksimalnaCijena(Double maxCijena){
        if(maxCijena == 0.0)
            return true;
        return aranzman.getUkupnaCijena() <= maxCijena;
    }

    public boolean vrstaSmjestaja(Soba smjestaj){
        if(smjestaj == null)
            return true;
        if(aranzman.getSmjestaj() == null)
            return false;
        return aranzman.getSmjestaj().equals(smjestaj);
    }

    public boolean vrstaPrevoza(Prevoz prevoz){
        if(prevoz == null)
            return true;
        return aranzman.getPrevoz().equals(prevoz);
    }

    public boolean minimalanDatum(LocalDate minDatum){
        if(minDatum == null)
            return true;
        return aranzman.getDatumPolaska().isAfter(minDatum);
    }

    public boolean maksimalanDatum(LocalDate maxDatum){
        if(maxDatum == null)
            return true;
        return aranzman.getDatumPolaska().isBefore(maxDatum);
    }

}

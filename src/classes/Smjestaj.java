package classes;

import java.util.ArrayList;

public class Smjestaj {

    private int id;
    private String naziv;
    private int brojZvjezdica;
    private Soba vrstaSobe;
    private double cijenaPN;
    public static ArrayList<Smjestaj> all = new ArrayList<>();

    public Smjestaj(int id, String naziv, int brojZvjezdica, Soba vrstaSobe, double cijenaPN) {
        this.id = id;
        this.naziv = naziv;
        this.brojZvjezdica = brojZvjezdica;
        this.vrstaSobe = vrstaSobe;
        this.cijenaPN = cijenaPN;
        all.add(this);
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public int getBrojZvjezdica() {
        return brojZvjezdica;
    }

    public Soba getVrstaSobe() {
        return vrstaSobe;
    }

    public double getCijenaPN() {
        return cijenaPN;
    }

    public static Smjestaj getFromID(int id) {
        for(Smjestaj x: all){
            if(x.getId() == id)
                return x;
        }
        return null;
    }

    @Override
    public String toString() {
        return naziv + ", zvjezdica: (" + brojZvjezdica + "), " + vrstaSobe + " soba";
    }

    public void setId(int id) {
        this.id = id;
    }
}

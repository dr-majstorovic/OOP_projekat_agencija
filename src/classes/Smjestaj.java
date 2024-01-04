package classes;

public class Smjestaj {

    private String naziv;
    private int brojZvjezdica;
    private Soba vrstaSobe;
    private double cijenaPN;

    public Smjestaj(String naziv, int brojZvjezdica, Soba vrstaSobe, double cijenaPN) {
        this.naziv = naziv;
        this.brojZvjezdica = brojZvjezdica;
        this.vrstaSobe = vrstaSobe;
        this.cijenaPN = cijenaPN;
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
}

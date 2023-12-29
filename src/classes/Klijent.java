package classes;

public class Klijent extends Korisnik{

    private String brojTelefona, JMBG, brojRacuna;

    public Klijent(String ime, String prezime, String korisnickoIme, String lozinka, String brojTelefona, String JMBG, String brojRacuna){
        super(ime, prezime, korisnickoIme, lozinka);
        this.brojTelefona = brojTelefona;
        this.JMBG = JMBG;
        this.brojRacuna = brojRacuna;
        Korisnik.all.add(this);
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public String getJMBG() {
        return JMBG;
    }

    public String getBrojRacuna() {
        return brojRacuna;
    }
}

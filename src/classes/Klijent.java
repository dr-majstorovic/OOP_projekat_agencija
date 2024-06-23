package classes;

public class Klijent extends Korisnik{

    private String brojTelefona, JMBG, brojRacuna;

    public Klijent(int id, String ime, String prezime, String korisnickoIme, String lozinka, String brojTelefona, String JMBG, String brojRacuna){
        super(id, ime, prezime, korisnickoIme, lozinka);
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

    @Override
    public String toString() {
        return ime + " " + prezime;
    }
}

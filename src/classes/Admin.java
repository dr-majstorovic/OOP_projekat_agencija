package classes;

public class Admin extends Korisnik{

    public Admin(String ime, String prezime, String korisnickoIme, String lozinka){
        super(ime, prezime, korisnickoIme, lozinka);
        Korisnik.all.add(this);
    }
}

package classes;

public class Admin extends Korisnik{

    public Admin(int id, String ime, String prezime, String korisnickoIme, String lozinka){
        super(id, ime, prezime, korisnickoIme, lozinka);
        Korisnik.all.add(this);
    }
}

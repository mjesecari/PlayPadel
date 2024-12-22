package fer.progi.mjesecari.ppadel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Vlasnik extends Korisnik{
    private String NazivVlasnik;
    private String Lokacija;
    private String BrojTel;

    public Vlasnik() {}

    public Vlasnik(String nazivVlasnik, String lokacija, String brojTel) {
        NazivVlasnik = nazivVlasnik;
        Lokacija = lokacija;
        BrojTel = brojTel;
    }

    public String getNazivVlasnik() {
        return NazivVlasnik;
    }

    public void setNazivVlasnik(String nazivVlasnik) {
        NazivVlasnik = nazivVlasnik;
    }

    public String getLokacija() {
        return Lokacija;
    }

    public void setLokacija(String lokacija) {
        Lokacija = lokacija;
    }

    public String getBrojTel() {
        return BrojTel;
    }

    public void setBrojTel(String brojTel) {
        BrojTel = brojTel;
    }

    @Override
    public String toString() {
        return NazivVlasnik + "se nalazi na lokaciji " + Lokacija +", broj telefona =" + BrojTel;
    }
}

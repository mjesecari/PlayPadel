package fer.progi.mjesecari.ppadel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jdk.jfr.DataAmount;

import java.io.Serializable;

@Entity
public class Igrac extends Korisnik implements Serializable {

    public Igrac() {}

    private String ImeIgrac;

    private String PrezimeIgrac;

    private String BrojTel;

    public String getImeIgrac() {
        return ImeIgrac;
    }

    public void setImeIgrac(String imeIgrac) {
        ImeIgrac = imeIgrac;
    }

    public String getPrezimeIgrac() {
        return PrezimeIgrac;
    }

    public void setPrezimeIgrac(String prezimeIgrac) {
        PrezimeIgrac = prezimeIgrac;
    }

    public String getBrojTel() {
        return BrojTel;
    }

    public void setBrojTel(String brojTel) {
        BrojTel = brojTel;
    }

    public String toString() {
        return ImeIgrac + " " + PrezimeIgrac;
    }
}

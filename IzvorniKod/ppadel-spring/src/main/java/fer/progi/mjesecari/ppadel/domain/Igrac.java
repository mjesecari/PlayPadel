package fer.progi.mjesecari.ppadel.domain;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;

import java.io.Serializable;
import java.util.List;

@Entity
public class Igrac extends Korisnik implements Serializable {

    public Igrac() {}

    private String ImeIgrac;

    private String PrezimeIgrac;

    private String BrojTel;

    @OneToMany(mappedBy = "igrac", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrijavaTurnir> Prijaveturniri;

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

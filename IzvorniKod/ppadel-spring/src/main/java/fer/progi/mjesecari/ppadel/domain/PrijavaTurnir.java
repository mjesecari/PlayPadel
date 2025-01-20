package fer.progi.mjesecari.ppadel.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class PrijavaTurnir implements Serializable {
    @Id
    @GeneratedValue
    private Long IDPrijava;

    @ManyToOne
    private Igrac igrac;
    @ManyToOne
    private Turnir turnir;
    private String StatusPrijava;

    public PrijavaTurnir(Igrac igrac, Turnir turnir, String statusPrijava) {
        this.igrac = igrac;
        this.turnir = turnir;
        StatusPrijava = statusPrijava;
    }

    public PrijavaTurnir() {
    }

    public Long getIDPrijava() {
        return IDPrijava;
    }

    public void setIDPrijava(Long IDPrijava) {
        this.IDPrijava = IDPrijava;
    }

    public Igrac getIgrac() {
        return igrac;
    }

    public void setIgrac(Igrac igrac) {
        this.igrac = igrac;
    }

    public Turnir getTurnir() {
        return turnir;
    }

    public void setTurnir(Turnir turnir) {
        this.turnir = turnir;
    }

    public String getStatusPrijava() {
        return StatusPrijava;
    }

    public void setStatusPrijava(String statusPrijava) {
        StatusPrijava = statusPrijava;
    }

    @Override
    public String toString() {
        return "PrijavaTurnir{" +
                "IDPrijava=" + IDPrijava +
                ", Igrac=" + igrac +
                ", Turnir=" + turnir +
                ", StatusPrijava='" + StatusPrijava + '\'' +
                '}';
    }
}

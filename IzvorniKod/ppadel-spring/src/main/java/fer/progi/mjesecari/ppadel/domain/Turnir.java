package fer.progi.mjesecari.ppadel.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Turnir implements Serializable {
    @Id
    @GeneratedValue
    private Long IDTurnir;
    @ManyToOne
    private Vlasnik vlasnik;
    @NotNull
    private String LokacijaTurnir;
    @NotNull
    private String NazivTurnir;
    @NotNull
    private Date DatumTurnir;
    private List<String> Nagrade;
    private String StatusTurnir;

    public Turnir(Vlasnik vlasnik, String lokacijaTurnir, String nazivTurnir, Date datumTurnir, List<String> nagrade, String statusTurnir) {
        this.vlasnik = vlasnik;
        LokacijaTurnir = lokacijaTurnir;
        NazivTurnir = nazivTurnir;
        DatumTurnir = datumTurnir;
        Nagrade = nagrade;
        StatusTurnir = statusTurnir;
    }

    public Turnir() {
    }

    public Long getIDTurnir() {
        return IDTurnir;
    }

    public void setIDTurnir(Long IDTurnir) {
        this.IDTurnir = IDTurnir;
    }

    public Vlasnik getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Vlasnik vlasnik) {
        this.vlasnik = vlasnik;
    }

    public String getLokacijaTurnir() {
        return LokacijaTurnir;
    }

    public void setLokacijaTurnir(String lokacijaTurnir) {
        LokacijaTurnir = lokacijaTurnir;
    }

    public String getNazivTurnir() {
        return NazivTurnir;
    }

    public void setNazivTurnir(String nazivTurnir) {
        NazivTurnir = nazivTurnir;
    }

    public Date getDatumTurnir() {
        return DatumTurnir;
    }

    public void setDatumTurnir(Date datumTurnir) {
        DatumTurnir = datumTurnir;
    }

    public List<String> getNagrade() {
        return Nagrade;
    }

    public void setNagrade(List<String> nagrade) {
        Nagrade = nagrade;
    }

    public String getStatusTurnir() {
        return StatusTurnir;
    }

    public void setStatusTurnir(String statusTurnir) {
        StatusTurnir = statusTurnir;
    }

    @Override
    public String toString() {
        return "Turnir{" +
                "IDTurnir=" + IDTurnir +
                ", Vlasnik=" + vlasnik +
                ", LokacijaTurnir='" + LokacijaTurnir + '\'' +
                ", NazivTurnir='" + NazivTurnir + '\'' +
                ", DatumTurnir=" + DatumTurnir +
                ", Nagrade=" + Nagrade +
                ", StatusTurnir='" + StatusTurnir + '\'' +
                '}';
    }
}

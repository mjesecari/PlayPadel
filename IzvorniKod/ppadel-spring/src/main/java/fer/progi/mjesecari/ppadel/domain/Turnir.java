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
    @NotNull
    private Float CijenaKotizacije;
    private List<Float> Nagrade;
    @NotNull
    private String Opis;
    //private String StatusTurnir;

    @OneToMany(mappedBy = "turnir", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrijavaTurnir> prijaveTurnira;
    @OneToMany(mappedBy = "turnir", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SlikaTurnir> slikeTurnir;
    @OneToMany(mappedBy = "turnir", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KomentarTurnir> komentariTurnira;

    public Turnir(Vlasnik vlasnik, String lokacijaTurnir, String nazivTurnir, Date datumTurnir, List<Float> nagrade) {
        this.vlasnik = vlasnik;
        LokacijaTurnir = lokacijaTurnir;
        NazivTurnir = nazivTurnir;
        DatumTurnir = datumTurnir;
        Nagrade = nagrade;
        //StatusTurnir = statusTurnir;
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

    public List<Float> getNagrade() {
        return Nagrade;
    }

    public void setNagrade(List<Float> nagrade) {
        Nagrade = nagrade;
    }

    public @NotNull Float getCijenaKotizacije() {
        return CijenaKotizacije;
    }

    public void setCijenaKotizacije(@NotNull Float cijenaKotizacije) {
        CijenaKotizacije = cijenaKotizacije;
    }

    public @NotNull String getOpis() {
        return Opis;
    }

    public void setOpis(@NotNull String opis) {
        Opis = opis;
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
                '}';
    }
}

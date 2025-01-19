package fer.progi.mjesecari.ppadel.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fer.progi.mjesecari.ppadel.domain.Turnir;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;

import java.util.Date;
import java.util.List;

public class TurnirDTO {
    private Vlasnik Vlasnik;
    private String LokacijaTurnir;
    private String NazivTurnir;
    private Date DatumTurnir;
    private List<String> Nagrade;
    private String StatusTurnir;

    public TurnirDTO(@JsonProperty("vlasnik") Vlasnik vlasnik, @JsonProperty("lokacijaTurnir") String lokacijaTurnir, @JsonProperty("nazivTurnir") String nazivTurnir, @JsonProperty("datumTurnir") Date datumTurnir, @JsonProperty("nagrade") List<String> nagrade, @JsonProperty("statusTurnir") String statusTurnir) {
        this.Vlasnik = vlasnik;
        this.LokacijaTurnir = lokacijaTurnir;
        this.NazivTurnir = nazivTurnir;
        this.DatumTurnir = datumTurnir;
        this.Nagrade = nagrade;
        this.StatusTurnir = statusTurnir;
    }

    public fer.progi.mjesecari.ppadel.domain.Vlasnik getVlasnik() {
        return Vlasnik;
    }

    public void setVlasnik(fer.progi.mjesecari.ppadel.domain.Vlasnik vlasnik) {
        Vlasnik = vlasnik;
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
}

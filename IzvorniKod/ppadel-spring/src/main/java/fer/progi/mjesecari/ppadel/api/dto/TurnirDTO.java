package fer.progi.mjesecari.ppadel.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fer.progi.mjesecari.ppadel.domain.Turnir;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;

import java.util.Date;
import java.util.List;

public class TurnirDTO {
    private Long VlasnikID;
    private String LokacijaTurnir;
    private String NazivTurnir;
    private Date DatumTurnir;
    private Float CijenaKotizacije;
    private List<Float> Nagrade;
    private String Opis;


    public TurnirDTO(@JsonProperty("vlasnikId") Long vlasnikId, @JsonProperty("lokacijaTurnir") String lokacijaTurnir, @JsonProperty("nazivTurnir") String nazivTurnir, @JsonProperty("datumTurnir") Date datumTurnir,@JsonProperty("cijenaKotizacije") Float cijenaKotizacije, @JsonProperty("nagrade") List<Float> nagrade, @JsonProperty("opis") String opis) {
        this.VlasnikID = vlasnikId;
        this.LokacijaTurnir = lokacijaTurnir;
        this.NazivTurnir = nazivTurnir;
        this.DatumTurnir = datumTurnir;
        this.CijenaKotizacije = cijenaKotizacije;
        this.Nagrade = nagrade;
        this.Opis = opis;
    }

    public Long getVlasnik() {
        return VlasnikID;
    }

    public void setVlasnik(Long vlasnikId) {
        VlasnikID = vlasnikId;
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

    public Float getCijenaKotizacije() {
        return CijenaKotizacije;
    }

    public void setCijenaKotizacije(Float cijenaKotizacije) {
        CijenaKotizacije = cijenaKotizacije;
    }

    public String getOpis() {
        return Opis;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }
}

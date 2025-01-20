package fer.progi.mjesecari.ppadel.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrijavaTurnirDTO {
    private Long IDKorisnik;
    private Long IDTurnir;
    private String StatusPrijava;

    public PrijavaTurnirDTO(@JsonProperty("IDKorisnik") Long IDKorisnik,@JsonProperty("IDTurnir") Long IDTurnir,@JsonProperty("statusPrijava") String statusPrijava) {
        this.IDKorisnik = IDKorisnik;
        this.IDTurnir = IDTurnir;
        this.StatusPrijava = statusPrijava;
    }

    public Long getIDKorisnik() {
        return IDKorisnik;
    }

    public void setIDKorisnik(Long IDKorisnik) {
        this.IDKorisnik = IDKorisnik;
    }

    public Long getIDTurnir() {
        return IDTurnir;
    }

    public void setIDTurnir(Long IDTurnir) {
        this.IDTurnir = IDTurnir;
    }

    public String getStatusPrijava() {
        return StatusPrijava;
    }

    public void setStatusPrijava(String statusPrijava) {
        StatusPrijava = statusPrijava;
    }

    @Override
    public String toString() {
        return "PrijavaTurnirDTO{" +
                "IDKorisnik=" + IDKorisnik +
                ", IDTurnir=" + IDTurnir +
                ", StatusPrijava='" + StatusPrijava + '\'' +
                '}';
    }
}

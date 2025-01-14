package fer.progi.mjesecari.ppadel.domain;


import org.springframework.util.Assert;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Teren {
    @Id
    @GeneratedValue
    private Long IDTeren;

    @ManyToOne
    Korisnik vlasnikTeren;

    @NotNull
    private String TipTeren;

    @NotNull
    private String NazivTeren;

    private String LokacijaTeren;

    public String getLokacijaTeren() {
        return LokacijaTeren;
    }

    public void setLokacijaTeren(String lokacijaTeren) {
        LokacijaTeren = lokacijaTeren;
    }

    public Teren(){
        
    }

    public Teren(String naziv, Korisnik vlasnik, String tip) {
        Assert.hasText(naziv, "Teren mora imati naziv");
        Assert.notNull(vlasnik, "Vlasnik mora biti naveden");
        if( !( tip.equals("unutarnji") || tip.equals("vanjski") ) ){
            throw new IllegalArgumentException("Tip mora biti unutarnji ili vanjski");
        }
        
        this.vlasnikTeren = vlasnik;
        this.NazivTeren = naziv;
        this.TipTeren = tip;
    }

    public Long getIDTeren() {
        return IDTeren;
    }

    public void setIDTeren(Long iDTeren) {
        IDTeren = iDTeren;
    }

    public Korisnik getVlasnikTeren() {
        return vlasnikTeren;
    }

    public void setVlasnikTerena(Korisnik vlasnikTeren) {
        Assert.notNull(vlasnikTeren, "Vlasnik mora biti naveden");
        this.vlasnikTeren = vlasnikTeren;
    }

    public String getTipTeren() {
        return TipTeren;
    }

    public void setTipTeren(String tipTeren) {
        if( !( tipTeren.equals("unutarnji") || tipTeren.equals("vanjski") ) ){
            throw new IllegalArgumentException("Tip mora biti unutarnji ili vanjski");
        }
        TipTeren = tipTeren;
    }

    public String getNazivTeren() {
        return NazivTeren;
    }

    public void setNazivTeren(String nazivTeren) {
        Assert.hasText(nazivTeren, "Teren mora imati naziv");
        NazivTeren = nazivTeren;
    }



}

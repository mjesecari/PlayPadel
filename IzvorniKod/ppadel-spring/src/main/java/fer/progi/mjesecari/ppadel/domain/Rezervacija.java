package fer.progi.mjesecari.ppadel.domain;

import java.time.LocalDateTime;

import org.springframework.util.Assert;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(RezervacijaId.class)
public class Rezervacija {
    @Id
    @ManyToOne
    private Teren zaTeren;
    @Id
    private LocalDateTime vrijeme;
    
    @ManyToOne
    private Korisnik korisnik;

    // trajanje uvijek 1h

    public Rezervacija(Teren zaTeren, LocalDateTime vrijeme, Korisnik korisnik){
        Assert.notNull(korisnik, "Korisnik mora biti naveden");
        this.zaTeren = zaTeren;
        this.vrijeme = vrijeme;
        this.korisnik = korisnik;

    }
    
    public Rezervacija() {
    }


    public Korisnik getKorisnik() {
        return korisnik;
    }
    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
    public Teren getZaTeren() {
        return zaTeren;
    }
    public void setZaTeren(Teren zaTeren) {
        this.zaTeren = zaTeren;
    }
    public LocalDateTime getVrijeme() {
        return vrijeme;
    }
    public void setVrijeme(LocalDateTime vrijeme) {
        this.vrijeme = vrijeme;
    }
    


}

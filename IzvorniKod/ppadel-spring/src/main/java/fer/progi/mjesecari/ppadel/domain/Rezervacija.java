package fer.progi.mjesecari.ppadel.domain;

import java.time.LocalDateTime;

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
    
    // trajanje uvijek 1h


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

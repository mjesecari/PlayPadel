package fer.progi.mjesecari.ppadel.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RezervacijaId implements Serializable {
    private Teren zaTeren;

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

    private LocalDateTime vrijeme;

    // default constructor

    public RezervacijaId(Teren zaTeren, LocalDateTime vrijeme) {
        this.zaTeren = zaTeren;
        this.vrijeme = vrijeme;
    }

}

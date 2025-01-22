package fer.progi.mjesecari.ppadel.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
@Entity
public class KomentarTurnir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKomentarTurnir;
    @ManyToOne
    private Korisnik korisnik;
    @ManyToOne
    private Turnir turnir;
    @Column(nullable = false)
    @NotNull
    private String tekstKomentara;

    public Long getIdKomentarTurnir() {
        return idKomentarTurnir;
    }

    public void setIdKomentarTurnir(Long idKomentarTurnir) {
        this.idKomentarTurnir = idKomentarTurnir;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Turnir getTurnir() {
        return turnir;
    }

    public void setTurnir(Turnir turnir) {
        this.turnir = turnir;
    }

    public @NotNull String getTekstKomentara() {
        return tekstKomentara;
    }

    public void setTekstKomentara(@NotNull String tekstKomentara) {
        this.tekstKomentara = tekstKomentara;
    }
}

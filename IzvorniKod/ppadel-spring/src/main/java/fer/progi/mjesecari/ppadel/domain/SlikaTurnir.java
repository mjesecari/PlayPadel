package fer.progi.mjesecari.ppadel.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class SlikaTurnir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSlikaTurnir;
    @ManyToOne
    private Korisnik korisnik;
    @ManyToOne
    private Turnir turnir;
    @Column(nullable = false)
    @NotNull
    @Lob
    private byte[] photoData;

    public Long getIdSlikaTurnir() {
        return idSlikaTurnir;
    }

    public void setIdSlikaTurnir(Long idSlikaTurnir) {
        this.idSlikaTurnir = idSlikaTurnir;
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

    @NotNull
    public byte[] getPhotoData() {
        return photoData;
    }

    public void setPhotoData(@NotNull byte[] photoData) {
        this.photoData = photoData;
    }
}

package fer.progi.mjesecari.ppadel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
@Entity
public class SlikaTeren {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    @Lob
    private byte[] PhotoData; // The path to the picture (could also be a URL or byte array)

    @OneToOne
    @JoinColumn(name = "idTeren", nullable = false)
    @JsonIgnore
    private Teren teren;

    @NotNull
    public byte[] getPhotoData() {
        return PhotoData;
    }

    public void setPhotoData(@NotNull byte[] photoData) {
        PhotoData = photoData;
    }

    public Teren getTeren() {
        return teren;
    }

    public void setTeren(Teren teren) {
        this.teren = teren;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SlikaTeren{" +
                "id=" + id +
                ", PhotoData=" + Arrays.toString(PhotoData);
    }
}

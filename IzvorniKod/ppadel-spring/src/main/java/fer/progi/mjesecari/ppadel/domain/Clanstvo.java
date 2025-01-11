package fer.progi.mjesecari.ppadel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Clanstvo {
    @Id
    private Long Id;
    private LocalDate datumIsteka;
    private String nacinPlacanja;
    private Double iznos;

    public Clanstvo() {
    }

    public Clanstvo(Long id) {
        Id = id;
        datumIsteka=null;
        nacinPlacanja = null;
        iznos = null;
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public LocalDate getDatumIsteka() {
        return datumIsteka;
    }

    public void setDatumIsteka(LocalDate datumIsteka) {
        this.datumIsteka = datumIsteka;
    }

    public String getNacinPlacanja() {
        return nacinPlacanja;
    }

    public void setNacinPlacanja(String nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }

    public Double getIznos() {
        return iznos;
    }

    public void setIznos(Double iznos) {
        this.iznos = iznos;
    }

    @Override
    public String toString() {
        return "Clanstvo{" +
                "Id=" + Id +
                ", datumIsteka=" + datumIsteka +
                ", nacinPlacanja='" + nacinPlacanja + '\'' +
                ", iznos=" + iznos +
                '}';
    }
}

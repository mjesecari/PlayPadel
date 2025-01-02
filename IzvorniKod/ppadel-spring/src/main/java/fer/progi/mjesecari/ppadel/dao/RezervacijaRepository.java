package fer.progi.mjesecari.ppadel.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fer.progi.mjesecari.ppadel.domain.Rezervacija;
import fer.progi.mjesecari.ppadel.domain.RezervacijaId;
import fer.progi.mjesecari.ppadel.domain.Teren;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, RezervacijaId>{
    List<Rezervacija> findAll();
    Rezervacija findByZaTerenAndVrijeme(Teren zaTeren, LocalDateTime vrijeme);
    Rezervacija findByZaTerenIDTerenAndVrijeme(Long zaTerenIDteren, LocalDateTime vrijeme);
    List<Rezervacija> findAllByZaTerenIDTeren(Long zaTerenIDTeren);
    List<Rezervacija> findByVrijemeBetween(LocalDateTime t1, LocalDateTime t2);
    List<Rezervacija> findByZaTerenIDTerenAndVrijemeBetween(Long ZaTerenIDTeren, LocalDateTime t1, LocalDateTime t2);
}

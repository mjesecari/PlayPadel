package fer.progi.mjesecari.ppadel.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fer.progi.mjesecari.ppadel.domain.Rezervacija;
import fer.progi.mjesecari.ppadel.domain.RezervacijaId;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, RezervacijaId>{
    List<Rezervacija> findAllByZaTerenIDTeren(Long ZaTerenIDTeren);
    List<Rezervacija> findByVrijemeBetween(LocalDateTime t1, LocalDateTime t2);
    List<Rezervacija> findByZaTerenIDTerenAndVrijemeBetween(Long ZaTerenIDTeren, LocalDateTime t1, LocalDateTime t2);
}

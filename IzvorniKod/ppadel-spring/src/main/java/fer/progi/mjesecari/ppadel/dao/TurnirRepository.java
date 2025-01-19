package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Turnir;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TurnirRepository extends JpaRepository<Turnir, Long> {
    List<Turnir> findByVlasnik (Vlasnik vlasnik);
    Optional<Turnir> findById (Long IDTurnir);
    List<Turnir> findAll();
    @Override
    void deleteById (Long IDTurnir);
    @Query("SELECT i FROM Igrac i JOIN PrijavaTurnir p WHERE (p.turnir.IDTurnir = :turnirID and i.IDKorisnik = p.igrac.IDKorisnik and StatusTurnir = 'naCekanju')")
    List<Igrac> findIgracByIDTurnirAndStatus(@Param("turnirID")Long IDTurnir);
}
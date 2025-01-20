package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Turnir;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TurnirRepository extends JpaRepository<Turnir, Long> {
    List<Turnir> findByVlasnik (Vlasnik vlasnik);
    Optional<Turnir> findById (Long IDTurnir);
    List<Turnir> findAll();
    @Override
    void deleteById (Long IDTurnir);
    @Query("SELECT i FROM Igrac i JOIN PrijavaTurnir p ON i.id = p.igrac.id WHERE (p.turnir.IDTurnir = :turnirID and i.id = p.igrac.id and p.StatusPrijava = :status)")
    List<Igrac> findIgracByIDTurnirAndStatus(@Param("turnirID")Long IDTurnir, @Param("status") String StatusPrijava);
    @Query("SELECT t FROM Turnir t WHERE NOT EXISTS (SELECT 1 FROM PrijavaTurnir p WHERE p.turnir.IDTurnir = t.IDTurnir AND p.igrac.id = :idKorisnik) AND t.DatumTurnir > :currentDate")
    List<Turnir> findAllTurnirForAplying (@Param("idKorisnik") Long IDKorisnik, @Param("currentDate") LocalDateTime currentDate);
}
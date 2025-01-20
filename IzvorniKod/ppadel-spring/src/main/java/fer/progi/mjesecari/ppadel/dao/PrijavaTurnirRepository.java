package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.PrijavaTurnir;
import fer.progi.mjesecari.ppadel.domain.Turnir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PrijavaTurnirRepository extends JpaRepository<PrijavaTurnir, Long> {
    List<PrijavaTurnir> findByIgrac (Igrac igrac);
    Optional<PrijavaTurnir> findById (Long IDPrijava);
    List<PrijavaTurnir> findAll();
    @Override
    void deleteById (Long IDPrijava);
    @Query("SELECT p FROM PrijavaTurnir p WHERE p.StatusPrijava = :statusPrijava")
    PrijavaTurnir findByStatusPrijava (@Param("statusPrijava") String StatusPrijava);
    @Query("SELECT p FROM PrijavaTurnir p WHERE p.igrac.id = :IdIgrac and p.turnir.IDTurnir = :IdTurnir")
    PrijavaTurnir findByigracandturnir (@Param("IdIgrac") Long IdIgrac, @Param("IdTurnir") Long IdTurnir);
    @Query("SELECT p.turnir FROM PrijavaTurnir p WHERE p.igrac.id = :IdIgrac and p.StatusPrijava = :status AND p.turnir.DatumTurnir > :currentDate")
    List<Turnir> findAllTurnirStatus (@Param("IdIgrac") Long idIgrac, @Param("status") String status, @Param("currentDate") LocalDateTime currentDate);
    @Query("SELECT p.turnir FROM PrijavaTurnir p WHERE p.igrac.id = :IdIgrac and p.StatusPrijava = 'prihvacen' AND p.turnir.DatumTurnir < :currentDate")
    List<Turnir> findAllPlayedTurnirs (@Param("IdIgrac") Long idIgrac, @Param("currentDate") LocalDateTime currentDate);
}
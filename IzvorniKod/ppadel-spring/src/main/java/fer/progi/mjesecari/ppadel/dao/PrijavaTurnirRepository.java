package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.PrijavaTurnir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
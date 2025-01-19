package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.PrijavaTurnir;
import fer.progi.mjesecari.ppadel.service.PrijavaTurnirService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrijavaTurnirRepository extends JpaRepository<PrijavaTurnir, Long> {
    List<PrijavaTurnir> findByIgracID (Long IgracID);
    Optional<PrijavaTurnir> findById (Long IDPrijava);
    List<PrijavaTurnir> findAll();
    @Override
    void deleteById (Long IDPrijava);
    List<PrijavaTurnir> findByStatus (String status);
}
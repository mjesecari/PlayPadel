package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.SlikaTurnir;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlikaTurnirRepository extends JpaRepository<SlikaTurnir,Long> {
    public List<SlikaTurnir> findAllByTurnirIDTurnir (Long IDTurnir);
}

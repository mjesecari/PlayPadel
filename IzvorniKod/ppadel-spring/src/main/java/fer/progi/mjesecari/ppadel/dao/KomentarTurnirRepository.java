package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.KomentarTurnir;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KomentarTurnirRepository extends JpaRepository<KomentarTurnir,Long> {
    public List<KomentarTurnir> findAllByTurnirIDTurnir (Long IDTurnir);
}

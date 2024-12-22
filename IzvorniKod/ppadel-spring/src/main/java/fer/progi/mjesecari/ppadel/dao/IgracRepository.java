package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.Igrac;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IgracRepository extends JpaRepository<Igrac, Long> {
    @Override
    Optional<Igrac> findById(Long IDKorisnik);

}

package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.Igrac;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IgracRepository extends JpaRepository<Igrac, Long> {
    @Override
    Optional<Igrac> findById(Long IDKorisnik);
    Optional<Igrac> findByEmail(String Email);
    List<Igrac> findAll();
    @Override
    void deleteById(Long IDKorisnik);
}

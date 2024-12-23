package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VlasnikRepository extends JpaRepository<Vlasnik, Long> {
    @Override
    Optional<Vlasnik> findById(Long IDKorisnik);

    Optional<Vlasnik> findByEmail(String email);
}

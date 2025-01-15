package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VlasnikRepository extends JpaRepository<Vlasnik, Long> {

    @Override
    Optional<Vlasnik> findById(Long IDKorisnik);
    List<Vlasnik> findByIdIn(List<Long> ids);

    Optional<Vlasnik> findByEmail(String email);

    @Override
    void deleteById(Long IDKorisnik);
}

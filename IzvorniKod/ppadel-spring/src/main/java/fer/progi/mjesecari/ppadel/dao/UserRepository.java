package fer.progi.mjesecari.ppadel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fer.progi.mjesecari.ppadel.domain.Korisnik;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Korisnik, Long> {

  Optional<Korisnik> findByEmail(String email);
  boolean existsByEmailAndIdNot(String email, Long id);
  // Note: exists- query is the best if we just want to predict conflicts
  boolean existsByEmail(String email);
}


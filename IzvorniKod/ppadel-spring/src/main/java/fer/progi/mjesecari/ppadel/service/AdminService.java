
package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.Administrator;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Korisnik> getAllUsers();
    Korisnik addUser(Korisnik korisnik);
    Korisnik deleteUser(long id);
    List<Korisnik> getUsersByType(String type);
    Korisnik fetch(long korisnikId);
    Optional<Korisnik> findById(long korisnikId);
    public Administrator updateClanarina (long id, Float novaCijenaClanarine);
    //TODO add method for filtering owners by payed membership
}

package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.api.dto.IgracDTO;
import fer.progi.mjesecari.ppadel.api.dto.VlasnikDTO;
import fer.progi.mjesecari.ppadel.domain.Administrator;
import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Korisnik> getAllUsers();
    Korisnik addUser(Korisnik korisnik);
    Vlasnik addVlasnik (VlasnikDTO vlasnik);
    Igrac addIgrac(IgracDTO igrac);
    Korisnik deleteUser(long id);
    List<Korisnik> getUsersByType(String type);
    Korisnik fetch(long korisnikId);
    Optional<Korisnik> findById(long korisnikId);
    public Administrator updateClanarina (long id, Float novaCijenaClanarine);
    public Double getClanarina();
    //TODO add method for filtering owners by payed membership
}

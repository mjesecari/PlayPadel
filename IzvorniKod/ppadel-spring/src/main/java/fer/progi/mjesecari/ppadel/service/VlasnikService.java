package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.Vlasnik;

import java.util.List;
import java.util.Optional;

public interface VlasnikService {
    List<Vlasnik> listAll();
    Vlasnik createVlasnik(Vlasnik vlasnik);
    void validate(Vlasnik vlasnik);
    Optional<Vlasnik> findByEmail (String email);
}

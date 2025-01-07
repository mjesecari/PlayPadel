package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface VlasnikService {
    List<Vlasnik> listAll();
    Vlasnik createVlasnik(Vlasnik vlasnik);
    void validate(Vlasnik vlasnik);
    Optional<Vlasnik> findByEmail (String email);
    void deleteVlasnik(Long id);
    Vlasnik fetch(Long id);
    Vlasnik updateNazivVlasnik(Long id, String NazivVlasnik);
    Vlasnik updateLokacija(Long id, String lokacija);
    Vlasnik updateBrojTel(Long id, String brojTel);
}

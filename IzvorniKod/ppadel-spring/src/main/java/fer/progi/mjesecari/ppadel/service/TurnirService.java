package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.Turnir;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface TurnirService {
    List<Turnir> listAll();
    Optional<Turnir> findById(Long IDTurnir);
    Turnir fetch (Long IDTurnir);
    Turnir updateTurnirLokacija (Long IDTurnir, String LokacijaTurnir);
    Turnir updateTurnirNaziv (Long IDTurnir, String NazivTurnir);
    Turnir updateTurnirDatum (Long IDTurnir, Date DatumTurnir);
    Turnir updateTurnirNagrade (Long IDTurnir, List<Float> Nagrade);
    Turnir updateTurnirCijenaKotizacije (Long IDTurnir, Float cijenaKotizacije);
    Turnir updateOpis (Long IDTurnir, String opis);
    Turnir deleteTurnir (Long IDTurnir);
}

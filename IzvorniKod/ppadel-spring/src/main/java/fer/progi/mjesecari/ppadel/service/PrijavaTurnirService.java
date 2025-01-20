package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.PrijavaTurnir;
import fer.progi.mjesecari.ppadel.domain.Turnir;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PrijavaTurnirService {
    List<PrijavaTurnir> listAll();
    PrijavaTurnir fetch (Long IDPrijava);
    PrijavaTurnir updateStatusPrijava (Long IDPrijava, String StatusPrijava);
    PrijavaTurnir getByIDgracandIdturnir (Long idIgrac, Long idTurnir);
    List<Turnir> getAllForAplying(Long idIgrac);
    List<Turnir> getAllWithStatusPrijava (Long idIgrac, String status);
    List<Turnir> getAllPlayed (Long idIgrac);
}

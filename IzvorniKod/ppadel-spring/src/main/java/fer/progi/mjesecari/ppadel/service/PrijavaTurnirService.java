package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.PrijavaTurnir;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PrijavaTurnirService {
    List<PrijavaTurnir> listAll();
    PrijavaTurnir fetch (Long IDPrijava);
    PrijavaTurnir updateStatusPrijava (Long IDPrijava, String StatusPrijava);
}

package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.KomentarTurnir;
import fer.progi.mjesecari.ppadel.domain.SlikaTurnir;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KomentarTurnirService {
    public KomentarTurnir uploadComment (String KomentarTekst, Long Idturnir, Long IdKorisnik);
    public List<KomentarTurnir> fetchAll (Long Idturnir);
}

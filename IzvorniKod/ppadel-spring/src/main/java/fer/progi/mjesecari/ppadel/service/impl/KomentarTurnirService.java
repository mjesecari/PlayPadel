package fer.progi.mjesecari.ppadel.service.impl;

import fer.progi.mjesecari.ppadel.dao.KomentarTurnirRepository;
import fer.progi.mjesecari.ppadel.domain.KomentarTurnir;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.domain.Turnir;
import fer.progi.mjesecari.ppadel.service.KorisnikService;
import fer.progi.mjesecari.ppadel.service.TurnirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class KomentarTurnirService implements fer.progi.mjesecari.ppadel.service.KomentarTurnirService {
    @Autowired
    private TurnirService turnirService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private KomentarTurnirRepository komentarTurnirRepository;
    @Override
    public KomentarTurnir uploadComment(String KomentarTekst, Long Idturnir, Long IdKorisnik) {
        KomentarTurnir komentarTurnir = new KomentarTurnir();
        Turnir turnir = turnirService.fetch(Idturnir);
        Korisnik korisnik = korisnikService.fetch(IdKorisnik);
        komentarTurnir.setTurnir(turnir);
        komentarTurnir.setKorisnik(korisnik);
        komentarTurnir.setTekstKomentara(KomentarTekst);
        return komentarTurnirRepository.save(komentarTurnir);
    }

    @Override
    public List<KomentarTurnir> fetchAll(Long Idturnir) {
        return komentarTurnirRepository.findAllByTurnirIDTurnir(Idturnir);
    }
}

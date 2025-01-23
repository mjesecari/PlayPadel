package fer.progi.mjesecari.ppadel.service.impl;

import fer.progi.mjesecari.ppadel.dao.SlikaTurnirRepository;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.domain.SlikaTeren;
import fer.progi.mjesecari.ppadel.domain.SlikaTurnir;
import fer.progi.mjesecari.ppadel.domain.Turnir;
import fer.progi.mjesecari.ppadel.service.KorisnikService;
import fer.progi.mjesecari.ppadel.service.SlikaTurnirService;
import fer.progi.mjesecari.ppadel.service.TurnirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlikaTurnirServiceJpa implements SlikaTurnirService {
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private TurnirService turnirService;
    @Autowired
    private SlikaTurnirRepository slikaTurnirRepository;
    @Override
    public SlikaTurnir uploadPicture(byte[] pictureData, Long IdTurnir, Long IdKorisnik) {
        SlikaTurnir slikaTurnir = new SlikaTurnir();
        Turnir turnir = turnirService.fetch(IdTurnir);
        Korisnik korisnik = korisnikService.fetch(IdKorisnik);
        slikaTurnir.setTurnir(turnir);
        slikaTurnir.setKorisnik(korisnik);
        slikaTurnir.setPhotoData(pictureData);
        return slikaTurnirRepository.save(slikaTurnir);
    }

    @Override
    public List<SlikaTurnir> fetchAll(Long Idturnir) {
        return slikaTurnirRepository.findAllByTurnirIDTurnir(Idturnir);
    }
}

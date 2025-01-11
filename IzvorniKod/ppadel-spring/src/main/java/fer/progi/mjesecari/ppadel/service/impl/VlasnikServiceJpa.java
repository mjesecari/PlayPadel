package fer.progi.mjesecari.ppadel.service.impl;

import fer.progi.mjesecari.ppadel.dao.ClanstvoRepository;
import fer.progi.mjesecari.ppadel.dao.VlasnikRepository;
import fer.progi.mjesecari.ppadel.domain.Clanstvo;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import fer.progi.mjesecari.ppadel.service.ClanstvoService;
import fer.progi.mjesecari.ppadel.service.EntityMissingException;
import fer.progi.mjesecari.ppadel.service.VlasnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class VlasnikServiceJpa implements VlasnikService {
    @Autowired
    private VlasnikRepository vlasnikRepository;
    @Autowired
    private ClanstvoService clanstvoService;
    @Override
    public List<Vlasnik> listAll() {
        return vlasnikRepository.findAll();
    }
    @Override
    public Vlasnik createVlasnik(Vlasnik vlasnik) {
        validate(vlasnik);
        Vlasnik vlasnikSaved = vlasnikRepository.save(vlasnik);
        Clanstvo clanstvo = clanstvoService.CreateClanstvo(vlasnikSaved.getId());
        return vlasnikSaved;
    }

    @Override
    public void validate(Vlasnik vlasnik) {
        Assert.notNull(vlasnik, "Vlasnik mora biti predan");
        Assert.hasText(vlasnik.getNazivVlasnik(), "Vlasnik mora imati naziv");
        Assert.hasText(vlasnik.getLokacija(), "Vlasnik mora imati zadanu lokaciju");
        Assert.hasText(vlasnik.getBrojTel(), "Vlasnik mora imati broj telefona");
    }

    @Override
    public Optional<Vlasnik> findByEmail(String email) {
        return vlasnikRepository.findByEmail(email);
    }

    @Override
    public void deleteVlasnik(Long id) {
        vlasnikRepository.deleteById(id);
    }

    @Override
    public Vlasnik fetch(Long id) {
        return vlasnikRepository.findById(id).orElseThrow(() ->
                new EntityMissingException(Vlasnik.class, id));
    }

    @Override
    public Vlasnik updateNazivVlasnik(Long id, String nazivVlasnik) {
        Vlasnik vlasnik = fetch(id);
        vlasnik.setNazivVlasnik(nazivVlasnik);
        return vlasnikRepository.save(vlasnik);
    }

    @Override
    public Vlasnik updateLokacija(Long id, String lokacija) {
        Vlasnik vlasnik = fetch(id);
        vlasnik.setLokacija(lokacija);
        return vlasnikRepository.save(vlasnik);
    }

    @Override
    public Vlasnik updateBrojTel(Long id, String brojTel) {
        Vlasnik vlasnik = fetch(id);
        vlasnik.setBrojTel(brojTel);
        return vlasnikRepository.save(vlasnik);
    }

}

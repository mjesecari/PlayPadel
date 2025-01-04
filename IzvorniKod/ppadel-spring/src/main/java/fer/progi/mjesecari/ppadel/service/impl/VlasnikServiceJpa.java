package fer.progi.mjesecari.ppadel.service.impl;

import fer.progi.mjesecari.ppadel.dao.VlasnikRepository;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import fer.progi.mjesecari.ppadel.service.VlasnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class VlasnikServiceJpa implements VlasnikService {
    @Autowired
    private VlasnikRepository vlasnikRepository;

    @Override
    public List<Vlasnik> listAll() {
        return vlasnikRepository.findAll();
    }
    @Override
    public Vlasnik createVlasnik(Vlasnik vlasnik) {
        validate(vlasnik);
        return vlasnikRepository.save(vlasnik);
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

}

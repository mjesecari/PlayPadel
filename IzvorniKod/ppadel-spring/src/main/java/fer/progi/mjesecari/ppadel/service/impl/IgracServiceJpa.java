package fer.progi.mjesecari.ppadel.service.impl;

import fer.progi.mjesecari.ppadel.dao.IgracRepository;
import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.service.IgracService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

public class IgracServiceJpa implements IgracService {

    @Autowired
    private IgracRepository igracRepository;

    @Override
    public List<Igrac> listAll() {
        return igracRepository.findAll();
    }
    @Override
    public Igrac createIgrac(Igrac igrac) {
        validate(igrac);
        return igracRepository.save(igrac);
    }

    @Override
    public void validate(Igrac igrac) {
        Assert.notNull(igrac, "Igrač mora biti predan");
        Assert.hasText(igrac.getImeIgrac(), "Igrac mora imati ime");
        Assert.hasText(igrac.getPrezimeIgrac(), "Igrac mora imati prezime");
        Assert.hasText(igrac.getBrojTel(), "Igrac mora imati broj telefona");
    }
}

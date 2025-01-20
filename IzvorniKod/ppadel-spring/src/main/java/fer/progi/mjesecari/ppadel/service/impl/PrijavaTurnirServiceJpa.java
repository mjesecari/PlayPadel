package fer.progi.mjesecari.ppadel.service.impl;

import fer.progi.mjesecari.ppadel.dao.PrijavaTurnirRepository;
import fer.progi.mjesecari.ppadel.domain.PrijavaTurnir;
import fer.progi.mjesecari.ppadel.service.PrijavaTurnirService;
import fer.progi.mjesecari.ppadel.service.exception.EntityMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrijavaTurnirServiceJpa implements PrijavaTurnirService {
    @Autowired
    PrijavaTurnirRepository prijavaTurnirRepository;

    @Override
    public List<PrijavaTurnir> listAll() {
        return prijavaTurnirRepository.findAll();
    }

    @Override
    public PrijavaTurnir fetch(Long IDPrijava) {
        return prijavaTurnirRepository.findById(IDPrijava).orElseThrow(
                () -> new EntityMissingException(PrijavaTurnir.class, IDPrijava)
        );
    }

    @Override
    public PrijavaTurnir updateStatusPrijava(Long IDPrijava, String statusPrijava) {
        PrijavaTurnir prijavaTurnir = fetch(IDPrijava);
        prijavaTurnir.setStatusPrijava(statusPrijava);
        return prijavaTurnirRepository.save(prijavaTurnir);
    }
}

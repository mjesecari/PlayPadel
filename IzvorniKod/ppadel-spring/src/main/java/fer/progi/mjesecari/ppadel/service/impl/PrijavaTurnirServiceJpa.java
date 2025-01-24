package fer.progi.mjesecari.ppadel.service.impl;

import fer.progi.mjesecari.ppadel.dao.PrijavaTurnirRepository;
import fer.progi.mjesecari.ppadel.dao.TurnirRepository;
import fer.progi.mjesecari.ppadel.domain.PrijavaTurnir;
import fer.progi.mjesecari.ppadel.domain.Turnir;
import fer.progi.mjesecari.ppadel.service.PrijavaTurnirService;
import fer.progi.mjesecari.ppadel.service.exception.EntityMissingException;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PrijavaTurnirServiceJpa implements PrijavaTurnirService {
    @Autowired
    PrijavaTurnirRepository prijavaTurnirRepository;
    @Autowired
    TurnirRepository turnirRepository;
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

    @Override
    public PrijavaTurnir getByIDgracandIdturnir(Long idIgrac, Long idTurnir) {
        return prijavaTurnirRepository.findByigracandturnir(idIgrac,idTurnir);
    }

    @Override
    public List<Turnir> getAllForAplying(Long idIgrac,Float cijenaKotizacijeMin, Float cijenaKotizacijeMax, Float nagradeMin, Float nagradeMax) {
        List<Turnir> turniri =  turnirRepository.findAllTurnirForAplying(idIgrac, LocalDateTime.now());
        List<Turnir> filterTurniri = new ArrayList<>();
        for (Turnir t : turniri){
            if (t.getCijenaKotizacije() <= cijenaKotizacijeMax && t.getCijenaKotizacije() >= cijenaKotizacijeMin
            && Collections.min(t.getNagrade()) >= nagradeMin && Collections.max(t.getNagrade()) <= nagradeMax){
                filterTurniri.add(t);
            }
        }
        return filterTurniri;
    }

    @Override
    public List<Turnir> getAllWithStatusPrijava(Long idIgrac, String status) {
        return prijavaTurnirRepository.findAllTurnirStatus(idIgrac,status, LocalDateTime.now());
    }

    @Override
    public List<Turnir> getAllPlayed(Long idIgrac) {
        return prijavaTurnirRepository.findAllPlayedTurnirs(idIgrac,LocalDateTime.now());
    }

    @Override
    public List<String> getAllEmails(Long idTurnir) {
        return prijavaTurnirRepository.findAllEmailsForNotifications(idTurnir);
    }
}

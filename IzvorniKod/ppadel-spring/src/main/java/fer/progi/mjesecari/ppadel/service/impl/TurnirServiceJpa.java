package fer.progi.mjesecari.ppadel.service.impl;

import fer.progi.mjesecari.ppadel.dao.TurnirRepository;
import fer.progi.mjesecari.ppadel.domain.Turnir;
import fer.progi.mjesecari.ppadel.service.TurnirService;
import fer.progi.mjesecari.ppadel.service.exception.EntityMissingException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TurnirServiceJpa implements TurnirService {

    @Autowired
    private TurnirRepository turnirRepository;

    @Override
    public List<Turnir> listAll() {
        return turnirRepository.findAll();
    }

    @Override
    public Optional<Turnir> findById(Long IDTurnir) {
        return turnirRepository.findById(IDTurnir);
    }

    @Override
    public Turnir fetch(Long IDTurnir) {
        return turnirRepository.findById(IDTurnir).orElseThrow(
                () -> new EntityMissingException(Turnir.class, IDTurnir)
        );
    }

    @Override
    public Turnir updateTurnirLokacija(Long IDTurnir, String LokacijaTurnir) {
        Turnir turnir = fetch(IDTurnir);
        turnir.setLokacijaTurnir(LokacijaTurnir);
        return turnirRepository.save(turnir);
    }

    @Override
    public Turnir updateTurnirNaziv(Long IDTurnir, String NazivTurnir) {
        Turnir turnir = fetch(IDTurnir);
        turnir.setNazivTurnir(NazivTurnir);
        return turnirRepository.save(turnir);
    }

    @Override
    public Turnir updateTurnirDatum(Long IDTurnir, Date DatumTurnir) {
        Turnir turnir = fetch(IDTurnir);
        turnir.setDatumTurnir(DatumTurnir);
        return turnirRepository.save(turnir);
    }

    @Override
    public Turnir updateTurnirNagrade(Long IDTurnir, List<String> Nagrade) {
        Turnir turnir = fetch(IDTurnir);
        turnir.setNagrade(Nagrade);
        return turnirRepository.save(turnir);
    }

    @Override
    public Turnir updateTurnirStatus(Long IDTurnir, String StatusTurnir) {
        Turnir turnir = fetch(IDTurnir);
        turnir.setStatusTurnir(StatusTurnir);
        return turnirRepository.save(turnir);
    }

    @Override
    public Turnir deleteTurnir(Long IDTurnir) {
        Turnir turnir = fetch(IDTurnir);
        turnirRepository.deleteById(IDTurnir);
        return turnir;
    }
}

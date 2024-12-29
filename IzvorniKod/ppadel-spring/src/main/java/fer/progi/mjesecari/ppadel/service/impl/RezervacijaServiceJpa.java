package fer.progi.mjesecari.ppadel.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fer.progi.mjesecari.ppadel.dao.RezervacijaRepository;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.domain.Rezervacija;
import fer.progi.mjesecari.ppadel.domain.RezervacijaId;
import fer.progi.mjesecari.ppadel.domain.Teren;
import fer.progi.mjesecari.ppadel.service.RezervacijaService;
import fer.progi.mjesecari.ppadel.service.exception.OverlappingRangeException;

@Service
public class RezervacijaServiceJpa implements RezervacijaService {

    @Autowired
    private RezervacijaRepository rezervacijaRepository;

    @Override
    public Rezervacija addRezervacija(Teren teren, LocalDateTime dateTime, Korisnik korisnik) {
        
        // check for overlaps
        List<Rezervacija> overlapping = fetchForTerenBetween(teren, dateTime.minusHours(1), dateTime);
        Rezervacija rezervacija = new Rezervacija(teren, dateTime, korisnik);

        if(overlapping.size() != 0)
            throw new OverlappingRangeException(rezervacija, overlapping.get(0));

        return rezervacijaRepository.save(rezervacija);
        
    }

    @Override
    public Rezervacija deleteRezervacija(RezervacijaId rezervacijaId) {

        Rezervacija rezervacija = rezervacijaRepository.findByZaTerenAndVrijeme(rezervacijaId.getZaTeren(), rezervacijaId.getVrijeme());
        rezervacijaRepository.delete(rezervacija);
        return rezervacija;

    }

    @Override
    public List<Rezervacija> fetchForTerenBetween(Teren teren, LocalDateTime t1, LocalDateTime t2) {
        return rezervacijaRepository.findByZaTerenIDTerenAndVrijemeBetween(teren.getIDTeren(), t1, t2);
    }

    @Override
    public List<Rezervacija> fetchAll() {
        return rezervacijaRepository.findAll();
    }
    
}

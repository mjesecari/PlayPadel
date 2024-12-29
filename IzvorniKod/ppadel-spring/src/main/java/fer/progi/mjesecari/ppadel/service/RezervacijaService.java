package fer.progi.mjesecari.ppadel.service;

import java.time.LocalDateTime;
import java.util.List;

import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.domain.Rezervacija;
import fer.progi.mjesecari.ppadel.domain.RezervacijaId;
import fer.progi.mjesecari.ppadel.domain.Teren;

/**
 * Manages rezervacija database.
 * @see Rezervacija
 * @author Dino Plečko dino.plecko@fer.unizg.hr
 */
public interface RezervacijaService {

    Rezervacija addRezervacija(Teren teren, LocalDateTime dateTime, Korisnik korisnik);
    Rezervacija deleteRezervacija(RezervacijaId rezervacijaId);
    List<Rezervacija> fetchForTerenBetween(Teren teren, LocalDateTime t1, LocalDateTime t2);
    List<Rezervacija> fetchAll();
}
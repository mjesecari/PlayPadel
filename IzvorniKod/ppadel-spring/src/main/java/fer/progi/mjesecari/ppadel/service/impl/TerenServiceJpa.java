package fer.progi.mjesecari.ppadel.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fer.progi.mjesecari.ppadel.dao.TerenRepository;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.domain.Teren;
import fer.progi.mjesecari.ppadel.service.EntityMissingException;
import fer.progi.mjesecari.ppadel.service.KorisnikService;
import fer.progi.mjesecari.ppadel.service.RequestDeniedException;
import fer.progi.mjesecari.ppadel.service.TerenService;

@Service
public class TerenServiceJpa implements TerenService{

    @Autowired
    private TerenRepository terenRepo;

    @Autowired
    private KorisnikService korisnikService;


    @Override
    public List<Teren> listAll() {
        return terenRepo.findAll();
    }


    @Override
    public Optional<Teren> findById(long terenId) {
        return terenRepo.findById(terenId);
    }

    @Override
    public Teren fetch(long terenId) {
        return findById(terenId).orElseThrow(
            () -> new EntityMissingException(Teren.class, terenId)
        );
    }

    @Override
    public Teren createTeren(String terenName, Long vlasnikID, String terenTip) {
        Korisnik vlasnik = korisnikService.findById(vlasnikID).orElseThrow(
            () -> new RequestDeniedException("No user with ID " + vlasnikID)
        );

        return terenRepo.save(new Teren(terenName, vlasnik, terenTip));

    }

    @Override
    public Teren createTeren(String terenName, Long vlasnikID, String terenTip, String lokacija) {
        Korisnik vlasnik = korisnikService.findById(vlasnikID).orElseThrow(
            () -> new RequestDeniedException("No user with ID " + vlasnikID)
        );
        Teren t = new Teren(terenName, vlasnik, terenTip);
        t.setLokacijaTeren(lokacija);
        return terenRepo.save(t);
    }

    @Override
    public Teren updateTerenName(long terenId, String name) {
        Teren teren = fetch(terenId);
        teren.setNazivTeren(name);
        return terenRepo.save(teren);
    }
    
    @Override
    public Teren updateTerenType(long terenId, String type) {
        Teren teren = fetch(terenId);
        teren.setTipTeren(type);
        return terenRepo.save(teren);
    }

    @Override
    public Teren deleteTeren(long terenId) {
        Teren teren = fetch(terenId);   // throws EntityMissingException
        terenRepo.delete(teren);
        return teren;
    }


}

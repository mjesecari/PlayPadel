package fer.progi.mjesecari.ppadel.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import fer.progi.mjesecari.ppadel.dao.UserRepository;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.EntityMissingException;
import fer.progi.mjesecari.ppadel.service.KorisnikService;
import fer.progi.mjesecari.ppadel.service.RequestDeniedException;


@Service
public class KorisnikServiceJpa implements KorisnikService {
    
    private static final String EMAIL_FORMAT = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Autowired
    private UserRepository userRepo;

    @Override 
    public List<Korisnik> listAll(){
        return userRepo.findAll();
    }

    @Override
    public Optional<Korisnik> findById(long korisnikId) {
      return userRepo.findById(korisnikId);
    }
  
    @Override
    public Optional<Korisnik> findByEmail(String email) {
        Assert.notNull(email, "Email must be given");
        return userRepo.findByEmail(email);
    }

    @Override
    public Korisnik fetch(long korisnikId) {
      return findById(korisnikId).orElseThrow(
        () -> new EntityMissingException(Korisnik.class, korisnikId)
      );
    }
  
    @Override
    public Korisnik createKorisnik(Korisnik korisnik) {
      validate(korisnik);
      Assert.isNull(korisnik.getId(),
        "User ID must be null, not: " + korisnik.getId()
      );
      if (userRepo.existsByEmail(korisnik.getEmail()))
        throw new RequestDeniedException(
          "User with email " + korisnik.getEmail() + " already exists"
        );
      
      
      
      return userRepo.save(korisnik);
    }
  
    @Override
    public Korisnik updateKorisnik(Korisnik korisnik) {
      validate(korisnik);
      Long korisnikId = korisnik.getId();
      if (!userRepo.existsById(korisnikId))
        throw new EntityMissingException(Korisnik.class, korisnikId);
      if (userRepo.existsByEmailAndIdNot(korisnik.getEmail(), korisnikId))
        throw new RequestDeniedException(
          "User with email " + korisnik.getEmail() + " already exists"
        );
      return userRepo.save(korisnik);
    }
  
    @Override
    public Korisnik deleteKorisnik(long korisnikId) {
        Korisnik korisnik = fetch(korisnikId);
        userRepo.delete(korisnik);
      return korisnik;
    }
  
    private void validate(Korisnik korisnik) {
        Assert.notNull(korisnik, "User object must be given");
        String email = korisnik.getEmail();
        Assert.hasText(email, "email must be given");
        Assert.isTrue(email.matches(EMAIL_FORMAT),
          "email '" + email + "' not valid");
      }
    
}

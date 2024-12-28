package fer.progi.mjesecari.ppadel.service.impl;

import fer.progi.mjesecari.ppadel.dao.AdminRepository;
import fer.progi.mjesecari.ppadel.dao.UserRepository;
import fer.progi.mjesecari.ppadel.domain.Administrator;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.AdminService;
import fer.progi.mjesecari.ppadel.service.EntityMissingException;
import fer.progi.mjesecari.ppadel.service.KorisnikService;
import fer.progi.mjesecari.ppadel.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceJpa implements AdminService {
    private static final String EMAIL_FORMAT = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private KorisnikService korisnikService;

    @Override
    public List<Korisnik> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Korisnik addUser(Korisnik korisnik) {
        /**validate(korisnik);
        Assert.isNull(korisnik.getId(),
                "User ID must be null, not: " + korisnik.getId()
        );
        if (userRepo.existsByEmail(korisnik.getEmail()))
            throw new RequestDeniedException(
                    "User with email " + korisnik.getEmail() + " already exists"
            );

        return userRepo.save(korisnik);**/
        return korisnikService.createKorisnik(korisnik);
    }

    @Override
    public Korisnik deleteUser(long id) {
        /**Korisnik korisnik = fetch(id);
        userRepo.delete(korisnik);
        return korisnik;**/
        return korisnikService.deleteKorisnik(id);
    }

    @Override
    public List<Korisnik> getUsersByType(String type) {
        return getAllUsers()
                .stream()
                .filter(user -> type.equals(user.getTip()))
                .collect(Collectors.toList());
    }

    @Override
    public Korisnik fetch(long korisnikId) {
        return findById(korisnikId).orElseThrow(
                () -> new EntityMissingException(Korisnik.class, korisnikId)
        );
    }

    @Override
    public Optional<Korisnik> findById(long korisnikId) {
        return userRepo.findById(korisnikId);

    }

    @Override
    public Administrator updateClanarina(long id, Float novaCijenaClanarine) {
        Administrator admin = adminRepo.findAdministratorById(id);
        if (admin == null){
            throw new EntityMissingException(Administrator.class, id);
        }
        admin.setCijenaClanarine(novaCijenaClanarine);
        return adminRepo.save(admin);
    }


    private void validate(Korisnik korisnik) {
        Assert.notNull(korisnik, "User object must be given");
        String email = korisnik.getEmail();
        Assert.hasText(email, "email must be given");
        Assert.isTrue(email.matches(EMAIL_FORMAT),
                "email '" + email + "' not valid");
    }
}

package fer.progi.mjesecari.ppadel.api;

import fer.progi.mjesecari.ppadel.dao.IgracRepository;
import fer.progi.mjesecari.ppadel.dao.UserRepository;
import fer.progi.mjesecari.ppadel.dao.VlasnikRepository;
import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.EntityMissingException;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/korisnik")
public class KorisnkiDetailController {

    @Autowired
    private IgracRepository igracRepository;

    @Autowired
    private VlasnikRepository vlasnikRepository;

    @GetMapping("/igrac")
    public Igrac getIgrac(@RequestBody String email) {
        return igracRepository.findByEmail(email).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find user email\n")
            );
    }

    @GetMapping("/vlasnik")
    public Vlasnik getVlasnik(@RequestBody String email) {
        return vlasnikRepository.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find user email\n")
        );
    }
}

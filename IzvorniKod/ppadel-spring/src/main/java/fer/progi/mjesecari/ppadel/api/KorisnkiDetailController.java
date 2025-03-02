package fer.progi.mjesecari.ppadel.api;

import fer.progi.mjesecari.ppadel.dao.IgracRepository;
import fer.progi.mjesecari.ppadel.dao.VlasnikRepository;
import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import fer.progi.mjesecari.ppadel.service.IgracService;
import fer.progi.mjesecari.ppadel.service.VlasnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping("/korisnik")
public class KorisnkiDetailController {

    @Autowired
    private IgracRepository igracRepository;

    @Autowired
    private IgracService igracService;

    @Autowired
    private VlasnikRepository vlasnikRepository;

    @Autowired
    private VlasnikService vlasnikService;

    @GetMapping ("igrac/{email}")
    public Igrac activeIgrac(@PathVariable String email) {
            return igracRepository.findByEmail(email).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find user email\n")
            );
    }

    @DeleteMapping ("/igrac/{id}")
    public void deleteIgrac(@PathVariable Long id) {
        if (!igracRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find user by id\n");
        }
        else {
            igracService.deleteIgrac(id);
        }
    }

    @GetMapping ("/vlasnik/{email}")
    public Vlasnik getVlasnik(@PathVariable String email) {
        return vlasnikRepository.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find user email\n")
        );
    }

    @DeleteMapping ("/vlasnik/{id}")
    public void removeVlasnik (@PathVariable Long id) {
        if (!vlasnikRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find user by id\n");
        }
        else {
            vlasnikService.deleteVlasnik(id);
        }
    }

}

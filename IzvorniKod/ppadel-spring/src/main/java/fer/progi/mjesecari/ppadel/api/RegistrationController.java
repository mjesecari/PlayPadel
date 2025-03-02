package fer.progi.mjesecari.ppadel.api;

import java.net.URI;

import fer.progi.mjesecari.ppadel.api.dto.IgracDTO;
import fer.progi.mjesecari.ppadel.api.dto.KorisnikDTO;
import fer.progi.mjesecari.ppadel.api.dto.VlasnikDTO;
import fer.progi.mjesecari.ppadel.domain.Administrator;
import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import fer.progi.mjesecari.ppadel.service.IgracService;
import fer.progi.mjesecari.ppadel.service.VlasnikService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.KorisnikService;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private KorisnikService userService;

    @Autowired
    private IgracService igracService;

    @Autowired
    private VlasnikService vlasnikService;

    @PostMapping ("/igrac")
    public ResponseEntity<Igrac> makeIgrac(@RequestBody IgracDTO igracDTO) {
        if (igracDTO.getRole().equals("igrač")) {
            Igrac igrac = new Igrac();
            igrac.setTip(igracDTO.getRole());
            igrac.setEmail(igracDTO.getEmail());
            igrac.setImeIgrac(igracDTO.getImeIgrac());
            igrac.setPrezimeIgrac(igracDTO.getPrezimeIgrac());
            igrac.setBrojTel(igracDTO.getBrojTel());
            System.out.println("saved");
            Igrac saved = igracService.createIgrac(igrac);
            return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(saved);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authenticated with oauth2\n");
        }
    }

    // TODO: change to postmapping
    @PostMapping ("/vlasnik")
    public ResponseEntity<Vlasnik> makeVlasnik(@RequestBody VlasnikDTO roleVlasnik) {
        log.info("vlasnik");
        if (roleVlasnik.getRole().equals("vlasnik")) {
            Vlasnik vlasnik = new Vlasnik();
            vlasnik.setEmail(roleVlasnik.getEmail());
            vlasnik.setNazivVlasnik(roleVlasnik.getNazivVlasnik());
            vlasnik.setLokacija(roleVlasnik.getLokacija());
            vlasnik.setBrojTel(roleVlasnik.getBrojTel());
            vlasnik.setTip(roleVlasnik.getRole());
            Vlasnik saved = vlasnikService.createVlasnik(vlasnik);

            return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(saved);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authenticated with oauth2\n");
        }
    }

    @PostMapping
    public ResponseEntity<Korisnik> makeKorisnik(@RequestBody KorisnikDTO roleDTO){
        log.info(roleDTO.getRole());
        Korisnik newKorisnik = new Korisnik();
        newKorisnik.setEmail(roleDTO.getEmail());
        newKorisnik.setTip(roleDTO.getRole());
        Korisnik saved = userService.createKorisnik(newKorisnik);
        if(roleDTO.getRole().equals("admin")){
            Administrator newAdministrator = new Administrator();

        }
        //TODO add if() user has role vlasnik or igrac -> new Vlasnik()/new Igrac()
        //oidcUser.getAuthorities().add(new SimpleGrantedAuthority("ROLE_OWNER"));
        return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(saved);
    }

}
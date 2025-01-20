package fer.progi.mjesecari.ppadel.api;

import fer.progi.mjesecari.ppadel.api.dto.PrijavaTurnirDTO;
import fer.progi.mjesecari.ppadel.dao.IgracRepository;
import fer.progi.mjesecari.ppadel.dao.PrijavaTurnirRepository;
import fer.progi.mjesecari.ppadel.dao.TurnirRepository;
import fer.progi.mjesecari.ppadel.domain.PrijavaTurnir;
import fer.progi.mjesecari.ppadel.domain.Turnir;
import fer.progi.mjesecari.ppadel.service.IgracService;
import fer.progi.mjesecari.ppadel.service.PrijavaTurnirService;
import fer.progi.mjesecari.ppadel.service.TurnirService;
import fer.progi.mjesecari.ppadel.service.VlasnikService;
import fer.progi.mjesecari.ppadel.service.exception.EntityMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Map;

@Profile({"form-security", "oauth-security"})
@RestController()
@RequestMapping("/prijavaTurnir")
public class PrijavaTurnirController {
    @Autowired
    private PrijavaTurnirService prijavaTurnirService;
    @Autowired
    private PrijavaTurnirRepository prijavaTurnirRepository;
    @Autowired
    private TurnirService turnirService;
    @Autowired
    private TurnirRepository turnirRepository;
    @Autowired
    private IgracService igracService;
    @Autowired
    private IgracRepository igracRepository;
    @Autowired
    private VlasnikService vlasnikService;

    @PostMapping("/")
    private PrijavaTurnir createPrijavaTurnir(@RequestBody PrijavaTurnirDTO prijavaTurnirDTO, Principal principal) {
        String mail = mailFromPrincipal(principal);
        if (mail == null || !igracService.fetch(prijavaTurnirDTO.getIDKorisnik()).getEmail().equals(mail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        validate(prijavaTurnirDTO);
        PrijavaTurnir prijavaTurnir = new PrijavaTurnir();
        prijavaTurnir.setIgrac(igracService.fetch(prijavaTurnirDTO.getIDKorisnik()));
        prijavaTurnir.setTurnir(turnirService.fetch(prijavaTurnirDTO.getIDTurnir()));
        prijavaTurnir.setStatusPrijava(prijavaTurnirDTO.getStatusPrijava());
        return prijavaTurnirRepository.save(prijavaTurnir);
    }

    @PostAuthorize("hasRole('ROLE_OWNER')")
    @PutMapping("/{id}")
    private PrijavaTurnir updatePrijavaTurnir(@PathVariable Long IDPrijava, @RequestBody PrijavaTurnirDTO prijavaTurnirDTO, Principal principal) {
        String mail = mailFromPrincipal(principal);
        if (mail == null
                || turnirService.fetch(prijavaTurnirDTO.getIDTurnir()).getVlasnik().getEmail().equals(mail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        validate(prijavaTurnirDTO);
        PrijavaTurnir prijavaTurnir = prijavaTurnirService.fetch(IDPrijava);
        prijavaTurnir.setStatusPrijava(prijavaTurnirDTO.getStatusPrijava());
        return prijavaTurnirRepository.save(prijavaTurnir);
    }

    @DeleteMapping("/vlasnik/{id}")
    private PrijavaTurnir deletePrijavaTurnirVlasnik (@PathVariable Long IDPrijava, Principal principal) {
        String mail = mailFromPrincipal(principal);
        String mailVlasnik = prijavaTurnirService.fetch(IDPrijava).getTurnir().getVlasnik().getEmail();
        if (mail == null
                || !mail.equals(mailVlasnik)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        PrijavaTurnir prijavaTurnir = prijavaTurnirService.fetch(IDPrijava);
        prijavaTurnirRepository.deleteById(IDPrijava);
        return prijavaTurnir;
    }

    @DeleteMapping("/igrac/{id}")
    private PrijavaTurnir deletePrijavaTurnirIgrac (@PathVariable Long IDPrijava, Principal principal) {
        String mail = mailFromPrincipal(principal);
        String mailIgrac = prijavaTurnirService.fetch(IDPrijava).getIgrac().getEmail();
        if (mail == null
                || !mail.equals(mailIgrac)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        PrijavaTurnir prijavaTurnir = prijavaTurnirService.fetch(IDPrijava);
        prijavaTurnirRepository.deleteById(IDPrijava);
        return prijavaTurnir;
    }

    private String mailFromPrincipal(Principal principal){
        if( OAuth2AuthenticationToken.class.isInstance(principal) ){
            Map<String, Object> attributes = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes();
            return (String) attributes.get("email");
        }
        else {
            return null;
        }
    }
    private void validate(PrijavaTurnirDTO prijavaTurnirDTO) {
        Assert.notNull(prijavaTurnirDTO, "Prijava must be given");
        Assert.notNull(igracService.fetch(prijavaTurnirDTO.getIDKorisnik()), "Igrac must be given");
        Assert.notNull(turnirService.fetch(prijavaTurnirDTO.getIDTurnir()), "Turnir must be given");
        Assert.hasText(prijavaTurnirDTO.getStatusPrijava(), "Status must be given");
    }
}

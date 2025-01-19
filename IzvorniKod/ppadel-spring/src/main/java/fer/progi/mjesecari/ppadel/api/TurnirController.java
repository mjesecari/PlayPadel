package fer.progi.mjesecari.ppadel.api;

import fer.progi.mjesecari.ppadel.api.dto.TurnirDTO;
import fer.progi.mjesecari.ppadel.dao.TurnirRepository;
import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Turnir;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import fer.progi.mjesecari.ppadel.service.TurnirService;
import fer.progi.mjesecari.ppadel.service.VlasnikService;
import fer.progi.mjesecari.ppadel.service.exception.EntityMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Profile({"form-security", "oauth-security"})
@RestController()
@RequestMapping("/turnir")
public class TurnirController {
    @Autowired
    private TurnirRepository turnirRepository;
    @Autowired
    private TurnirService turnirService;

    @Autowired
    private VlasnikService vlasnikService;

    @GetMapping("/")
    private String turnir(){
        return "Turnir";
    }

    @GetMapping("/{id}")
    private List<Turnir> getAllTurnir(Long IDKorisnik, Principal principal) {
        String mail = mailFromPrincipal(principal);
        if (mail == null || !vlasnikService.fetch(IDKorisnik).getEmail().equals(mail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        else {

            return turnirRepository.findByVlasnik(vlasnikService.fetch(IDKorisnik));
        }
    }

    @GetMapping("/status/{id}")
    private String getStatus(Long IDTurnir) {
        Turnir turnir = turnirRepository.findById(IDTurnir).orElseThrow(
                () -> new EntityMissingException(Turnir.class, IDTurnir));
        return turnir.getStatusTurnir();
    }

    /*@GetMapping("/cekanje/{id}")
    private List<Igrac> getNaCekanju (Long IDTurnir) {
        return turnirRepository.findIgracByIDTurnirAndStatus(IDTurnir);
    }*/

    @PostMapping("/")
    private Turnir createTurnir (@RequestBody TurnirDTO turnirDTO) {
        Turnir turnir = new Turnir();
        validate(turnir);
        turnir.setVlasnik(turnirDTO.getVlasnik());
        turnir.setNazivTurnir(turnirDTO.getNazivTurnir());
        turnir.setLokacijaTurnir(turnirDTO.getLokacijaTurnir());
        turnir.setDatumTurnir(turnirDTO.getDatumTurnir());
        turnir.setNagrade(turnirDTO.getNagrade());
        turnir.setStatusTurnir(turnir.getStatusTurnir());
        Turnir saved = turnirRepository.save(turnir);
        return saved;
    }

    @DeleteMapping("/{id}")
    private Turnir deleteTurnir (@PathVariable Long IDTurnir, Principal principal) {
        if (mailFromPrincipal(principal) != null && mailFromPrincipal(principal).equals(turnirService.fetch(IDTurnir).getVlasnik().getEmail()))
            if (turnirRepository.existsById(IDTurnir)) {
                Turnir turnir = turnirService.fetch(IDTurnir);
                turnirRepository.deleteById(IDTurnir);
                return turnir;
            }
            else {
                throw new EntityMissingException(Turnir.class, "Tournament not found");
            }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
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

    private void validate(Turnir turnir) {
        Assert.notNull(turnir, "Tournament object must be given");
        Vlasnik vlasnik = turnir.getVlasnik();
        Assert.notNull(vlasnik, "Vlasnik object must be given");
        Assert.notNull(turnir.getDatumTurnir(), "Date must be given");
        Assert.hasText(turnir.getLokacijaTurnir(), "Location must be given");
        Assert.hasText(turnir.getStatusTurnir(), "Status must be given");
        Assert.hasText(turnir.getNazivTurnir(), "Name of tournament must be given");
    }
}

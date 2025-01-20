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
import org.yaml.snakeyaml.events.Event;

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
    private List<Turnir> turnir(){
        return turnirRepository.findAll();
    }


    @GetMapping("/detalji/{id}")
    public Turnir getTurnirDetails (@PathVariable Long id, Principal principal){
        String mail = mailFromPrincipal(principal);
        if (mail == null || !turnirService.fetch(id).getVlasnik().getEmail().equals(mail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        else {
            System.out.println(turnirService.fetch(id));
            return turnirService.fetch(id);
        }
    }
    @GetMapping("/vlasnik/{id}")
    public List<Turnir> getAllTurnir(@PathVariable Long id, Principal principal) {
        String mail = mailFromPrincipal(principal);
        if (mail == null || !vlasnikService.fetch(id).getEmail().equals(mail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        else {
            System.out.println(turnirRepository.findByVlasnik(vlasnikService.fetch(id)));
            return turnirRepository.findByVlasnik(vlasnikService.fetch(id));
        }
    }

    @GetMapping ("/igrac/{id}")
    public Turnir getAllTurnirIgracc (@PathVariable Long IDTurnir, Principal principal) {
        if (mailFromPrincipal(principal) == null ) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!turnirRepository.existsById(IDTurnir)){
            throw new EntityMissingException(Turnir.class, "Entity not found");
        }
        return turnirService.fetch(IDTurnir);
    }

    /**@GetMapping("/status/{id}")
    private String getStatus(Long IDTurnir) {
        Turnir turnir = turnirRepository.findById(IDTurnir).orElseThrow(
                () -> new EntityMissingException(Turnir.class, IDTurnir));
        return turnir.getStatusTurnir();
    }**/

    @GetMapping("/statusIgraca/{IDTurnir}/{status}")
    public List<Igrac> getSaStatusom (@PathVariable Long IDTurnir, @PathVariable String status, Principal principal) {
        String mail = mailFromPrincipal(principal);
        if (mail == null || !turnirService.fetch(IDTurnir).getVlasnik().getEmail().equals(mail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return turnirRepository.findIgracByIDTurnirAndStatus(IDTurnir, status);
    }

    @PutMapping("/{IDTurnir}")
    public Turnir updateTurnir (@PathVariable Long IDTurnir, @RequestBody TurnirDTO turnirDTO, Principal principal) {
        if (mailFromPrincipal(principal) != null && mailFromPrincipal(principal).equals(turnirService.fetch(IDTurnir).getVlasnik().getEmail()))
            if (turnirRepository.existsById(IDTurnir)) {
                turnirService.updateTurnirLokacija(IDTurnir, turnirDTO.getLokacijaTurnir());
                turnirService.updateTurnirNaziv(IDTurnir, turnirDTO.getNazivTurnir());
                turnirService.updateTurnirDatum(IDTurnir, turnirDTO.getDatumTurnir());
                turnirService.updateTurnirNagrade(IDTurnir, turnirDTO.getNagrade());
                turnirService.updateTurnirCijenaKotizacije(IDTurnir, turnirDTO.getCijenaKotizacije());
                turnirService.updateOpis(IDTurnir, turnirDTO.getOpis());
                Turnir turnir = turnirService.fetch(IDTurnir);
                validate(turnir);
                System.out.println(turnir);
                return turnir;
            }
            else {
                throw new EntityMissingException(Turnir.class, "Tournament not found");
            }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/")
    public Turnir createTurnir (@RequestBody TurnirDTO turnirDTO, Principal principal) {
        String mail = mailFromPrincipal(principal);
        if (mail == null || !vlasnikService.fetch(turnirDTO.getVlasnik()).getEmail().equals(mail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Turnir turnir = new Turnir();
        System.out.println(turnirDTO.getVlasnik());

        Vlasnik vlasnik = vlasnikService.fetch(turnirDTO.getVlasnik());
        System.out.println(vlasnik);
        turnir.setVlasnik(vlasnik);
        turnir.setNazivTurnir(turnirDTO.getNazivTurnir());
        turnir.setLokacijaTurnir(turnirDTO.getLokacijaTurnir());
        turnir.setDatumTurnir(turnirDTO.getDatumTurnir());
        turnir.setNagrade(turnirDTO.getNagrade());
        turnir.setCijenaKotizacije(turnirDTO.getCijenaKotizacije());
        turnir.setOpis(turnirDTO.getOpis());
        Turnir saved = turnirRepository.save(turnir);
        validate(saved);
        return saved;
    }

    @DeleteMapping("/{id}")
    public Turnir deleteTurnir (@PathVariable Long id, Principal principal) {
        if (mailFromPrincipal(principal) != null && mailFromPrincipal(principal).equals(turnirService.fetch(id).getVlasnik().getEmail()))
            if (turnirRepository.existsById(id)) {
                Turnir turnir = turnirService.fetch(id);
                turnirRepository.deleteById(id);
                return turnir;
            }
            else {
                throw new EntityMissingException(Turnir.class, "Tournament not found");
            }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public String mailFromPrincipal(Principal principal){
        if( OAuth2AuthenticationToken.class.isInstance(principal) ){
            Map<String, Object> attributes = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes();
            return (String) attributes.get("email");
        }
        else {
            return null;
        }
    }

    public void validate(Turnir turnir) {
        Assert.notNull(turnir, "Tournament object must be given");
        Vlasnik vlasnik = turnir.getVlasnik();
        Assert.notNull(vlasnik, "Vlasnik object must be given");
        Assert.notNull(turnir.getDatumTurnir(), "Date must be given");
        Assert.hasText(turnir.getLokacijaTurnir(), "Location must be given");
        Assert.hasText(turnir.getNazivTurnir(), "Name of tournament must be given");
    }
}

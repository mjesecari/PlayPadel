package fer.progi.mjesecari.ppadel.api;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.Events;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import fer.progi.mjesecari.ppadel.api.dto.RezervacijaDTO;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.domain.Rezervacija;
import fer.progi.mjesecari.ppadel.domain.RezervacijaId;
import fer.progi.mjesecari.ppadel.domain.Teren;
import fer.progi.mjesecari.ppadel.service.KorisnikService;
import fer.progi.mjesecari.ppadel.service.RezervacijaService;
import fer.progi.mjesecari.ppadel.service.TerenService;
import fer.progi.mjesecari.ppadel.api.exception.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Profile({"form-security", "oauth-security"})
@RestController()
@RequestMapping("/rezervacije")
public class RezervacijaController {
    @Autowired
    RezervacijaService rezervacijaService;

    @Autowired
    TerenService terenService;

    @Autowired
    KorisnikService KorisnikService;

    @Autowired
    private OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    private static final com.google.api.client.json.JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    
    @Value("progi.google.application-name")
    private static String APPLICATION_NAME;

    @GetMapping("")
    public List<Rezervacija> getAll(@RequestParam(required = false) Long terenId) {
        if(terenId == null){
            return rezervacijaService.fetchAll();
        }
        // else
        return rezervacijaService.fetchAllForTeren(terenId);
        
        
    }
    
    @PostMapping("")
    public Rezervacija newRezervacija(@RequestBody RezervacijaDTO rezDTO) {
        
        Teren teren = terenService.findById(rezDTO.getTerenId()).orElseThrow(()-> {
            return new ResourceNotFoundException("Nema terena s id " + rezDTO.getTerenId());
        });

        Korisnik korisnik = KorisnikService.findByEmail(rezDTO.getKorisnikEmail()).orElseThrow(()->{
            return new ResourceNotFoundException("Nema korisnika s emailom " + rezDTO.getKorisnikEmail());
        });
        
        return rezervacijaService.addRezervacija(teren, rezDTO.getVrijeme(), korisnik);
        
    }
    @PostMapping("/save")
    public Rezervacija saveRezervacija(@RequestBody RezervacijaDTO rezDTO) {
        
        com.google.api.services.calendar.model.Events eventList;
        String message;
        Rezervacija rezervacija;
        try{
            rezervacija = rezervacijaService.fetch(rezDTO.getTerenId(), rezDTO.getVrijeme());
        }
        catch(Exception e){
            throw new ResourceNotFoundException("Ne postoji rezervacija");
        }


        try {
            GoogleCredential credential = new GoogleCredential().setAccessToken(getAccessToken().getTokenValue());
            
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME).build();

            Event event = new Event()
                .setSummary("Rezervacija za teren " + rezervacija.getZaTeren().getNazivTeren())
                //.setLocation("800 Howard St., San Francisco, CA 94103")
                //.setDescription("A chance to hear more about Google's developer products.");

            event.setStart(new EventDateTime().setDateTime(rezervacija.getVrijeme().atz));

            DateTime endDateTime = new DateTime("2015-05-28T17:00:00-07:00");
            EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
            event.setEnd(end);


            Events events = service.events();
            events.insert("primary", null)
        } catch (Exception e) {
            
            message = "Exception while handling OAuth2 callback (" + e.getMessage() + ")."
            + " Redirecting to google connection status page.";
        }
        
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    
    @DeleteMapping("")
    public Rezervacija deleteRezervacija(@RequestParam Long terenId, @RequestParam LocalDateTime startTime){
        return rezervacijaService.deleteRezervacija(new RezervacijaId(terenService.fetch(terenId), startTime));
    }
    
    private OAuth2AccessToken getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken token) {
            OAuth2AuthorizeRequest authRequest = OAuth2AuthorizeRequest
                    .withClientRegistrationId(token.getAuthorizedClientRegistrationId())
                    .principal(token)
                    .build();
            OAuth2AuthorizedClient client = oAuth2AuthorizedClientManager.authorize(authRequest);
            OAuth2AccessToken accessToken = client.getAccessToken();
            System.out.println("Token raw value: " + accessToken.getTokenValue());
            System.out.println("Token scopes: " + accessToken.getScopes());
            return accessToken;
        }
        throw new IllegalStateException("Oauth2 Security Context not found!");
    }

}

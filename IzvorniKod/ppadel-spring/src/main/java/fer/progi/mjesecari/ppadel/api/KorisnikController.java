package fer.progi.mjesecari.ppadel.api;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fer.progi.mjesecari.ppadel.dao.UserRepository;
import fer.progi.mjesecari.ppadel.domain.Korisnik;

@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping("/user")
public class KorisnikController {
    
    @Autowired
    UserRepository userRepo;

    @GetMapping
    public Korisnik activeUser(Principal principal){
         if( OAuth2AuthenticationToken.class.isInstance(principal) ){
            Map<String, Object> attributes = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes();
            return userRepo.findByEmail((String) attributes.get("email")).orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find user email\n")
            );
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authenticated with oauth2\n");
        }
    } 


}

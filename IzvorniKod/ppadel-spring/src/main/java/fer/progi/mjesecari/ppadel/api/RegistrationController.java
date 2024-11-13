package fer.progi.mjesecari.ppadel.api;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.KorisnikService;


@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private KorisnikService userService;

    private static class RoleDTO {
        private String role;
        private String email;
        
        
        public RoleDTO(@JsonProperty("role") String role, @JsonProperty("email") String email){
            this.role = role;
            this.email = email;
        }
        
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    // TODO: change to postmapping
    @PostMapping
    public ResponseEntity<Korisnik> setRole(@RequestBody RoleDTO roleDTO){
        // DefaultOidcUser oidcUser = (DefaultOidcUser)((OAuth2AuthenticationToken)principal).getPrincipal();
        // Map<String,Object> claims = oidcUser.getClaims();
        // String userEmail = (String)claims.get("email");
        
        Korisnik newKorisnik = new Korisnik();
        newKorisnik.setEmail(roleDTO.getEmail());
        newKorisnik.setTip(roleDTO.getRole());
        Korisnik saved = userService.createKorisnik(newKorisnik);

        //oidcUser.getAuthorities().add(new SimpleGrantedAuthority("ROLE_OWNER"));

        return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(saved);
    }
}



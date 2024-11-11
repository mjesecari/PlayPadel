package fer.progi.mjesecari.ppadel.api;

import java.security.Principal;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;

@Profile({"form-security", "oauth-security"})
@RestController
@PreAuthorize("hasRole('ROLE_oauth2')")
@RequestMapping("/register")
public class RegistrationController {
    // TODO: change to postmapping
    @GetMapping
    public String setRole(Principal principal, @RequestParam String role){
        Map<String,Object> claims = ((DefaultOidcUser)((OAuth2AuthenticationToken)principal).getPrincipal()).getClaims();
        String userEmail = (String)claims.get("email");
        
        //TODO: check database and insert user with privileges if does not exist



        return role;//auth.getName();


    }
}

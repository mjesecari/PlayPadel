package fer.progi.mjesecari.ppadel.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fer.progi.mjesecari.ppadel.dao.TerenRepository;
import fer.progi.mjesecari.ppadel.domain.Teren;
import fer.progi.mjesecari.ppadel.service.TerenService;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Profile({"form-security", "oauth-security"})
@RestController()
@RequestMapping("/tereni")
public class TerenController {
    
    @Autowired
    private TerenService terenService;

    @Autowired
    private TerenRepository terenRepo;



    @GetMapping("/")
    public Collection<Teren> getAll() {
        return terenService.listAll();
    }
    
    @PostAuthorize("hasRole('ROLE_OWNER')")
    @PostMapping("/")
    public ResponseEntity<Teren> createTeren(@RequestBody createTerenDTO terenDTO) {
        
        Teren saved = terenService.createTeren(terenDTO.getNaziv(), terenDTO.getVlasnikTerenaId(), terenDTO.getTip(), terenDTO.getLokacija());

        return ResponseEntity.created(URI.create("/tereni/" + saved.getIDTeren())).body(saved);
    }
    
    @PostAuthorize("hasRole('ROLE_OWNER')")
    @DeleteMapping("/{id}")
    public void removeTeren(Principal principal, @PathVariable Long id) {
        String mail = mailFromPrincipal(principal);
        if(mail == null || !terenService.fetch(id).getVlasnikTeren().getEmail().equals(mail)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        terenService.deleteTeren(id);
    }

    @GetMapping("/{id}")
    public Optional<Teren> getTeren(Principal principal, @PathVariable Long id) {
        return terenService.findById(id);
    }

    @PostAuthorize("hasRole('ROLE_OWNER')")
    @GetMapping("/my")
    public Collection<Teren> myTereni(Principal principal) {
        String mail = mailFromPrincipal(principal);
        if(mail != null)
            return terenRepo.findAllByVlasnikTerenEmail(mail);
        else
            return new ArrayList<Teren>();
    }
    
    @PostAuthorize("hasRole('ROLE_OWNER')")
    @PutMapping("/{id}")
    public Teren editTeren(Principal principal, @PathVariable Long id, @RequestBody createTerenDTO terenDto) {
        String mail = mailFromPrincipal(principal);
        
        if(mail == null || !terenService.fetch(id).getVlasnikTeren().getEmail().equals(mail)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        else{
            terenService.updateTerenName(id, terenDto.getNaziv());
            return terenService.updateTerenType(id, terenDto.getTip());
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
    


}

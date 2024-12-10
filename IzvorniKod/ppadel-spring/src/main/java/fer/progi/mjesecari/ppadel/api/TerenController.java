package fer.progi.mjesecari.ppadel.api;

import org.springframework.web.bind.annotation.RestController;

import fer.progi.mjesecari.ppadel.dao.TerenRepository;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.domain.Teren;
import fer.progi.mjesecari.ppadel.service.TerenService;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Profile({"form-security", "oauth-security"})
@RestController()
@RequestMapping("/tereni")
public class TerenController {
    
    @Autowired
    private TerenService terenService;

    @GetMapping("/")
    public Collection<Teren> getAll() {
        return terenService.listAll();
    }
    
    @PostMapping("/")
    public ResponseEntity<Teren> createTeren(@RequestBody createTerenDTO terenDTO) {
        
        Teren saved = terenService.createTeren(terenDTO.getNaziv(), terenDTO.getVlasnikTerenaId(), terenDTO.getTip());

        return ResponseEntity.created(URI.create("/tereni/" + saved.getIDTeren())).body(saved);
    }
    
    


}

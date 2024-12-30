package fer.progi.mjesecari.ppadel.api;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    
    @DeleteMapping("")
    public Rezervacija deleteRezervacija(@RequestParam Long terenId, @RequestParam LocalDateTime startTime){
        return rezervacijaService.deleteRezervacija(new RezervacijaId(terenService.fetch(terenId), startTime));
    }
    


}

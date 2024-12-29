package fer.progi.mjesecari.ppadel.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fer.progi.mjesecari.ppadel.api.dto.RezervacijaDTO;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.domain.Rezervacija;
import fer.progi.mjesecari.ppadel.domain.Teren;
import fer.progi.mjesecari.ppadel.service.KorisnikService;
import fer.progi.mjesecari.ppadel.service.RezervacijaService;
import fer.progi.mjesecari.ppadel.service.TerenService;
import fer.progi.mjesecari.ppadel.api.exception.*;

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
    public List<Rezervacija> getAll() {
        return rezervacijaService.fetchAll();
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
    


}

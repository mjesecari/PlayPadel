package fer.progi.mjesecari.ppadel.api;

import fer.progi.mjesecari.ppadel.api.dto.IgracDTO;
import fer.progi.mjesecari.ppadel.api.dto.VlasnikDTO;
import fer.progi.mjesecari.ppadel.dao.IgracRepository;
import fer.progi.mjesecari.ppadel.dao.VlasnikRepository;
import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import fer.progi.mjesecari.ppadel.service.IgracService;
import fer.progi.mjesecari.ppadel.service.VlasnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping("/vlasnik")
public class VlasnikController {

    @Autowired
    private VlasnikRepository vlasnikRepository;

    @Autowired
    private VlasnikService vlasnikService;

    @PostMapping
    public Vlasnik createVlasnik (@RequestBody VlasnikDTO vlasnikDTO){
        if (vlasnikDTO.getRole().equals("vlasnik")) {
            Vlasnik vlasnik = new Vlasnik();
            vlasnik.setEmail(vlasnikDTO.getEmail());
            vlasnik.setNazivVlasnik(vlasnikDTO.getNazivVlasnik());
            vlasnik.setLokacija(vlasnikDTO.getBrojTel());
            vlasnik.setBrojTel(vlasnikDTO.getBrojTel());
            vlasnik.setTip(vlasnikDTO.getRole());
            Vlasnik saved = vlasnikService.createVlasnik(vlasnik);
            return saved;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authenticated with oauth2\n");
        }
    }

    @GetMapping ("/read/{email}")
    public Vlasnik readVlasnikEmail(@PathVariable String email) {
        return vlasnikRepository.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find user email\n"));
    }

    @GetMapping ("/{id}")
    public Vlasnik readVlasnikID (@PathVariable Long id) {
        return vlasnikRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find user by id\n"));
    }

    @PutMapping ("/{id}")
    public Vlasnik updateVlasnik(@PathVariable Long id, @RequestBody VlasnikDTO vlasnikDTO) {
        if (!vlasnikRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find user by id\n");
        }
        else {
            vlasnikService.updateNazivVlasnik(id, vlasnikDTO.getNazivVlasnik());
            vlasnikService.updateLokacija(id, vlasnikDTO.getLokacija());
            return vlasnikService.updateBrojTel(id, vlasnikDTO.getBrojTel());
        }
    }

    @DeleteMapping ("/{id}")
    public void deleteVlasnik(@PathVariable Long id) {
        if (!vlasnikRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find user by id\n");
        }
        else {
            vlasnikService.deleteVlasnik(id);
        }
    }
}
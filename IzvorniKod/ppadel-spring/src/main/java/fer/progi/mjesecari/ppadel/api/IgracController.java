package fer.progi.mjesecari.ppadel.api;

import fer.progi.mjesecari.ppadel.api.dto.IgracDTO;
import fer.progi.mjesecari.ppadel.dao.IgracRepository;
import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.service.IgracService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping("/igrac")
public class IgracController {

    @Autowired
    private IgracRepository igracRepository;

    @Autowired
    private IgracService igracService;

    @PostMapping
    public Igrac createIgrac(@RequestBody IgracDTO igracDTO) {
        if (igracDTO.getRole().equals("igrač")) {
            Igrac igrac = new Igrac();
            igrac.setTip(igracDTO.getRole());
            igrac.setEmail(igracDTO.getEmail());
            igrac.setImeIgrac(igracDTO.getImeIgrac());
            igrac.setPrezimeIgrac(igracDTO.getPrezimeIgrac());
            igrac.setBrojTel(igracDTO.getBrojTel());
            System.out.println("saved");
            Igrac saved = igracService.createIgrac(igrac);
            return saved;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not authenticated with oauth2\n");
        }
    }

    @GetMapping ("/read/{email}")
    public Igrac readIgracEmail(@PathVariable String email) {
        return igracRepository.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found\n"));
    }

    @GetMapping ("/{id}")
    public Igrac readIgracID(@PathVariable Long id) {
        return igracRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found\n"));
    }

    @PutMapping ("/{id}")
    public Igrac updateIgrac(@PathVariable Long id, @RequestBody IgracDTO igracDTO) {
        if (!igracRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found\n");
        }
        else {
            igracService.updateImeIgrac(id, igracDTO.getImeIgrac());
            igracService.updatePrezimeIgrac(id, igracDTO.getPrezimeIgrac());
            return igracService.updateBrojTel(id, igracDTO.getBrojTel());
        }
    }

    @DeleteMapping ("/{id}")
    public void deleteIgrac(@PathVariable Long id) {
        if (!igracRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found\n");
        }
        else {
            igracService.deleteIgrac(id);
        }
    }
}
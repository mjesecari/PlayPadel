package fer.progi.mjesecari.ppadel.api;

import fer.progi.mjesecari.ppadel.dao.ClanstvoRepository;
import fer.progi.mjesecari.ppadel.domain.Clanstvo;
import fer.progi.mjesecari.ppadel.service.ClanstvoService;
import fer.progi.mjesecari.ppadel.service.impl.ClanstvoServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@Profile({"form-security", "oauth-security"})
public class ClanstvoController {
    @Autowired
    private ClanstvoService clanstvoService;
    @Autowired
    private ClanstvoRepository clanstvoRepository;

    @PostMapping("/membership/create/{id}")
    public Clanstvo createMembership (@PathVariable Long id){
        Clanstvo clanstvo = clanstvoService.CreateClanstvo(id);
        return clanstvo;
    }
    @GetMapping("membership/all")
    public List<Clanstvo> listAll (){
        return clanstvoService.listAll();
    }
    @GetMapping("membership/isPayed")
    public boolean isPayed (@RequestParam Long id){
        Optional<Clanstvo> Currentclanstvo = clanstvoRepository.findById(id);
        Clanstvo clanstvo = Currentclanstvo.orElseThrow(()->new IllegalArgumentException("Clanstvo za vlasnika nije pronađeno"));
        if (clanstvo.getDatumIsteka() != null && clanstvo.getDatumIsteka().isAfter(LocalDate.now())){
            return true;
        }
        else{
            return false;
        }
    }
}

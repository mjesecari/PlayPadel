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

@RestController
@Profile({"form-security", "oauth-security"})
public class ClanstvoController {
    @Autowired
    private ClanstvoService clanstvoService;

    @PostMapping("/membership/payment")
    public RedirectView PayMembership(@RequestParam Long userId,
                                      @RequestParam double total,
                                      @RequestParam String currency,
                                      @RequestParam String method,
                                      @RequestParam String intent,
                                      @RequestParam String description) {
        String redirectUrl = String.format("/payment/create?userId=%d&total=%.2f&currency=%s&method=%s&intent=%s&description=%s",
                userId, total, currency, method, intent, description);
        return new RedirectView(redirectUrl);
    }
    @PostMapping("/membership/create/{id}")
    public Clanstvo createMembership (@PathVariable Long id){
        Clanstvo clanstvo = clanstvoService.CreateClanstvo(id);
        return clanstvo;
    }
    @GetMapping("membership/all")
    public List<Clanstvo> listAll (){
        return clanstvoService.listAll();
    }
}

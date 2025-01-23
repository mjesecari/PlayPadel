package fer.progi.mjesecari.ppadel.api;

import fer.progi.mjesecari.ppadel.api.dto.KomentarTurnirDTO;
import fer.progi.mjesecari.ppadel.domain.KomentarTurnir;
import fer.progi.mjesecari.ppadel.domain.SlikaTurnir;
import fer.progi.mjesecari.ppadel.service.KomentarTurnirService;
import fer.progi.mjesecari.ppadel.service.SlikaTurnirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Profile({"form-security", "oauth-security"})
@RestController()
public class KomentarTurnirController {
    @Autowired
    private KomentarTurnirService komentarTurnirService;
    @PostMapping(value="/komentarTurnir/{idTurnir}")
    public void uploadSlikaTurnir(@PathVariable Long idTurnir, @RequestBody KomentarTurnirDTO komentarTurnirDTO)  {
        KomentarTurnir komentarTurnir = komentarTurnirService.uploadComment(komentarTurnirDTO.getTekst(),idTurnir,komentarTurnirDTO.getUserId());
    }
    @GetMapping("/komentariTurnir/{idTurnir}")
    public List<KomentarTurnir> getAllComments (@PathVariable Long idTurnir){
        return komentarTurnirService.fetchAll((idTurnir));
    }
}

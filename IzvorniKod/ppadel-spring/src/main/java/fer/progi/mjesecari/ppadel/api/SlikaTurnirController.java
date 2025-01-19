package fer.progi.mjesecari.ppadel.api;

import fer.progi.mjesecari.ppadel.domain.SlikaTurnir;
import fer.progi.mjesecari.ppadel.service.SlikaTurnirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Profile({"form-security", "oauth-security"})
@RestController()
public class SlikaTurnirController {
    @Autowired
    private SlikaTurnirService slikaTurnirService;
   @PostMapping(value="/slikaTurnir/{idTurnir}", consumes = {"multipart/form-data"})
    public void uploadSlikaTurnir(@PathVariable Long idTurnir, @RequestParam("photo") MultipartFile file, @RequestParam("userId") Long IdKorisnik) throws IOException {
        SlikaTurnir slikaTurnir = slikaTurnirService.uploadPicture(file.getBytes(),idTurnir, IdKorisnik );
    }
    /**@PostMapping(value = "/slikaTurnir/{idTurnir}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadSlikaTurnir(
            @PathVariable Long idTurnir,
            @RequestPart("photo") MultipartFile file,
            @RequestPart("userId") Long userId
    ) {
        try {
            System.out.println("File Name: " + file.getOriginalFilename());
            System.out.println("File Size: " + file.getSize());
            System.out.println("User ID: " + userId);

            // Call your service to process the file
            slikaTurnirService.uploadPicture(file.getBytes(), idTurnir, userId);
            return ResponseEntity.ok("Photo uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading photo");
        }
    }**/
    @GetMapping("/slikeTurnir/{idTurnir}")
    public List<SlikaTurnir> getAllPictures (@PathVariable Long idTurnir){
        return slikaTurnirService.fetchAll((idTurnir));
    }
}

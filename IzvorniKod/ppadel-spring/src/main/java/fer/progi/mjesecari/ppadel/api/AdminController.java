package fer.progi.mjesecari.ppadel.api;


import fer.progi.mjesecari.ppadel.dao.UserRepository;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminsrv;

    @GetMapping
    public String AdminPage(){
        return("Admin");
    }
    @GetMapping("/users")
    public List<Korisnik> GetAll(){
        return adminsrv.getAllUsers();
    }
    @GetMapping("/users/{type}")
    public List<Korisnik> GetByType(@PathVariable String type){
        return adminsrv.getUsersByType(type);
    }

    @PostMapping("/users")
    public Korisnik addUser(@RequestBody Korisnik korisnik){
        return adminsrv.addUser(korisnik);
    }

    @DeleteMapping("/users/{id}")
    public Korisnik deleteUser(@PathVariable Long id){
        return adminsrv.deleteUser(id);
    }

    @PutMapping("/{id}/membership")
    public ResponseEntity<String> updateMembership (@PathVariable Long id, @RequestParam Float clanarina){
        try {
            adminsrv.updateClanarina(id, clanarina);
            return ResponseEntity.ok("Membership updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

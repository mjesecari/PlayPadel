package fer.progi.mjesecari.ppadel.api;


import fer.progi.mjesecari.ppadel.dao.AdminRepository;
import fer.progi.mjesecari.ppadel.dao.UserRepository;
import fer.progi.mjesecari.ppadel.domain.Administrator;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminsrv;
    /**@Autowired
    private AdminRepository adminRepo;**/

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String AdminPage(){
        return("Admin");
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Korisnik> GetAll(){
        return adminsrv.getAllUsers();
    }

    @GetMapping("/users/{type}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Korisnik> GetByType(@PathVariable String type){
        return adminsrv.getUsersByType(type);
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Korisnik addUser(@RequestBody Korisnik korisnik){
        return adminsrv.addUser(korisnik);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Korisnik deleteUser(@PathVariable Long id){
        return adminsrv.deleteUser(id);
    }

    @PutMapping("/{id}/membership")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateMembership (@PathVariable Long id, @RequestParam Float clanarina){
        try {
            adminsrv.updateClanarina(id, clanarina);
            return ResponseEntity.ok("Membership updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //used for testing
    /**@GetMapping("/admins")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Administrator> listAdmins(){return adminRepo.findAll();}**/
    //TODO: add edit user endpoint
    //TODO: add filter owners by payed membership endpoint

}

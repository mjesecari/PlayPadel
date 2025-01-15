package fer.progi.mjesecari.ppadel.api;


import fer.progi.mjesecari.ppadel.api.dto.IgracDTO;
import fer.progi.mjesecari.ppadel.api.dto.VlasnikDTO;
import fer.progi.mjesecari.ppadel.dao.AdminRepository;
import fer.progi.mjesecari.ppadel.dao.IgracRepository;
import fer.progi.mjesecari.ppadel.dao.VlasnikRepository;
import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import fer.progi.mjesecari.ppadel.service.AdminService;
import fer.progi.mjesecari.ppadel.service.IgracService;
import fer.progi.mjesecari.ppadel.service.VlasnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import fer.progi.mjesecari.ppadel.dao.UserRepository;
import fer.progi.mjesecari.ppadel.domain.Administrator;

import java.util.List;

@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminsrv;
    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private IgracService igracService;
    @Autowired
    private VlasnikService vlasnikService;
    @Autowired
    private IgracRepository igracRepository;
    @Autowired
    private VlasnikRepository vlasnikRepository;

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

    @PutMapping ("igrac/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private Igrac updateIgrac(@PathVariable Long id, @RequestBody IgracDTO igracDTO) {
        if (!igracRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found\n");
        }
        else {
            igracService.updateImeIgrac(id, igracDTO.getImeIgrac());
            igracService.updatePrezimeIgrac(id, igracDTO.getPrezimeIgrac());
            return igracService.updateBrojTel(id, igracDTO.getBrojTel());
        }
    }

    @PutMapping ("vlasnik/{id}")
    private Vlasnik updateVlasnik(@PathVariable Long id, @RequestBody VlasnikDTO vlasnikDTO) {
        if (!vlasnikRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find user by id\n");
        }
        else {
            vlasnikService.updateNazivVlasnik(id, vlasnikDTO.getNazivVlasnik());
            vlasnikService.updateLokacija(id, vlasnikDTO.getLokacija());
            return vlasnikService.updateBrojTel(id, vlasnikDTO.getBrojTel());
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Administrator> listAdmins(){return adminRepo.findAll();}

   /** @GetMapping("/vlasnici")
    public List<Vlasnik> listVlasnici(){return vlasnikRepository.findAll();}
    @GetMapping("/igraci")
    public List<Igrac> listIgraci(){return igracRepository.findAll();}**/

    @PostMapping("/users/vlasnik")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Korisnik addUser(@RequestBody VlasnikDTO vlasnik){
        return adminsrv.addVlasnik(vlasnik);
    }

    @PostMapping("/users/igrac")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Korisnik addUser(@RequestBody IgracDTO igrac){
        return adminsrv.addIgrac(igrac);
    }

}

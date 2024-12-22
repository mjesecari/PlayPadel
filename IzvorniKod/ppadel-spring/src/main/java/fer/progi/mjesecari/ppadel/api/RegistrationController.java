package fer.progi.mjesecari.ppadel.api;

import java.net.URI;

import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import fer.progi.mjesecari.ppadel.service.IgracService;
import fer.progi.mjesecari.ppadel.service.VlasnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.KorisnikService;


@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private KorisnikService userService;
    private IgracService igracService;
    private VlasnikService vlasnikService;

    private static class RoleIgrac {
        private String role;
        private String email;
        private String imeIgrac;
        private String prezimeIgrac;
        private String BrojTel;

        public RoleIgrac(@JsonProperty("email") String email,@JsonProperty("role") String role, @JsonProperty("imeIgrac") String imeIgrac, @JsonProperty("prezimeIgrac") String prezimeIgrac, @JsonProperty("brojTel") String brojTel) {
            this.role = role;
            this.email = email;
            this.imeIgrac = imeIgrac;
            this.prezimeIgrac = prezimeIgrac;
            this.BrojTel = brojTel;
        }

        public String getEmail() {
            return email;
        }

        public String getImeIgrac() {
            return imeIgrac;
        }

        public void setImeIgrac(String imeIgrac) {
            this.imeIgrac = imeIgrac;
        }

        public String getPrezimeIgrac() {
            return prezimeIgrac;
        }

        public void setPrezimeIgrac(String prezimeIgrac) {
            this.prezimeIgrac = prezimeIgrac;
        }

        public String getBrojTel() {
            return BrojTel;
        }

        public void setBrojTel(String brojTel) {
            BrojTel = brojTel;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    private static class RoleVlasnik {
        private String role;
        private String email;
        private String BrojTel;
        private String NazivVlasnik;
        private String lokacija;

        public RoleVlasnik(@JsonProperty("role") String role, @JsonProperty("email") String email, @JsonProperty("brojTel") String brojTel, @JsonProperty("nazivVlasnik") String nazivVlasnik, @JsonProperty("lokacija") String lokacija) {
            this.role = role;
            this.email = email;
            BrojTel = brojTel;
            NazivVlasnik = nazivVlasnik;
            this.lokacija = lokacija;
        }

        public String getEmail() {
            return email;
        }


        public String getBrojTel() {
            return BrojTel;
        }

        public void setBrojTel(String brojTel) {
            BrojTel = brojTel;
        }

        public String getNazivVlasnik() {
            return NazivVlasnik;
        }

        public void setNazivVlasnik(String nazivVlasnik) {
            NazivVlasnik = nazivVlasnik;
        }

        public String getLokacija() {
            return lokacija;
        }

        public void setLokacija(String lokacija) {
            this.lokacija = lokacija;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    // TODO: change to postmapping
    @PostMapping ("/igrac")
    public ResponseEntity<Korisnik> setRoleIgrac(@RequestBody RoleIgrac roleIgrac) {
        // DefaultOidcUser oidcUser = (DefaultOidcUser)((OAuth2AuthenticationToken)principal).getPrincipal();
        // Map<String,Object> claims = oidcUser.getClaims();
        // String userEmail = (String)claims.get("email");

        if (roleIgrac.getRole().equals("igrač")) {
            Igrac igrac = new Igrac();
            igrac.setEmail(roleIgrac.getEmail());
            igrac.setImeIgrac(roleIgrac.getImeIgrac());
            igrac.setPrezimeIgrac(roleIgrac.getPrezimeIgrac());
            igrac.setBrojTel(roleIgrac.getBrojTel());
            igrac.setTip(roleIgrac.getRole());
            Igrac saved = igracService.createIgrac(igrac);
            return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(saved);
        }
        else {
            Korisnik newKorisnik = new Korisnik();
            newKorisnik.setEmail(roleIgrac.getEmail());
            newKorisnik.setTip(roleIgrac.getRole());
            Korisnik saved = userService.createKorisnik(newKorisnik);
            return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(saved);
        }

        //oidcUser.getAuthorities().add(new SimpleGrantedAuthority("ROLE_OWNER"));

    }

    // TODO: change to postmapping
    @PostMapping ("/vlasnik")
    public ResponseEntity<Korisnik> setRole(@RequestBody RoleVlasnik roleVlasnik) {
        // DefaultOidcUser oidcUser = (DefaultOidcUser)((OAuth2AuthenticationToken)principal).getPrincipal();
        // Map<String,Object> claims = oidcUser.getClaims();
        // String userEmail = (String)claims.get("email");

        if (roleVlasnik.getRole().equals("vlasnik")) {
            Vlasnik vlasnik = new Vlasnik();
            vlasnik.setEmail(roleVlasnik.getEmail());
            vlasnik.setNazivVlasnik(roleVlasnik.getNazivVlasnik());
            vlasnik.setLokacija(roleVlasnik.getBrojTel());
            vlasnik.setBrojTel(roleVlasnik.getBrojTel());
            vlasnik.setTip(roleVlasnik.getRole());
            Vlasnik saved = vlasnikService.createVlasnik(vlasnik);
            return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(saved);
        }
        else {
            Korisnik newKorisnik = new Korisnik();
            newKorisnik.setEmail(roleVlasnik.getEmail());
            newKorisnik.setTip(roleVlasnik.getRole());
            Korisnik saved = userService.createKorisnik(newKorisnik);
            return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(saved);
        }

        //oidcUser.getAuthorities().add(new SimpleGrantedAuthority("ROLE_OWNER"));

    }
}



/*    package fer.progi.mjesecari.ppadel.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.KorisnikService;


            @Profile({"form-security", "oauth-security"})
            @RestController
            @RequestMapping("/register")
            public class RegistrationController {

                @Autowired
                private KorisnikService userService;

                private static class RoleDTO {
                    private String role;
                    private String email;


                    public RoleDTO(@JsonProperty("role") String role, @JsonProperty("email") String email){
                        this.role = role;
                        this.email = email;
                    }

                    public String getEmail() {
                        return email;
                    }

                    public void setEmail(String email) {
                        this.email = email;
                    }

                    public String getRole() {
                        return role;
                    }

                    public void setRole(String role) {
                        this.role = role;
                    }
                }

                @PostMapping
                public ResponseEntity<Korisnik> setRole(@RequestBody RoleDTO roleDTO){
                    // DefaultOidcUser oidcUser = (DefaultOidcUser)((OAuth2AuthenticationToken)principal).getPrincipal();
                    // Map<String,Object> claims = oidcUser.getClaims();
                    // String userEmail = (String)claims.get("email");

                    Korisnik newKorisnik = new Korisnik();
                    newKorisnik.setEmail(roleDTO.getEmail());
                    newKorisnik.setTip(roleDTO.getRole());
                    Korisnik saved = userService.createKorisnik(newKorisnik);

                    //oidcUser.getAuthorities().add(new SimpleGrantedAuthority("ROLE_OWNER"));

                    return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(saved);
                }
            }*/
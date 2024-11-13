package fer.progi.mjesecari.ppadel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fer.progi.mjesecari.ppadel.dao.UserRepository;
import fer.progi.mjesecari.ppadel.domain.Korisnik;

@Component    
public class DBInitializaer implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String...args) throws Exception {
        Korisnik admin = new Korisnik();
        admin.setTip("admin");
        admin.setEmail("dinoplecko@gmail.com");
        userRepository.save(admin);
    }
}
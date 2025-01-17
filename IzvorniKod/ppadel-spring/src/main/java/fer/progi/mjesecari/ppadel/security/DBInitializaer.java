package fer.progi.mjesecari.ppadel.security;

import fer.progi.mjesecari.ppadel.domain.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

import fer.progi.mjesecari.ppadel.dao.UserRepository;
import fer.progi.mjesecari.ppadel.domain.Korisnik;

@Component    
public class DBInitializaer implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String...args) throws Exception {
        Administrator admin = new Administrator();
        admin.setTip("admin");
        admin.setEmail("playpadel.mjesecari@gmail.com");
        admin.setCijenaClanarine(0.0f);
        userRepository.save(admin);
    }
}
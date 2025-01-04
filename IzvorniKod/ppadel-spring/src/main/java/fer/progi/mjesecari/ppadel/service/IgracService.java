package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.Igrac;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IgracService {
    /**
     * Lists all users in the system.
     * @return a list with all users
     */
    List<Igrac> listAll();
    Igrac createIgrac(Igrac igrac);
    void validate(Igrac igrac);

    /**
     * Finds the user with given email.
     * @param email user email
     * @return Optional with value of a user associated with given email exists in the system,
     * no value otherwise
     * @throws IllegalArgumentException if given email is null
     */
    Optional<Igrac> findByEmail (String email);
}

package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.Igrac;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IgracService {
    Igrac fetch(Long id);
    List<Igrac> listAll();
    Igrac createIgrac(Igrac igrac);
    void validate(Igrac igrac);
    Optional<Igrac> findByEmail (String email);
    void deleteIgrac (Long id);
    Igrac updateImeIgrac (Long id, String imeIgrac);
    Igrac updatePrezimeIgrac (Long id, String prezimeIgrac);
    Igrac updateBrojTel (Long id, String brojTel);
}

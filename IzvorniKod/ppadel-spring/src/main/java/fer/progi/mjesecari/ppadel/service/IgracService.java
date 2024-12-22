package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.Igrac;

import java.util.List;

public interface IgracService {
    List<Igrac> listAll();
    Igrac createIgrac(Igrac igrac);
    void validate(Igrac igrac);
}

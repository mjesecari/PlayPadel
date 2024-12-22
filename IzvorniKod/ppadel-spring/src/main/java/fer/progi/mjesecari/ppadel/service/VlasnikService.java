package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.Vlasnik;

import java.util.List;

public interface VlasnikService {
    List<Vlasnik> listAll();
    Vlasnik createVlasnik(Vlasnik vlasnik);
    void validate(Vlasnik vlasnik);
}

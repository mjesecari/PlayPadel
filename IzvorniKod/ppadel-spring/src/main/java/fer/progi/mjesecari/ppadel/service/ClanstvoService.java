package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.Clanstvo;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;

import java.util.List;

public interface ClanstvoService {
    List<Clanstvo> listAll();
    Clanstvo CreateClanstvo(Long id);
    Clanstvo UpdateClanstvo (Long id, double total, String method);
    List<Vlasnik> listAllPayed();
    List<Vlasnik> listAllUnPayed();
}

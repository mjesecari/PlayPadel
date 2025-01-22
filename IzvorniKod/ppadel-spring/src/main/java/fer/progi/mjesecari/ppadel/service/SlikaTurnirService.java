package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.domain.SlikaTeren;
import fer.progi.mjesecari.ppadel.domain.SlikaTurnir;
import fer.progi.mjesecari.ppadel.domain.Teren;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SlikaTurnirService {
    public SlikaTurnir uploadPicture (byte[] pictureData, Long Idturnir, Long IdKorisnik);
    public List<SlikaTurnir> fetchAll (Long Idturnir);
}

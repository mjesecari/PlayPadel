package fer.progi.mjesecari.ppadel.service;

import fer.progi.mjesecari.ppadel.dao.SlikaTerenRepository;
import fer.progi.mjesecari.ppadel.domain.SlikaTeren;
import fer.progi.mjesecari.ppadel.domain.Teren;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface SlikaTerenService {
    public void uploadPicture (byte[] pictureData, Teren teren);
    public SlikaTeren deletePicture (SlikaTeren slikaTeren);
    public void saveSlikaTeren (SlikaTeren slikaTeren);
    public void UpdateSlikaTeren (Long terenID, byte[] PictureData);
}
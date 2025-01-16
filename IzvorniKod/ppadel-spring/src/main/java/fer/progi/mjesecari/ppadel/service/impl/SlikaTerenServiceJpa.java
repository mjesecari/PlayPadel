package fer.progi.mjesecari.ppadel.service.impl;

import fer.progi.mjesecari.ppadel.dao.SlikaTerenRepository;
import fer.progi.mjesecari.ppadel.domain.SlikaTeren;
import fer.progi.mjesecari.ppadel.domain.Teren;
import fer.progi.mjesecari.ppadel.service.EntityMissingException;
import fer.progi.mjesecari.ppadel.service.SlikaTerenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SlikaTerenServiceJpa implements SlikaTerenService {
    @Autowired
    private SlikaTerenRepository slikaTerenRepo;

    @Override
    public void uploadPicture(byte[] pictureData, Teren teren) {
        SlikaTeren slikaTeren = new SlikaTeren();
        slikaTeren.setTeren(teren);
        slikaTeren.setPhotoData(pictureData);
        slikaTerenRepo.save(slikaTeren);

    }

    @Override
    public SlikaTeren deletePicture(SlikaTeren slikaTeren) {
        slikaTerenRepo.delete(slikaTeren);
        return slikaTeren;
    }

    @Override
    public void saveSlikaTeren(SlikaTeren slikaTeren) {
        slikaTerenRepo.save(slikaTeren);
    }

    @Override
    public void UpdateSlikaTeren(Long terenID, byte[] PictureData ) {
        Optional<SlikaTeren> slikaTerenOp = slikaTerenRepo.findSlikaByTerenId(terenID);
        SlikaTeren slikaTeren = slikaTerenOp.orElseThrow(()-> new EntityMissingException(SlikaTeren.class,"Teren nema sliku"));
        slikaTeren.setPhotoData(PictureData);
        slikaTerenRepo.save(slikaTeren);
    }


}

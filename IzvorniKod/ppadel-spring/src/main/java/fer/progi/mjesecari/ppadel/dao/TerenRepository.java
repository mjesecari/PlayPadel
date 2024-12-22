package fer.progi.mjesecari.ppadel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fer.progi.mjesecari.ppadel.domain.Teren;
import java.util.List;


public interface TerenRepository extends JpaRepository<Teren, Long>{
    List<Teren> findAllByVlasnikTerenId(Long VlasnikTerenId);
    List<Teren> findAllByVlasnikTerenEmail(String VlasnikTerenEmail);
}

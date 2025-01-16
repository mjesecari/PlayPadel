package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.SlikaTeren;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SlikaTerenRepository extends JpaRepository<SlikaTeren, Long> {
    @Query("SELECT s FROM SlikaTeren s WHERE s.teren.IDTeren = :terenId")
    Optional<SlikaTeren> findSlikaByTerenId (@Param("terenId") Long terenId);
}

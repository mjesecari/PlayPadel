package fer.progi.mjesecari.ppadel.dao;

import fer.progi.mjesecari.ppadel.domain.Clanstvo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClanstvoRepository extends JpaRepository <Clanstvo, Long> {
    Optional<Clanstvo> findById (Long idVlasnik);
    @Query("SELECT m.Id FROM Clanstvo m " +
            "WHERE m.datumIsteka IS NULL OR m.datumIsteka < :currentDate" )
    List<Long> findExpiredMemberships (@Param("currentDate")LocalDateTime currentDate);
    @Query("SELECT m.Id FROM Clanstvo m WHERE m.datumIsteka > :currentDate")
    List<Long> findValidMemberships (@Param("currentDate") LocalDateTime currentDate);


}

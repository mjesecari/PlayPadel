package fer.progi.mjesecari.ppadel.dao;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import fer.progi.mjesecari.ppadel.domain.Administrator;

public interface AdminRepository extends JpaRepository<Administrator, Long>{
    Administrator findAdministratorById (Long id);
    Administrator save(Administrator admin);
}
package fer.progi.mjesecari.ppadel.dao;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import fer.progi.mjesecari.ppadel.domain.Administrator;

public interface AdminRepository extends JpaRepository<Administrator, Long>{
    Administrator findAdministratorById (Long id);
    Administrator save(Administrator admin);
}
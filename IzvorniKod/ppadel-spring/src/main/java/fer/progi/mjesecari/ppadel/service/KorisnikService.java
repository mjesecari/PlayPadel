package fer.progi.mjesecari.ppadel.service;

import java.util.List;
import java.util.Optional;

import fer.progi.mjesecari.ppadel.domain.Korisnik;

/**
 * Manages korisnik database.
 * @see Korisnik
 * @author Dino Plečko dino.plecko@fer.unizg.hr
 */
public interface KorisnikService {
  /**
   * Lists all users in the system.
   * @return a list with all users
   */
  List<Korisnik> listAll();

  /**
   * Fetches user with given ID.
   * @param korisnikId given user ID
   * @return user associated with given ID in the system
   * @throws EntityMissingException if user with that ID is not found
   */
  Korisnik fetch(long korisnikId);
  // Note: verb "fetch" in method name is typically used when identified object is expected

  /**
   * Creates new user in the system.
   * @param korisnik object to create, with ID set to null
   * @return created user object in the system with ID set
   * @throws IllegalArgumentException if given user is null, or its ID is NOT null,
   * or its JMBAG is null or invalid
   * @throws RequestDeniedException if user with that JMBAG already exists in the system
   * @see Korisnik
   */
  Korisnik createKorisnik(Korisnik korisnik);

  /**
   * Finds user with given ID, if exists.
   * @param korisnikId given user ID
   * @return Optional with value of user associated with given ID in the system,
   * or no value if one does not exist
   */
  Optional<Korisnik> findById(long korisnikId);

  /**
   * Finds the user with given email.
   * @param email user email
   * @return Optional with value of a user associated with given email exists in the system,
   * no value otherwise
   * @throws IllegalArgumentException if given email is null
   */
  Optional<Korisnik> findByEmail(String email);

  /**
   * Updates the user with that same ID.
   * @param korisnik object to update, with ID set
   * @return updated user object in the system
   * @throws IllegalArgumentException if given object is null, has null ID, or has null or invalid JMBAG
   * @throws EntityMissingException if user with given ID is not found
   * @throws RequestDeniedException if another user with some other ID and the same JMBAG already exists
   */
  Korisnik updateKorisnik(Korisnik korisnik);

  /**
   * Deletes one user.
   * @param korisnikId ID of user to delete from the system
   * @return deleted data
   * @throws EntityMissingException if user with that ID is not found
   */
  Korisnik deleteKorisnik(long korisnikId);
}

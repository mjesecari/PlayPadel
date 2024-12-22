package fer.progi.mjesecari.ppadel.service;

import org.springframework.stereotype.Service;

import fer.progi.mjesecari.ppadel.domain.Teren;

import java.util.List;
import java.util.Optional;

/**
 * Manages terens in the system.
 * @see Teren
 * @author Dino Plečko dino.plecko@fer.unizg.hr
 */
@Service
public interface TerenService {

  /**
   * Lists all terens.
   * @return iterable containing all terens
   */
  List<Teren> listAll();

  /**
   * Finds teren with given ID, if exists.
   * @param terenId given teren ID
   * @return Optional with value of teren associated with given ID in the system,
   * or no value if one does not exist
   * @see TerenService#fetch
   */
  Optional<Teren> findById(long terenId);

  /**
   * Fetches teren with given ID.
   * @param terenId given teren ID
   * @return teren associated with given ID
   * @throws EntityMissingException if teren with that ID not found in the system
   */
  Teren fetch(long terenId);

  /**
   * Creates new teren with given name, vlasnik and tip.
   * @param terenName name of the new teren
   * @param vlasnikID id of vlasnik of the new teren
   * @return created teren object, with ID set
   * @throws IllegalArgumentException if name is empty or any is <code>null</code>
   * @throws RequestDeniedException if no user with given Id
   */
  Teren createTeren(String terenName, Long vlasnikID, String terenTip);

  /**
   * Updates the name of a given teren.
   * @param terenId identifies teren to update
   * @param name new name of the teren
   * @return updated teren object
   * @throws EntityMissingException if entity with the same ID as in parameter does not exist
   * @throws IllegalArgumentException if name is empty or any is <code>null</code>
   */
  Teren updateTerenName(long terenId, String name);


  /**
   * Deletes one teren.
   * @param terenId ID of teren to delete from the system
   * @return deleted data
   * @throws EntityMissingException if teren with that ID is not found
   */
  Teren deleteTeren(long terenId);
}

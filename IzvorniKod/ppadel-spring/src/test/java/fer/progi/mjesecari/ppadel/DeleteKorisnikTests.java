package fer.progi.mjesecari.ppadel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import fer.progi.mjesecari.ppadel.dao.IgracRepository;
import fer.progi.mjesecari.ppadel.dao.VlasnikRepository;
import fer.progi.mjesecari.ppadel.service.IgracService;
import fer.progi.mjesecari.ppadel.service.VlasnikService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
		"FRONTEND_REGISTER=http://localhost:3000/register",
		"DATASOURCE_URL=jdbc:h2:mem:testiranje;DB_CLOSE_DELAY=-1",
		"FRONTEND=http://localhost:3000"
})
public class DeleteKorisnikTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IgracRepository igracRepository;

	@MockBean
	private VlasnikRepository vlasnikRepository;

	@MockBean
	private IgracService igracService;

	@MockBean
	private VlasnikService vlasnikService;

	@BeforeEach
	void setUp() {
		Mockito.reset(igracRepository, vlasnikRepository, igracService, vlasnikService);
	}

	@Test
	@WithMockUser
	void testDeleteIgracSuccess() {
		Long id = 1L;
		System.out.println("Test input: id-1");
		try {
			when(igracRepository.existsById(id)).thenReturn(true);

			System.out.println("Testing testDeleteIgracSuccess with ID: " + id);

			mockMvc.perform(delete("/korisnik/igrac/{id}", id))
					.andExpect(status().isOk());

			verify(igracService, times(1)).deleteIgrac(id);

			System.out.println("Test passed: Successfully deleted igrac with ID: " + id);
		} catch (Exception e) {
			System.out.println("Test failed: Error while testing deletion for ID: " + id);
			e.printStackTrace();
			throw new RuntimeException("Test failed", e); // Re-throw to ensure the test fails in reporting.
		}
	}

	@Test
	@WithMockUser
	void testDeleteVlasnikSuccess() {
		Long id = 2L;
		System.out.println("Test input: id-2 ");
		try {
			when(vlasnikRepository.existsById(id)).thenReturn(true);

			System.out.println("Testing testDeleteVlasnikSuccess with ID: " + id);

			mockMvc.perform(delete("/korisnik/vlasnik/{id}", id))
					.andExpect(status().isOk());

			verify(vlasnikService, times(1)).deleteVlasnik(id);

			System.out.println("Test passed: Successfully deleted vlasnik with ID: " + id);
		} catch (Exception e) {
			System.out.println("Test failed: Error while testing deletion for ID: " + id);
			e.printStackTrace();
			throw new RuntimeException("Test failed", e); // Re-throw to ensure the test fails in reporting.
		}
	}
}
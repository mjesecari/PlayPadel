package fer.progi.mjesecari.ppadel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import fer.progi.mjesecari.ppadel.dao.IgracRepository;
import fer.progi.mjesecari.ppadel.dao.VlasnikRepository;
import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import fer.progi.mjesecari.ppadel.service.IgracService;
import fer.progi.mjesecari.ppadel.service.VlasnikService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"FRONTEND_REGISTER=http://localhost:3000/register"})
public class PlayPadelApplicationTests {

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
	void testGetIgracByEmailSuccess() throws Exception {
		String email = "test@example.com";
		Igrac igrac = new Igrac();
		igrac.setEmail(email);
		when(igracRepository.findByEmail(email)).thenReturn(Optional.of(igrac));

		mockMvc.perform(get("/korisnik/igrac/{email}", email))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value(email));
	}

	// Other test methods...


	@Test
	void testGetIgracByEmailNotFound() throws Exception {
		String email = "nonexistent@example.com";
		when(igracRepository.findByEmail(email)).thenReturn(Optional.empty());

		mockMvc.perform(get("/korisnik/igrac/{email}", email))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.message").value("Cannot find user email"));
	}

	@Test
	void testDeleteIgracSuccess() throws Exception {
		Long id = 1L;
		when(igracRepository.existsById(id)).thenReturn(true);

		mockMvc.perform(delete("/korisnik/igrac/{id}", id))
				.andExpect(status().isOk());

		verify(igracService, times(1)).deleteIgrac(id);
	}

	@Test
	void testDeleteIgracNotFound() throws Exception {
		Long id = 1L;
		when(igracRepository.existsById(id)).thenReturn(false);

		mockMvc.perform(delete("/korisnik/igrac/{id}", id))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.message").value("Cannot find user by id"));
	}

	@Test
	void testGetVlasnikByEmailSuccess() throws Exception {
		String email = "vlasnik@example.com";
		Vlasnik vlasnik = new Vlasnik();
		vlasnik.setEmail(email);
		when(vlasnikRepository.findByEmail(email)).thenReturn(Optional.of(vlasnik));

		mockMvc.perform(get("/korisnik/vlasnik/{email}", email))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value(email));
	}

	@Test
	void testGetVlasnikByEmailNotFound() throws Exception {
		String email = "nonexistent@example.com";
		when(vlasnikRepository.findByEmail(email)).thenReturn(Optional.empty());

		mockMvc.perform(get("/korisnik/vlasnik/{email}", email))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.message").value("Cannot find user email"));
	}

	@Test
	void testDeleteVlasnikSuccess() throws Exception {
		Long id = 2L;
		when(vlasnikRepository.existsById(id)).thenReturn(true);

		mockMvc.perform(delete("/korisnik/vlasnik/{id}", id))
				.andExpect(status().isOk());

		verify(vlasnikService, times(1)).deleteVlasnik(id);
	}

	@Test
	void testDeleteVlasnikNotFound() throws Exception {
		Long id = 2L;
		when(vlasnikRepository.existsById(id)).thenReturn(false);

		mockMvc.perform(delete("/korisnik/vlasnik/{id}", id))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.message").value("Cannot find user by id"));
	}

	@Test
	void testInvalidEmailFormatForIgrac() throws Exception {
		String invalidEmail = "invalid-email";

		mockMvc.perform(get("/korisnik/igrac/{email}", invalidEmail))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testEmptyEmailForVlasnik() throws Exception {
		String emptyEmail = "";

		mockMvc.perform(get("/korisnik/vlasnik/{email}", emptyEmail))
				.andExpect(status().isBadRequest());
	}
}
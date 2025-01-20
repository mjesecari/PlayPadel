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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"FRONTEND_REGISTER=http://localhost:3000/register"})
public class PlayPadelApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(PlayPadelApplicationTests.class);

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
	void testGetIgracByEmailSuccess() throws Exception {
		String email = "test@example.com";
		Igrac igrac = new Igrac();
		igrac.setEmail(email);
		when(igracRepository.findByEmail(email)).thenReturn(Optional.of(igrac));

		logger.info("Testing getIgracByEmailSuccess with email: {}", email);

		mockMvc.perform(get("/korisnik/igrac/{email}", email))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value(email));

		logger.info("testGetIgracByEmailSuccess passed");
	}

	@Test
	@WithMockUser
	void testGetIgracByEmailNotFound() throws Exception {
		String email = "nonexistent@example.com";
		when(igracRepository.findByEmail(email)).thenReturn(Optional.empty());

		logger.info("Testing testGetIgracByEmailNotFound with email: {}", email);

		mockMvc.perform(get("/korisnik/igrac/{email}", email))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Cannot find user email"));

		logger.info("testGetIgracByEmailNotFound passed");
	}

	@Test
	@WithMockUser
	void testDeleteIgracSuccess() throws Exception {
		Long id = 1L;
		when(igracRepository.existsById(id)).thenReturn(true);

		logger.info("Testing testDeleteIgracSuccess with id: {}", id);

		mockMvc.perform(delete("/korisnik/igrac/{id}", id))
				.andExpect(status().isOk());

		verify(igracService, times(1)).deleteIgrac(id);

		logger.info("testDeleteIgracSuccess passed");
	}

	@Test
	@WithMockUser
	void testDeleteIgracNotFound() throws Exception {
		Long id = 1L;
		when(igracRepository.existsById(id)).thenReturn(false);

		logger.info("Testing testDeleteIgracNotFound with id: {}", id);

		mockMvc.perform(delete("/korisnik/igrac/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Cannot find user by id"));

		logger.info("testDeleteIgracNotFound passed");
	}

	@Test
	@WithMockUser
	void testGetVlasnikByEmailSuccess() throws Exception {
		String email = "vlasnik@example.com";
		Vlasnik vlasnik = new Vlasnik();
		vlasnik.setEmail(email);
		when(vlasnikRepository.findByEmail(email)).thenReturn(Optional.of(vlasnik));

		logger.info("Testing testGetVlasnikByEmailSuccess with email: {}", email);

		mockMvc.perform(get("/korisnik/vlasnik/{email}", email))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value(email));

		logger.info("testGetVlasnikByEmailSuccess passed");
	}

	@Test
	@WithMockUser
	void testGetVlasnikByEmailNotFound() throws Exception {
		String email = "nonexistent@example.com";
		when(vlasnikRepository.findByEmail(email)).thenReturn(Optional.empty());

		logger.info("Testing testGetVlasnikByEmailNotFound with email: {}", email);

		mockMvc.perform(get("/korisnik/vlasnik/{email}", email))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Cannot find user email"));

		logger.info("testGetVlasnikByEmailNotFound passed");
	}

	@Test
	@WithMockUser
	void testDeleteVlasnikSuccess() throws Exception {
		Long id = 2L;
		when(vlasnikRepository.existsById(id)).thenReturn(true);

		logger.info("Testing testDeleteVlasnikSuccess with id: {}", id);

		mockMvc.perform(delete("/korisnik/vlasnik/{id}", id))
				.andExpect(status().isOk());

		verify(vlasnikService, times(1)).deleteVlasnik(id);

		logger.info("testDeleteVlasnikSuccess passed");
	}

	@Test
	@WithMockUser
	void testDeleteVlasnikNotFound() throws Exception {
		Long id = 2L;
		when(vlasnikRepository.existsById(id)).thenReturn(false);

		logger.info("Testing testDeleteVlasnikNotFound with id: {}", id);

		mockMvc.perform(delete("/korisnik/vlasnik/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Cannot find user by id"));

		logger.info("testDeleteVlasnikNotFound passed");
	}

	@Test
	@WithMockUser
	void testInvalidEmailFormatForIgrac() throws Exception {
		String invalidEmail = "invalid-email";

		logger.info("Testing testInvalidEmailFormatForIgrac with email: {}", invalidEmail);

		mockMvc.perform(get("/korisnik/igrac/{email}", invalidEmail))
				.andExpect(status().isBadRequest());

		logger.info("testInvalidEmailFormatForIgrac passed");
	}

	@Test
	@WithMockUser
	void testEmptyEmailForVlasnik() throws Exception {
		String emptyEmail = "";

		logger.info("Testing testEmptyEmailForVlasnik with email: {}", emptyEmail);

		mockMvc.perform(get("/korisnik/vlasnik/{email}", emptyEmail))
				.andExpect(status().isBadRequest());

		logger.info("testEmptyEmailForVlasnik passed");
	}
}
package fer.progi.mjesecari.ppadel.api;

import fer.progi.mjesecari.ppadel.api.dto.IgracDTO;
import fer.progi.mjesecari.ppadel.domain.Igrac;
import fer.progi.mjesecari.ppadel.service.IgracService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"FRONTEND_REGISTER=http://localhost:3000/register"})
public class IgracControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IgracService igracService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        Mockito.reset(igracService);
    }

    @Test
    void createIgracTest() throws Exception {
        IgracDTO igracDTO = new IgracDTO();
        igracDTO.setRole("igrač");
        igracDTO.setEmail("test@example.com");
        igracDTO.setImeIgrac("Test");
        igracDTO.setPrezimeIgrac("User");
        igracDTO.setBrojTel("123456789");

        Igrac igrac = new Igrac();
        igrac.setTip(igracDTO.getRole());
        igrac.setEmail(igracDTO.getEmail());
        igrac.setImeIgrac(igracDTO.getImeIgrac());
        igrac.setPrezimeIgrac(igracDTO.getPrezimeIgrac());
        igrac.setBrojTel(igracDTO.getBrojTel());

        Mockito.when(igracService.createIgrac(any(Igrac.class))).thenReturn(igrac);

        mockMvc.perform(post("/igrac")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(igracDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.imeIgrac").value("Test"))
                .andExpect(jsonPath("$.prezimeIgrac").value("User"))
                .andExpect(jsonPath("$.brojTel").value("123456789"));
    }

    @Test
    void readIgracEmail() {
    }

    @Test
    void readIgracID() {
    }
}
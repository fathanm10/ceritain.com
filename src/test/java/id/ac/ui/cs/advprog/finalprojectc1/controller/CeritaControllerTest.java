package id.ac.ui.cs.advprog.finalprojectc1.controller;

import id.ac.ui.cs.advprog.finalprojectc1.service.AppUserService;
import id.ac.ui.cs.advprog.finalprojectc1.service.CeritaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CeritaController.class)
class CeritaControllerTest {
    private static final String mainUser = "fathan muhammad";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CeritaServiceImpl ceritaService;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(mainUser)
    void testDeleteCerita() throws Exception {
        String ceritaId = "0";
        mockMvc.perform(get("/cerita/delete-cerita/"+ceritaId))
                .andExpect(status().is3xxRedirection())
                .andExpect((handler().methodName("deleteCerita")));
        verify(ceritaService).deleteCeritaFromAllReadingList(ceritaId);
        verify(ceritaService).deleteCerita(ceritaId);
    }
}

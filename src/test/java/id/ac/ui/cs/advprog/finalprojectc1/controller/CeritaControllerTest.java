package id.ac.ui.cs.advprog.finalprojectc1.controller;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.repository.CeritaRepository;
import id.ac.ui.cs.advprog.finalprojectc1.service.AppUserService;
import id.ac.ui.cs.advprog.finalprojectc1.service.CeritaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private CeritaRepository ceritaRepository;

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
    void testGetCreateCeritaURL() throws Exception {
        mockMvc.perform(get("/cerita/add-cerita"))
                .andExpect(status().is2xxSuccessful())
                .andExpect((handler().methodName("createCerita")))
                .andExpect(model().attributeExists("newCerita"))
                .andExpect(view().name("add_cerita"));
    }

    @Test
    @WithMockUser(mainUser)
    void testPostCeritaURL() throws Exception {
        mockMvc.perform(post("/cerita/add-cerita")
                        .param("judulCerita", "MockJudul")
                        .param("isiCerita", "MockIsi")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect((handler().methodName("createCerita")));

        verify(ceritaService, times(1)).createCerita("MockJudul", "MockIsi");
    }

    @Test
    @WithMockUser(mainUser)
    void testGetCeritaByIDURL() throws Exception {
        Cerita cerita = Mockito.mock(Cerita.class);
        when(ceritaService.getCeritaById(any(String.class))).thenReturn(cerita);

        mockMvc.perform(get("/cerita/" + cerita.getId())
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("cerita"))
                .andExpect(model().attributeExists("currentUsername"))
                .andExpect(view().name("cerita"))
                .andExpect((handler().methodName("getCerita")));
    }

    @Test
    @WithMockUser(mainUser)
    void testGetUpdateCeritaURL() throws Exception {
        Cerita cerita = Mockito.mock(Cerita.class);
        when(ceritaService.getCeritaById(any(String.class))).thenReturn(cerita);

        mockMvc.perform(get("/cerita/edit-cerita/" + cerita.getId())
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("cerita"))
                .andExpect(view().name("edit_cerita"))
                .andExpect((handler().methodName("updateCerita")));
    }

    @Test
    @WithMockUser(mainUser)
    void testPostUpdateCeritaURL() throws Exception {
        String ceritaId = "7a72a23c-4beb-49e1-8092-89b75804b50b";
        mockMvc.perform(post("/cerita/edit-cerita/" + ceritaId)
                        .param("judulCerita", "MockJudul")
                        .param("isiCerita", "MockIsi"))
                .andExpect(status().is3xxRedirection())
                .andExpect((handler().methodName("updateCerita")));

        verify(ceritaService, times(1)).updateCerita(ceritaId, "MockJudul", "MockIsi");
    }

    @Test
    @WithMockUser(mainUser)
    void testDeleteCerita() throws Exception {
        String ceritaId = "7a72a23c-4beb-49e1-8092-89b75804b50b";
        mockMvc.perform(get("/cerita/delete-cerita/" + ceritaId))
                .andExpect(status().is3xxRedirection())
                .andExpect((handler().methodName("deleteCerita")));
        verify(ceritaService).deleteCeritaFromAllReadingList(ceritaId);
        verify(ceritaService).deleteCerita(ceritaId);
    }
}

package id.ac.ui.cs.advprog.finalprojectc1.controller;

import id.ac.ui.cs.advprog.finalprojectc1.service.AppUserService;
import id.ac.ui.cs.advprog.finalprojectc1.service.CeritaService;
import id.ac.ui.cs.advprog.finalprojectc1.service.ReadingListService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserRegistrationController.class)
class UserRegistrationControllerTest {
    private static final String mainUser = "fathan muhammad";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRegistrationController userRegistrationController;

    @MockBean
    private CeritaService ceritaService;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @WithMockUser(mainUser)
    void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("login")))
                .andExpect(view().name("login"));
    }



}
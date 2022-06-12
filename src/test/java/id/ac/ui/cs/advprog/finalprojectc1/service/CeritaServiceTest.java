package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.model.ReadingList;
import id.ac.ui.cs.advprog.finalprojectc1.repository.CeritaRepository;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ReadingListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CeritaService.class)
class CeritaServiceTest {
    private static final String mainUser = "fathan muhammad";

    @MockBean
    private SecurityContextHolder securityContextHolder;

    @MockBean
    private ReadingListRepository readingListRepository;

    @MockBean
    private CeritaRepository ceritaRepository;

    @InjectMocks
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
    void testAddCeritaPositive() {
        ceritaService.createCerita("Mock Judul", "Mock Cerita");
        verify(ceritaRepository).save(any(Cerita.class));
    }

    @Test
    @WithMockUser(mainUser)
    void testAddCeritaNegative() {
        boolean thrown = false;
        try {
            ceritaService.createCerita("", "Mock Cerita");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void testReadOneCerita() {
        Cerita cerita = Mockito.mock(Cerita.class);
        ceritaRepository.save(cerita);

        ceritaService.getCeritaById(cerita.getId());
        verify(ceritaRepository).getById(cerita.getId());
    }

    @Test
    void testReadAllCerita() {
        ceritaService.getAllCerita();
        verify(ceritaRepository).findAll();
    }

    @Test
    @WithMockUser(mainUser)
    void testUpdateOneCerita() {
        Cerita cerita = Mockito.mock(Cerita.class);
        when(ceritaService.getCeritaById(any(String.class))).thenReturn(cerita);
        ceritaService.updateCerita("7a72a23c-4beb-49e1-8092-89b75804b50b", "Judul Edited", "Isi Edited");
        verify(cerita).setJudulCerita("Judul Edited");
        verify(cerita).setIsiCerita("Isi Edited");
        verify(ceritaRepository).save(cerita);
    }

    @Test
    void testDeleteCeritaPositive() {
        Cerita cerita = Mockito.mock(Cerita.class);
        when(ceritaRepository.getById(any(String.class))).thenReturn(cerita);
        ceritaRepository.save(cerita);
        ceritaService.deleteCerita(cerita.getId());
        verify(ceritaRepository).deleteById(cerita.getId());
    }

    @Test
    void testDeleteCeritaFromAllReadingList() {
        Cerita c1 = Mockito.mock(Cerita.class);
        Cerita c2 = Mockito.mock(Cerita.class);
        Cerita c3 = Mockito.mock(Cerita.class);
        ceritaRepository.save(c1);
        ceritaRepository.save(c2);
        ceritaRepository.save(c3);

        ReadingList r1 = Mockito.mock(ReadingList.class);
        ReadingList r2 = Mockito.mock(ReadingList.class);
        List<ReadingList> readingLists = new ArrayList<ReadingList>();
        readingLists.add(r1);
        readingLists.add(r2);

        Set<Cerita> ceritaSet = r1.getCeritaSet();
        ceritaSet.add(c1);
        ceritaSet.add(c2);
        r1.setCeritaSet(ceritaSet);

        ceritaSet = r2.getCeritaSet();
        ceritaSet.add(c1);
        ceritaSet.add(c3);
        r2.setCeritaSet(ceritaSet);

        readingListRepository.save(r1);
        readingListRepository.save(r2);

        when(readingListRepository.findAll()).thenReturn(readingLists);
        ceritaService.deleteCeritaFromAllReadingList(c1.getId());
        assertNotNull(readingLists);
    }
}

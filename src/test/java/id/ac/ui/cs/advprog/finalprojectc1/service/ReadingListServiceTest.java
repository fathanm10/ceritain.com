package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.model.ReadingList;
import id.ac.ui.cs.advprog.finalprojectc1.repository.CeritaRepository;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ReadingListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = ReadingListService.class)
class ReadingListServiceTest {
    private static final String mainUser = "fathan muhammad";

    @MockBean
    private SecurityContextHolder securityContextHolder;

    @MockBean
    private ReadingListRepository readingListRepository;

    @MockBean
    private CeritaRepository ceritaRepository;

    @InjectMocks
    private ReadingListServiceImpl readingListService;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetReadingListById() {
        ReadingList readingList = ReadingList.builder()
                .judul("judul")
                .deskripsi("deskripsi")
                .creator(mainUser)
                .build();
        when(readingListRepository.getById(0)).thenReturn(readingList);
        readingListService.getReadingListById(0);
        verify(readingListRepository).getById(0);
    }

    @Test
    void testUpdateReadingList() {
        ReadingList readingList = Mockito.mock(ReadingList.class);
        when(readingListService.getReadingListById(anyInt())).thenReturn(readingList);
        readingListService.updateReadingList(0, "new judul", "new deskripsi");
        verify(readingList).setJudul("new judul");
        verify(readingList).setDeskripsi("new deskripsi");
        verify(readingListRepository).save(readingList);
    }

    @Test
    void testDeleteReadingList() {
        ReadingList readingList = Mockito.mock(ReadingList.class);
        when(readingListService.getReadingListById(0)).thenReturn(readingList);
        readingListService.deleteReadingList(0);
        verify(readingListRepository).delete(readingList);
    }

    @Test
    void testGetAllReadingList() {
        readingListService.getAllReadingList();
        verify(readingListRepository).findAll();
    }

    @Test
    void testUpdateCerita() {
        Cerita cerita = Mockito.mock(Cerita.class);
        Set<Cerita> ceritaSet = Mockito.mock(Set.class);
        ReadingList readingList = Mockito.mock(ReadingList.class);
        ceritaRepository.save(cerita);

        when(ceritaRepository.findById("0")).thenReturn(Optional.of(cerita));
        when(readingListService.getReadingListById(0)).thenReturn(readingList);
        when(readingList.getCeritaSet()).thenReturn(ceritaSet);

        readingListService.updateCerita(0,"0","add");
        readingListService.updateCerita(0,"0","remove");

        verify(readingList, times(2)).getCeritaSet();
        verify(readingList, times(2)).setCeritaSet(anySet());
        verify(ceritaSet).add(any(Cerita.class));
        verify(ceritaSet).remove(any(Cerita.class));

        verify(ceritaRepository, times(2)).findById("0");
        verify(readingListRepository,times(2)).save(readingList);
    }


    @Test
    @WithMockUser(mainUser)
    void testGetCurrentUsername() {
        String username = readingListService.getCurrentUsername();
        assertEquals(mainUser, username);
    }

    @Test
    @WithMockUser(mainUser)
    void testMatchCreatorWithUserPositive() {
        ReadingList readingList = ReadingList.builder()
                .judul("judul")
                .deskripsi("deskripsi")
                .creator(mainUser)
                .build();
        boolean match = readingListService.matchCreatorWithUser(readingList);
        assertEquals(true, match);
    }

    @Test
    @WithMockUser(mainUser)
    void testMatchCreatorWithUserNegative() {
        ReadingList readingList = ReadingList.builder()
                .judul("judul")
                .deskripsi("deskripsi")
                .creator("otherUser")
                .build();
        boolean match = readingListService.matchCreatorWithUser(readingList);
        assertEquals(false, match);
    }

    @Test
    @WithMockUser(mainUser)
    void testGetAllUserReadingList() {
        List<ReadingList> readingLists = new ArrayList<ReadingList>();
        for(int i = 0; i < 10; i++) {
            ReadingList readingList = ReadingList.builder()
                    .judul("judul"+i)
                    .deskripsi("deskripsi"+i)
                    .creator(mainUser)
                    .build();
            readingListRepository.save(readingList);
            readingLists.add(readingList);
        }
        when(readingListRepository.findAll()).thenReturn(readingLists);
        assertNotNull(readingListService.getAllUserReadingList());
    }

    @Test
    @WithMockUser(mainUser)
    void testCreateReadingList() {
        ReadingList readingList = Mockito.mock(ReadingList.class);
        when(readingListRepository.save(any(ReadingList.class))).thenReturn(readingList);
        readingListService.createReadingList("judul","deskripsi");
        assertNotNull(readingList);
    }
}

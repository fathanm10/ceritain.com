package id.ac.ui.cs.advprog.finalprojectc1.controller;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.model.ReadingList;
import id.ac.ui.cs.advprog.finalprojectc1.service.AppUserService;
import id.ac.ui.cs.advprog.finalprojectc1.service.CeritaService;
import id.ac.ui.cs.advprog.finalprojectc1.service.ReadingListService;
import org.junit.jupiter.api.Test;
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
@WebMvcTest(controllers = ReadingListController.class)
class ReadingListControllerTest {
    private static final String mainUser = "fathan muhammad";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReadingListService readingListService;

    @MockBean
    private CeritaService ceritaService;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @WithMockUser(mainUser)
    void testGetReadingListURL() throws Exception {
        mockMvc.perform(get("/reading-list"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("readingList")))
                .andExpect(model().attributeExists("allReadingList"))
                .andExpect(model().attributeExists("allUserReadingList"))
                .andExpect(view().name("reading_list"));

        verify(readingListService, times(1)).getAllReadingList();
        verify(readingListService, times(1)).getAllUserReadingList();
    }

    @Test
    @WithMockUser(mainUser)
    void testGetCreateReadingListURL() throws Exception {
        mockMvc.perform(get("/reading-list/create"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("createReadingList")))
                .andExpect(model().attributeExists("newReadingList"))
                .andExpect(view().name("reading_list_create"));
    }

    @Test
    @WithMockUser(mainUser)
    void testPostCreateReadingListURL() throws Exception {
        String judul = "title";
        String deskripsi = "desc";
        ReadingList mockReadingList = ReadingList.builder()
                .judul(judul)
                .deskripsi(deskripsi)
                .creator(mainUser)
                .build();

        when(readingListService.createReadingList(judul,deskripsi)).thenReturn(mockReadingList);

        mockMvc.perform(post("/reading-list/create")
                .param("judul",judul)
                .param("deskripsi",deskripsi))
                .andExpect(status().is3xxRedirection());

        verify(readingListService, times(1)).createReadingList(judul,deskripsi);
    }

    @Test
    @WithMockUser(mainUser)
    void testGetEditReadingListURLMatchFlagFalse() throws Exception {
        ReadingList mockReadingList = ReadingList.builder()
                .judul("judul")
                .deskripsi("deskripsi")
                .creator(mainUser)
                .build();

        when(readingListService.getReadingListById(0)).thenReturn(mockReadingList);

        mockMvc.perform(get("/reading-list/edit/0"))
                .andExpect(status().is3xxRedirection())
                .andExpect((handler().methodName("editReadingList")));

        verify(readingListService, times(1)).getReadingListById(0);
        verify(readingListService, times(1)).matchCreatorWithUser(mockReadingList);
    }

    @Test
    @WithMockUser(mainUser)
    void testGetEditReadingListURLMatchFlagTrue() throws Exception {
        ReadingList mockReadingList = ReadingList.builder()
                .judul("judul")
                .deskripsi("deskripsi")
                .creator(mainUser)
                .build();

        when(readingListService.getReadingListById(0)).thenReturn(mockReadingList);
        when(readingListService.matchCreatorWithUser(mockReadingList)).thenReturn(true);

        mockMvc.perform(get("/reading-list/edit/0"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("editReadingList")));

        verify(readingListService, times(1)).getReadingListById(0);
        verify(readingListService, times(1)).matchCreatorWithUser(mockReadingList);
    }

    @Test
    @WithMockUser(mainUser)
    void testPostEditReadingListURL() throws Exception {
        int readingListId = 0;
        String judul = "judul";
        String deskripsi = "deskripsi";

        mockMvc.perform(post("/reading-list/edit/save")
                        .param("id", String.valueOf(readingListId))
                        .param("judul",judul)
                        .param("deskripsi",deskripsi))
                .andExpect(status().is3xxRedirection())
                .andExpect((handler().methodName("editReadingList")));
        verify(readingListService, times(1)).updateReadingList(readingListId,judul,deskripsi);

        mockMvc.perform(post("/reading-list/edit/delete")
                        .param("id", String.valueOf(readingListId))
                        .param("judul",judul)
                        .param("deskripsi",deskripsi))
                .andExpect(status().is3xxRedirection())
                .andExpect((handler().methodName("editReadingList")));
        verify(readingListService, times(1)).deleteReadingList(readingListId);
    }

    @Test
    @WithMockUser(mainUser)
    void testGetViewReadingListURL() throws Exception {
        ReadingList mockReadingList = new ReadingList();
        when(readingListService.getReadingListById(0)).thenReturn(mockReadingList);
        mockMvc.perform(get("/reading-list/view/0"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("viewReadingList")))
                .andExpect(view().name("reading_list_view"));

        verify(readingListService, times(1)).getReadingListById(0);
        verify(readingListService, times(1)).matchCreatorWithUser(mockReadingList);
    }

    @Test
    @WithMockUser(mainUser)
    void testGetAddCeritaURL() throws Exception {
        Cerita mockCerita = Cerita.builder()
                .judulCerita("judul")
                .isiCerita("isi")
                .id("0")
                .build();
        when(ceritaService.getCeritaById("0")).thenReturn(mockCerita);
        mockMvc.perform(get("/reading-list/add-cerita/0"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("addCerita")))
                .andExpect(model().attributeExists("cerita"))
                .andExpect(model().attributeExists("allUserReadingList"))
                .andExpect(view().name("reading_list_add_cerita"));

        verify(ceritaService, times(1)).getCeritaById("0");
        verify(readingListService, times(1)).getAllUserReadingList();
    }

    @Test
    @WithMockUser(mainUser)
    void testPostAddCeritaURL() throws Exception {
        String ceritaId = "0";
        int readingListId = 0;
        mockMvc.perform(post("/reading-list/add-cerita/"+ceritaId)
                        .param("id", String.valueOf(readingListId)))
                .andExpect(status().is3xxRedirection())
                .andExpect((handler().methodName("addCerita")));

        verify(readingListService, times(1)).updateCerita(readingListId, ceritaId, "add");
    }

    @Test
    @WithMockUser(mainUser)
    void testPostRemoveCeritaURL() throws Exception {
        String ceritaId = "0";
        int readingListId = 0;
        mockMvc.perform(post("/reading-list/remove-cerita/")
                        .param("id", String.valueOf(readingListId))
                        .param("ceritaId",ceritaId))
                .andExpect(status().is3xxRedirection())
                .andExpect((handler().methodName("removeCerita")));

        verify(readingListService, times(1)).updateCerita(readingListId, ceritaId, "remove");
    }
}

package id.ac.ui.cs.advprog.finalprojectc1.service;

import id.ac.ui.cs.advprog.finalprojectc1.model.Profile;
import id.ac.ui.cs.advprog.finalprojectc1.repository.AppUserRepository;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ProfileService.class)
public class ProfileServiceTest {

    @MockBean
    private SecurityContextHolder securityContextHolder;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private ProfileRepository profileRepository;
    
    @InjectMocks
    private ProfileServiceImpl profileService;

    @MockBean
    private AppUserRepository appUserRepository;

    private final String firstName = "firstname";
    private final String lastName = "lastname";
    private final String email = "email@gmail.com";
    private final String username = "username";
    private final String bio = "ini bio";
    private final String linkPhoto = "https://commons.wikimedia.org/wiki/File:Profile_avatar_placeholder_large.png";

    private final Profile mockProfile = new Profile();

    private final String firstNameF = "firstname1";
    private final String lastNameF = "lastname1";
    private final String emailF = "email1@gmail.com";
    private final String usernameF = "username1";
    private final String bioF = "ini bio1";
    private final String linkPhotoF = "https://commons.wikimedia.org/wiki/File:Profile_avatar_placeholder_large1.png";

    Profile mockProfileF = new Profile();


    List<Profile> mockListProfile = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        mockProfile.setFirstName(firstName);
        mockProfile.setLastName(lastName);
        mockProfile.setEmail(email);
        mockProfile.setUsername(username);
        mockProfile.setBio(bio);
        mockProfile.setUrl(linkPhoto);

        mockListProfile.add(mockProfile);

        mockProfileF.setFirstName(firstName);
        mockProfileF.setLastName(lastName);
        mockProfileF.setEmail(email);
        mockProfileF.setUsername(username);
        mockProfileF.setBio(bio);
        mockProfileF.setUrl(linkPhoto);
    }

    @Test
    void testGetProfileById() {


        
        when(profileRepository.getById(0)).thenReturn(mockProfile);
        profileService.getProfileById(0);
        verify(profileRepository).getById(0);
        assertEquals(profileService.getProfileById(0), mockProfile);
        assertNotEquals(profileService.getProfileById(0), mockProfileF);
    }

    @Test
    void testGetEmailWithInputEmail(){
        when(profileRepository.findAll()).thenReturn(mockListProfile);
        profileService.getProfileByEmail(email);
        verify(profileRepository).findAll();
        assertEquals(profileService.getProfileByEmail(email),mockProfile);
        assertEquals(profileService.getProfileByEmail(email),mockProfileF);
    }

    @Test
    void testGetEmailWIthoutInputEmail(){
        when(profileService.getAppuser().getEmail()).thenReturn(email);
        profileService.getProfileByEmail();
        verify(profileService).getProfileByEmail(email);
        verify(profileService).getAppuser();
        assertEquals(profileService.getProfileByEmail(), email);
        assertEquals(profileService.getProfileByEmail(), emailF);
    }
}

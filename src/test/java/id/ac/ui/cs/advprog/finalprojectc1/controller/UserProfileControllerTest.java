package id.ac.ui.cs.advprog.finalprojectc1.controller;

import id.ac.ui.cs.advprog.finalprojectc1.model.Profile;
import id.ac.ui.cs.advprog.finalprojectc1.repository.AppUserRepository;
import id.ac.ui.cs.advprog.finalprojectc1.service.AppUserService;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ProfileRepository;
import id.ac.ui.cs.advprog.finalprojectc1.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserProfileController.class)
class UserProfileControllerTest {
    private final String mainUser = "Rizky Juniastiar";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityContextHolder securityContextHolder;

    @MockBean
    private AppUserService appUserService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private AppUserRepository appUserRepository;

    @Test
    @WithMockUser(mainUser)
    void testGetHomepage() throws Exception {

        String firstName = "firstname";
        String lastName = "lastname";
        String email = "email@gmail.com";
        String username = "username";
        String bio = "ini bio";
        String linkPhoto = "https://commons.wikimedia.org/wiki/File:Profile_avatar_placeholder_large.png";

        Profile mockProfile = new Profile();
        mockProfile.setFirstName(firstName);
        mockProfile.setLastName(lastName);
        mockProfile.setEmail(email);
        mockProfile.setUsername(username);
        mockProfile.setBio(bio);
        mockProfile.setUrl(linkPhoto);

        when(profileService.saveNewProfile()).thenReturn(mockProfile);

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("homepage")))
                .andExpect(model().attributeExists("userLogin"))
                .andExpect(view().name("profile"));

        verify(profileService, times(1)).getProfileByEmail();
        verify(profileService, times(1)).saveNewProfile();
        verify(profileService, times(0)).saveProfileToDB(firstName, lastName, email, username, bio, linkPhoto);
        verify(profileService, times(0)).getProfileByEmail(email);
        verify(profileService, times(0)).getAppuser();

    }

    @Test
    @WithMockUser(mainUser)
    void testPostEditProfile() throws Exception {


        mockMvc.perform(post("/profile"))
                .andExpect((handler().methodName("createProfile")))
                .andExpect(view().name("redirect:/profile/edit"))
                .andExpect(status().is3xxRedirection());

        verify(profileService, times(1)).getProfileByEmail();
        verify(profileService, times(0)).getProfileByEmail("email@gmail.com");
        verify(profileService, times(0)).getUsernameLogin();
        verify(profileService, times(0)).getAppuser();
    }

    @Test
    @WithMockUser(mainUser)
    void testGetEditProfile() throws Exception {

        String firstName = "firstname";
        String lastName = "lastname";
        String email = "email@gmail.com";
        String username = "username";
        String bio = "ini bio";
        String linkPhoto = "https://commons.wikimedia.org/wiki/File:Profile_avatar_placeholder_large.png";

        Profile mockProfile = new Profile();
        mockProfile.setFirstName(firstName);
        mockProfile.setLastName(lastName);
        mockProfile.setEmail(email);
        mockProfile.setUsername(username);
        mockProfile.setBio(bio);
        mockProfile.setUrl(linkPhoto);

        when(profileService.getProfileByEmail()).thenReturn(mockProfile);

        mockMvc.perform(get("/profile/edit"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("editProfile")))
                .andExpect(model().attributeExists("profile"))
                .andExpect(view().name("edit_profile"));

        verify(profileService, times(1)).getProfileByEmail(email);
        verify(profileService, times(0)).getProfileByEmail();
        verify(profileService, times(0)).saveNewProfile();
        verify(profileService, times(0)).saveProfileToDB(firstName, lastName, email, username, bio, linkPhoto);
        verify(profileService, times(0)).getAppuser();

    }

    @Test
    @WithMockUser(mainUser)
    void testPostUpdateProfile() throws Exception {

        String firstName = "firstname";
        String lastName = "lastname";
        String email = "email@gmail.com";
        String username = "username";
        String bio = "ini bio";
        String linkPhoto = "https://commons.wikimedia.org/wiki/File:Profile_avatar_placeholder_large.png";

        mockMvc.perform(post("/profile/edit")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .param("email", email)
                        .param("username", username)
                        .param("bio", bio)
                        .param("linkPhoto", linkPhoto)
                )

                .andExpect((handler().methodName("editProfilePost")))
                .andExpect(view().name("redirect:/profile"))
                .andExpect(status().is3xxRedirection());

        verify(profileService, times(1)).saveProfileToDB(firstName, lastName, email, username, bio, linkPhoto);
    }
}

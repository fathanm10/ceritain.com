package id.ac.ui.cs.advprog.finalprojectc1.controller;

import id.ac.ui.cs.advprog.finalprojectc1.model.Profile;
import id.ac.ui.cs.advprog.finalprojectc1.repository.AppUserRepository;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ProfileRepository;
import id.ac.ui.cs.advprog.finalprojectc1.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/profile")
public class UserProfileController {

    private static final String PROFILE = "profile";

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping(value = "")
    public String homepage(Model model){
        Profile profile = profileService.getProfileByEmail();

        if (profile == null)
            profile = profileService.saveNewProfile();
        model.addAttribute("userLogin", profile);
        return PROFILE;
    }

    @PostMapping(value = "")
    public String createProfile(Model model){
        model.addAttribute("userLoggedIn", profileService.getProfileByEmail());

        return "redirect:/profile/edit";
    }

    @GetMapping(value = "/edit")
    public String editProfile(Model model){
        model.addAttribute(PROFILE, profileService.getProfileByEmail());
        return "edit_profile";
    }

    @PostMapping(value = "/edit")
    public String editProfilePost(@RequestParam(value = "linkPhoto") String linkPhoto, @RequestParam(value = "firstName") String firstName,
                              @RequestParam(value = "lastName") String lastName, @RequestParam(value = "email") String email,
                              @RequestParam(value = "username") String username, @RequestParam(value = "bio") String bio){
        profileService.saveProfileToDB(firstName, lastName, email, username, bio, linkPhoto);
        return "redirect:/profile";
    }


}


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
//        model.addAttribute("username", profileService.getUsernameLogin());
        model.addAttribute("userLogin", profileService.getAppuser());
        model.addAttribute("userName", profileService.getAppuser().getName());
        model.addAttribute("userEmail",profileService.getAppuser().getEmail());
        return PROFILE;
    }

    @GetMapping(value = "/{id}")
    public String profilePage(@PathVariable int id, Model model){
        model.addAttribute("user", profileService.getProfileById(id));
        return PROFILE;
    }

    @PostMapping(value = "")
    public String createProfile(Model model){
        model.addAttribute("userLoggedIn", profileService.getAppuser());

        return "redirect:/profile/edit";
    }

    @GetMapping(value = "/edit")
    public String editProfile(Model model){
        model.addAttribute("userLoggedIn", profileService.getAppuser());
        model.addAttribute(PROFILE, new Profile());
        return "edit_profile";
    }

    @PostMapping(value = "/edit")
    public String editProfilePost(@RequestParam(value = "linkPhoto") String linkPhoto, @RequestParam(value = "firstName") String firstName,
                              @RequestParam(value = "lastName") String lastName, @RequestParam(value = "email") String email,
                              @RequestParam(value = "username") String username, @RequestParam(value = "bio") String bio){
        int id = profileService.saveProfileToDB(firstName, lastName, email, username, bio, linkPhoto);
        return "redirect:/profile/" + id;
    }


}


package id.ac.ui.cs.advprog.finalprojectc1.controller;

import id.ac.ui.cs.advprog.finalprojectc1.model.Profile;
import id.ac.ui.cs.advprog.finalprojectc1.repository.AppUserRepository;
import id.ac.ui.cs.advprog.finalprojectc1.repository.ProfileRepository;
import id.ac.ui.cs.advprog.finalprojectc1.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
@RequestMapping(path = "/profile")
public class UserProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping(value = "")
    public String homepage(Model model){

        return "profile";
    }

    @GetMapping(value = "/{id}")
    public String profilePage(@PathVariable int id, Model model){
        model.addAttribute("user", profileService.getProfileById(id));
        return "profile";
    }


//    public String profile(Model model, @RequestParam(value = "file") MultipartFile file, @RequestParam(value = "firstName") String firstName,
//                          @RequestParam(value = "lastName") String lastName, @RequestParam(value = "email") String email,
//                          @RequestParam(value = "username") String username, @RequestParam(value = "bio") String bio){
////
////        return "profile";
//        model.addAttribute("firstName", firstName);
//        model.addAttribute("lastName", lastName);
//        model.addAttribute("email", email);
//        model.addAttribute("user", profileService.getUserByEmail(email));
//        model.addAttribute("username", username);
//        model.addAttribute("bio", bio);
//        model.addAttribute("file", file);
//        return "profile";
//    }

    @PostMapping(value = "")
    public String saveProfile(Model model){
//        model.addAttribute("profile", new Profile());
        return "redirect:/profile/edit";
    }

//    public String saveProfile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "firstName") String firstName,
//                              @RequestParam(value = "lastName") String lastName, @RequestParam(value = "email") String email,
//                              @RequestParam(value = "username") String username, @RequestParam(value = "bio") String bio)
//    {
//        profileService.saveProfileToDB(file, firstName, lastName, email, username, bio);
//        return "redirect:/profile.html";
//    }

//    public RedirectView saveProfile(Profile profile, @RequestParam("image")MultipartFile multipartFile) throws IOException {
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        profile.setPhoto(fileName);
//
//        Profile savedProfile = profileRepository.save(profile);
//
//        String uploadDir = "user-photo/" + savedProfile.getId();
//
//        FileUploadUtil.save
//    }

//    public String showProfile(Model model){
//        return "redirect:/profile";
//    }

    @GetMapping(value = "/edit")
    public String editProfile(Model model){
        model.addAttribute("profile", new Profile());
        return "edit_profile";
    }

    @PostMapping(value = "/edit")
    public String editProfilePost(@RequestParam(value = "firstName") String firstName,
                              @RequestParam(value = "lastName") String lastName, @RequestParam(value = "email") String email,
                              @RequestParam(value = "username") String username, @RequestParam(value = "bio") String bio){
        int id = profileService.saveProfileToDB(firstName, lastName, email, username, bio);
        return "redirect:/profile/" + id;
    }


}


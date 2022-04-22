package id.ac.ui.cs.advprog.finalprojectc1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/profile")
public class ProfileController {

//    @RequestMapping(method = RequestMethod.GET, value = "")
//    private String home(){
//        return "profile";
//    }

    @GetMapping(value = "")
    public String profile(Model model){
        return "profile";
    }

    @PostMapping(value = "")
    public String showProfile(Model model){
        return "redirect:/profile";
    }

    @GetMapping(value = "/edit")
    public String editProfile(Model model){
        return "edit_profile";
    }

    @PostMapping(value = "/edit")
    public String editProfilePost(Model model){
        return "redirect:/profile/edit";
    }


}


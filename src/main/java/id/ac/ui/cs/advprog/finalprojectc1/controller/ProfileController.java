package id.ac.ui.cs.advprog.finalprojectc1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/profile")
public class ProfileController {

    @RequestMapping(method = RequestMethod.GET, value = "")
    private String home(){
        return "profile";
    }
}


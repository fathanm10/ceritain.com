package id.ac.ui.cs.advprog.finalprojectc1.controller;


import id.ac.ui.cs.advprog.finalprojectc1.core.registration.RegistrationRequest;
import id.ac.ui.cs.advprog.finalprojectc1.repository.AppUserRepository;
import id.ac.ui.cs.advprog.finalprojectc1.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class UserRegistrationController {

    private RegistrationService registrationService;
    private AppUserRepository appUserRepository;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/registration")
    public String register(Model model,@Valid RegistrationRequest req) {
        boolean emailExist = appUserRepository.findByEmail(req.getEmail()).isPresent();
        boolean usernameExist = appUserRepository.findByName(req.getName()).isPresent();

        if(emailExist && usernameExist) return  "redirect:/registration?error1&error2";
        else if (emailExist) return "redirect:/registration?error1";
        else if (usernameExist) return "redirect:/registration?error2";

        registrationService.register(req);

        return "redirect:/registration?success";
    }

    @GetMapping("/registration")
    public String register (Model model){
        model.addAttribute("registerReq", new RegistrationRequest());
        return "registration";
    }

    @GetMapping(path = "/registration/confirm/{token}")
    public String confirm(@PathVariable(value = "token") String token){
        registrationService.confirmToken(token);

        return "redirect:/login?success";

    }


}
 
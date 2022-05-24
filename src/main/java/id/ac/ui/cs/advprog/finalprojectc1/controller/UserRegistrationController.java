package id.ac.ui.cs.advprog.finalprojectc1.controller;


import id.ac.ui.cs.advprog.finalprojectc1.core.registration.RegistrationRequest;
import id.ac.ui.cs.advprog.finalprojectc1.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class UserRegistrationController {

    private RegistrationService registrationService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/registration")
    public String register(Model model,@Valid RegistrationRequest req, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            model.addAttribute("registerReq",req);
            return "registration";

        }
        registrationService.register(req);
        return "redirect:/registration?success";

    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/registration")
    public String register (Model model){
        model.addAttribute("registerReq", new RegistrationRequest());
        return "registration";
    }

    @GetMapping(path = "/registration/confirm/{token}")
    public String confirm(@PathVariable(value = "token") String token){
        registrationService.confirmToken(token);
        return "redirect:/login";
    }

    @GetMapping( path = "/logged")
    public String logged() {
        return "login_success";
    }


}
 
package id.ac.ui.cs.advprog.finalprojectc1.controller;

import id.ac.ui.cs.advprog.finalprojectc1.core.registration.RegistrationRequest;
import id.ac.ui.cs.advprog.finalprojectc1.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "cerita/registration")
@AllArgsConstructor
public class UserRegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public String register (@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path = "/confirm/{token}")
    public String confirm(@PathVariable(value = "token") String token){
        return registrationService.confirmToken(token);
    }

    @GetMapping( path = "/logged")
    public String logged() {
        return "Login successful";
    }


}
 
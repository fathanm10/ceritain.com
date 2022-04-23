package id.ac.ui.cs.advprog.finalprojectc1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cerita")
public class CeritaController {
    @GetMapping(value = "/add-cerita")
    public String createCerita(Model model) {
        return "add_cerita";
    }

    @GetMapping(value = "/edit-cerita")
    public String editCerita(Model model) {
        return "edit_cerita";
    }

    @GetMapping(value = "/dummy-cerita")
    public String readCerita(Model model) {
        return "cerita";
    }

}

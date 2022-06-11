package id.ac.ui.cs.advprog.finalprojectc1.controller;

import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.service.CeritaService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@Controller
@RequestMapping("/cerita")
public class CeritaController {

    @Autowired
    private CeritaService ceritaService;

    final Logger logger = LoggerFactory.getLogger(CeritaController.class);

    private static final String CERITASTRING = "cerita";

    @GetMapping()
    public String ceritaLandingPage(Model model) {
        model.addAttribute("AllCerita", ceritaService.getAllCerita());
        model.addAttribute("UserCerita", ceritaService.getAllUserCerita());
        return "all_cerita";
    }

    @GetMapping(value = "/{ceritaId}")
    public String getCerita(@PathVariable String ceritaId, Model model) {
        var cerita = ceritaService.getCeritaById(ceritaId);
        model.addAttribute(CERITASTRING, cerita);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = ((UserDetails) principal).getUsername();
        model.addAttribute("currentUsername", currentUsername);
        return CERITASTRING;
    }

    @GetMapping(value = "/add-cerita")
    public String createCerita(Model model) {
        model.addAttribute("newCerita", new Cerita());
        return "add_cerita";
    }

    @PostMapping(value = "/add-cerita")
    public String createCerita(@RequestParam String judulCerita, @RequestParam String isiCerita) {
        try {
            ceritaService.createCerita(judulCerita, isiCerita);
        } catch (Exception e) {
            logger.info(e.toString());
        }
        return "redirect:/cerita";


    }

    @GetMapping(value = "delete-cerita/{ceritaId}")
    public String deleteCerita(@PathVariable String ceritaId) {
        try {
            ceritaService.deleteCeritaFromAllReadingList(ceritaId);
            ceritaService.deleteCerita(ceritaId);
        } catch (Exception e) {
            logger.info(e.toString());
        }
        return "redirect:/cerita";

    }

    @GetMapping(value = "/edit-cerita/{ceritaId}")
    public String updateCerita(@PathVariable String ceritaId, Model model) {
        var cerita = ceritaService.getCeritaById(ceritaId);
        model.addAttribute(CERITASTRING, cerita);
        return "edit_cerita";
    }

    @PostMapping(value = "/edit-cerita/{ceritaId}")
    public String updateCerita(@PathVariable String ceritaId,
                               @RequestParam String judulCerita,
                               @RequestParam String isiCerita) {
        ceritaService.updateCerita(ceritaId, judulCerita, isiCerita);
        return "redirect:/cerita/" + ceritaId;
    }

}

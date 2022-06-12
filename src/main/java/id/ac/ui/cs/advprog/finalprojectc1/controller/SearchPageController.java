package id.ac.ui.cs.advprog.finalprojectc1.controller;
import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.service.SearchPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/search-page")
public class SearchPageController {

    @Autowired
    private SearchPageService searchPageService;

    @GetMapping(value = "")
    public String home(Cerita cerita, Model model, String keyword) {
        if(keyword != null) {
            List<Cerita> list = searchPageService.getByKeyword(keyword);
            model.addAttribute("list", list);
        }
        else {
            List<Cerita> list = searchPageService.getAllCerita();
            model.addAttribute("list", list);
        }
        return "searchpage";
    }
}

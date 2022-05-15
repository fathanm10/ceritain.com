package id.ac.ui.cs.advprog.finalprojectc1.controller;
import id.ac.ui.cs.advprog.finalprojectc1.model.Cerita;
import id.ac.ui.cs.advprog.finalprojectc1.service.SearchPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/search-page")
public class SearchPageController {

    @Autowired
    private SearchPageService searchPageService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    private String home(Model model, @Param("keyword") String keyword) {
        List<Cerita> ceritaList = searchPageService.findAllCerita(keyword);
        model.addAttribute("ceritaList", ceritaList);
        model.addAttribute("keyword", keyword);
        return "searchpage";
    }
}

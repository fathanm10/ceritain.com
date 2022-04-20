package id.ac.ui.cs.advprog.finalprojectc1.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/search-page")
public class SearchPageController {

    @RequestMapping(method = RequestMethod.GET, value = "")
    private String home() {
        return "searchpage";
    }



}

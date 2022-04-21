package id.ac.ui.cs.advprog.finalprojectc1.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/reading-list")
public class ReadingListController {
//    @RequestMapping(method = RequestMethod.GET, value = "/")
//    @ResponseBody
//    private String helloWorld() {
//        return "Reading List Landing Page";
//    }

    @GetMapping(value = "/")
    public String readingList(Model model) {
        return "reading_list";
    }

    @GetMapping(value = "/create")
    public String createReadingList(Model model) {
        return "reading_list_create";
    }

    @GetMapping(value = "/edit")
    public String editReadingList(Model model) {
        return "reading_list_edit";
    }

    @GetMapping(value = "/add-cerita")
    public String addCerita(Model model) {
        return "reading_list_add_cerita";
    }

}

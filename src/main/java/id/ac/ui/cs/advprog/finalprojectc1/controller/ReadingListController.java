package id.ac.ui.cs.advprog.finalprojectc1.controller;
import id.ac.ui.cs.advprog.finalprojectc1.core.ReadingList;
import id.ac.ui.cs.advprog.finalprojectc1.service.ReadingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/reading-list")
public class ReadingListController {

    @Autowired
    private ReadingListService readingListService;

    @GetMapping({"/",""})
    public String readingList(Model model) {
        return "reading_list";
    }

    @GetMapping(value = "/create")
    public String createReadingList(Model model) {
        model.addAttribute("newReadingList", new ReadingList());
        return "reading_list_create";
    }

    @PostMapping(value = "/new-readinglist")
    public String createReadingList(@RequestParam String judul,
                                    @RequestParam String deskripsi) {
        var readinglist = readingListService.createReadingList(judul,deskripsi);
        return String.format("redirect:/reading-list/view/%d",readinglist.getId());
    }

    @GetMapping(value = "/edit/{readinglistId}")
    public String editReadingList(@PathVariable int readinglistId,
                                  Model model) {
        var readingList = readingListService.getReadingListById(readinglistId);
        model.addAttribute("readingList", readingList);
        return "reading_list_edit";
    }

    @GetMapping(value = "/view/{readinglistId}")
    public String viewReadingList(@PathVariable(required=false) int readinglistId,
                                  Model model) {
        var readingList = readingListService.getReadingListById(readinglistId);
        model.addAttribute("readingList", readingList);
        System.out.println(readingList.getId());
        System.out.println(readingList.getJudul());
        System.out.println(readingList.getDeskripsi());
        return "reading_list_view";
    }

    @GetMapping(value = "/add-cerita")
    public String addCerita(Model model) {
        return "reading_list_add_cerita";
    }

}

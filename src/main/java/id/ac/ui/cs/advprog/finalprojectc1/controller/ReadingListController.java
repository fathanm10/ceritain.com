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

    @PostMapping(value = "/create")
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

    @PostMapping(value = "/edit/{command}")
    public String editReadingList(@PathVariable String command,
                                  @RequestParam (value="id") int readinglistId,
                                  @RequestParam (value="judul") String judul,
                                  @RequestParam (value="deskripsi") String deskripsi,
                                  Model model) {
        ReadingList readingList = null;
        if (command.equals("save")) {
            readingList = readingListService.updateReadingList(readinglistId,judul,deskripsi);
        } else if (command.equals("delete")) {
            readingListService.deleteReadingList(readinglistId);
        }
        model.addAttribute("readingList", readingList);
        return "redirect:/reading-list/add-cerita";
    }

    @GetMapping(value = "/view/{readinglistId}")
    public String viewReadingList(@PathVariable(required=false) int readinglistId,
                                  Model model) {
        var readingList = readingListService.getReadingListById(readinglistId);
        model.addAttribute("readingList", readingList);
        return "reading_list_view";
    }

    @GetMapping(value = "/add-cerita")
    public String addCerita(Model model) {
        var allReadingList = readingListService.getAllReadingList();
        model.addAttribute("allReadingList", allReadingList);
        return "reading_list_add_cerita";
    }

}

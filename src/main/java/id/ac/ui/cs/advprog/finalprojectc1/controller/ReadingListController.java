package id.ac.ui.cs.advprog.finalprojectc1.controller;
import id.ac.ui.cs.advprog.finalprojectc1.model.ReadingList;
import id.ac.ui.cs.advprog.finalprojectc1.service.CeritaService;
import id.ac.ui.cs.advprog.finalprojectc1.service.ReadingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/reading-list")
public class ReadingListController {

    private static final String READINGLISTMODEL = "readingList";
    @Autowired
    private ReadingListService readingListService;

    @Autowired
    private CeritaService ceritaService;

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
        model.addAttribute(READINGLISTMODEL, readingList);
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
        model.addAttribute(READINGLISTMODEL, readingList);
        return "redirect:/reading-list/add-cerita";
    }

    @GetMapping(value = "/view/{readinglistId}")
    public String viewReadingList(@PathVariable(required=false) int readinglistId,
                                  Model model) {
        var readingList = readingListService.getReadingListById(readinglistId);
        model.addAttribute(READINGLISTMODEL, readingList);
        return "reading_list_view";
    }

    @GetMapping(value = "/add-cerita/{ceritaId}")
    public String addCerita(@PathVariable String ceritaId,
                            Model model) {
        var cerita = ceritaService.getCeritaById(ceritaId);
        var allReadingList = readingListService.getAllReadingList();
        model.addAttribute("cerita", cerita);
        model.addAttribute("allReadingList", allReadingList);
        return "reading_list_add_cerita";
    }

    @PostMapping(value = "/add-cerita/{ceritaId}")
    public String addCerita(@PathVariable String ceritaId,
                            @RequestParam (value="id") int readinglistId) {
        readingListService.updateCerita(readinglistId, ceritaId, "add");
        return "redirect:/reading-list/view/"+readinglistId;
    }

    @PostMapping(value = "/remove-cerita")
    public String removeCerita(@RequestParam (value="id") int readinglistId,
                               @RequestParam (value="ceritaId") String ceritaId) {
        readingListService.updateCerita(readinglistId, ceritaId, "remove");
        return "redirect:/reading-list/view/"+readinglistId;
    }
}

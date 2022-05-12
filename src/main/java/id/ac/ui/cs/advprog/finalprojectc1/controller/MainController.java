package id.ac.ui.cs.advprog.finalprojectc1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @RequestMapping(method = RequestMethod.GET, value = "/")
    @ResponseBody
    private String helloWorld() {
        return "Hello World";
    }
}


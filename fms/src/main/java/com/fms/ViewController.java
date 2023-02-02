package com.fms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping({"/fares", "/routes", "/flights", "/available-flights"})
    public String index() { return "forward:/index.html"; }
}

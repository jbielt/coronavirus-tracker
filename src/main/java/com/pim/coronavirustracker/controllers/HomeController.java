package com.pim.coronavirustracker.controllers;


import com.pim.coronavirustracker.services.CoronaVirusDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class HomeController {

    CoronaVirusDataService coronaVirusDataService;


    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("locationStats", coronaVirusDataService.getAllStats());
        return "home";
    }





}

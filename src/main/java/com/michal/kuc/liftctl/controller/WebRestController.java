package com.michal.kuc.liftctl.controller;

import com.michal.kuc.liftctl.model.CallParams;
import com.michal.kuc.liftctl.model.CarriageInfo;
import com.michal.kuc.liftctl.service.MiddlemanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Controller
@RequestMapping("/")
public class WebRestController {

    @Autowired
    private MiddlemanService middlemanService;

    @GetMapping
    public String showHome(Model model) {
        model.addAttribute("carriages", middlemanService.getAllCarriages());
        model.addAttribute("callFormData", new CallParams());
        model.addAttribute("carFormData", new CarriageInfo());
        middlemanService.initialize();
        return "panel";
    }

    @PostMapping(value = "newCarriage")
    public String createCarriage(@ModelAttribute(name = "carFormData") CarriageInfo formData, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "redirect:/";
        }
        middlemanService.addCarriage(formData);
        return "redirect:/";
    }
    @PostMapping(value = "newCall")
    public String call(@ModelAttribute(name = "callFormData")CallParams formData, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "redirect:/";
        }
        middlemanService.call(formData);
        return "redirect:/";
    }


    @PostMapping(value = "{id}")
    public String updateCarriageById(@PathVariable BigInteger id,
                                     @ModelAttribute(name="carFormData") CarriageInfo formData,
                                     BindingResult bindingResult,
                                     Model model) {
        if(bindingResult.hasErrors()) {
            return "redirect:/";
        }
        middlemanService.updateCarriageById(id, formData);
        return "redirect:/";
    }

    @DeleteMapping(value = "{id}")
    public String removeCarriageById(@PathVariable BigInteger id) {
        middlemanService.removeCarriageById(id);
        return "redirect:/";
    }

    @DeleteMapping
    public String purge() {
        middlemanService.removeAllCarriages();
        return "redirect:/";
    }


}

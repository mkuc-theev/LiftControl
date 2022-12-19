package com.michal.kuc.liftctl.controller;

import com.michal.kuc.liftctl.model.CallParams;
import com.michal.kuc.liftctl.model.CarriageInfo;
import com.michal.kuc.liftctl.model.SendParam;
import com.michal.kuc.liftctl.service.MiddlemanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;

/**
 * Web controller for interacting with the program from a browser
 */
@Controller
@RequestMapping("/")
public class WebRestController {

    @Autowired
    private MiddlemanService middlemanService;

    @GetMapping
    public String showHome(Model model) {
        model.addAttribute("carriages", middlemanService.getAllCarriages());
        model.addAttribute("calls", middlemanService.getCalls());
        model.addAttribute("maxCarriages", middlemanService.getMaxCarriages());
        model.addAttribute("callFormData", new CallParams());
        model.addAttribute("carFormData", new CarriageInfo());
        model.addAttribute("sendFormData", new SendParam());
        return "panel";
    }

    @GetMapping(value = "init")
    public String initialize() {
        middlemanService.initialize();
        return "redirect:/";
    }

    @GetMapping(value = "step")
    public String step() {
        middlemanService.step();
        return "redirect:/";
    }

    @PostMapping(value = "send/{id}")
    public String sendCarriage(@ModelAttribute(name = "sendFormData") SendParam sendFormData,
                               @PathVariable(name = "id") BigInteger id,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        middlemanService.send(id, sendFormData);
        return "redirect:/";
    }

    @PostMapping(value = "newCarriage")
    public String createCarriage(@Valid @ModelAttribute(name = "carFormData") CarriageInfo carFormData,
                                 BindingResult bindingResult,
                                 Model model) {

        if (middlemanService.isAtMaxCars()) {
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        middlemanService.addCarriage(carFormData);
        return "redirect:/";
    }

    @PostMapping(value = "newCall")
    public String call(@ModelAttribute(name = "callFormData") CallParams callFormData,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        middlemanService.call(callFormData);
        return "redirect:/";
    }

    @PostMapping(value = "update/{id}")
    public String updateCarriageById(@PathVariable BigInteger id,
                                     @ModelAttribute(name = "carFormData") CarriageInfo carFormData,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        middlemanService.updateCarriageById(id, carFormData);
        return "redirect:/";
    }

    @GetMapping(value = "{id}")
    public String removeCarriageById(@PathVariable BigInteger id) {
        middlemanService.removeCarriageById(id);
        return "redirect:/";
    }

    @GetMapping(value = "/purge")
    public String purge() {
        middlemanService.removeAllCarriages();
        return "redirect:/";
    }
}

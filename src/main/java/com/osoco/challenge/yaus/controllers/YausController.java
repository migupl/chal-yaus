package com.osoco.challenge.yaus.controllers;

import com.osoco.challenge.yaus.domain.ShortenUrl;
import com.osoco.challenge.yaus.services.YausService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class YausController {

    private YausService yausService;

    @RequestMapping(value = "/",  method = RequestMethod.GET)
    String index(Model model) {
        model.addAttribute("shortenUrl", new ShortenUrl());
        return "index";
    }

    @RequestMapping(value = "/",  method = RequestMethod.POST)
    @ResponseBody
    ShortenUrl shorthen(@RequestBody ShortenUrl shortenUrl) {
        yausService.setShortenUrl(shortenUrl);

        return shortenUrl;
    }

    @RequestMapping(value = "/{shorten}",  method = RequestMethod.GET)
    Object redirect(@PathVariable String shorten, HttpServletResponse response) {
        try {
            RedirectView redirect = new RedirectView(yausService.getRedirection(shorten));
            redirect.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
            return redirect;

        } catch (NotFoundException e) {
        }

        String redirect = "404";
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        return redirect;
    }

    @Autowired
    YausController(YausService yausService) {
        this.yausService = yausService;
    }
}

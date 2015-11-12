package com.osoco.challenge.yaus.controllers;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Cacheable("url-shortener")
public class IndexController {

    @RequestMapping(value="/",  method = RequestMethod.GET)
    String index(){
        return "index";
    }
}
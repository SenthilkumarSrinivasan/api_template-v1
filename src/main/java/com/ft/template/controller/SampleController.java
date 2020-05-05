package com.ft.template.controller;

import com.ft.template.service.SampleService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    SampleService sampleService;

    @RequestMapping(method = RequestMethod.GET, value= "/hello")
    public String sampleGetMethod() {
        return sampleService.sampleServiceMethod();
    }

}

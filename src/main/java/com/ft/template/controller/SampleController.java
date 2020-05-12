package com.ft.template.controller;

import com.ft.template.domain.entities.Sample;
import com.ft.template.service.SampleService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SampleController {

    @Autowired
    SampleService sampleService;


    @GetMapping(
            value= "/hello")
    public String sampleGetMethod() {
        return sampleService.sampleServiceMethod();
    }

    @PostMapping(
            value= "/sample/add")
    public String addData(
            @RequestParam("title") String title) {
        String id = sampleService.addData(title);
        return id;
    }

    @GetMapping(
            value = "/sample/{id}"
    )
    public Sample getData(@PathVariable String id) {
        Sample sample = sampleService.getData(id);
        return sample;
    }

}

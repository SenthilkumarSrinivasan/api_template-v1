package com.ft.template.dao;

import com.ft.template.domain.entities.Sample;
import com.ft.template.domain.repositories.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SampleDAO {

    @Autowired
    SampleRepository sampleRepository;

    public String sampleDAOMethod() {
        return "Say hello from dao layer";
    }

    public String addData(String title) {
        Sample sample = new Sample();
        sample.setTitle(title);
        sample = sampleRepository.save(sample);
        return sample.getId();
    }

    public Sample getData(String id) {
        return sampleRepository.findById(id).get();
    }

}

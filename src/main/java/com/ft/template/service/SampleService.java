package com.ft.template.service;

import com.ft.template.dao.SampleDAO;
import com.ft.template.domain.entities.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    @Autowired
    SampleDAO sampleDAO;

    public String sampleServiceMethod() {
        return sampleDAO.sampleDAOMethod();
    }

    public String addData(String title) {
        String id = sampleDAO.addData(title);
        return id;
    }

    public Sample getData(String id) {
        return sampleDAO.getData(id);
    }

}

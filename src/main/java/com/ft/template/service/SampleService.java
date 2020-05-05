package com.ft.template.service;

import com.ft.template.dao.SampleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    @Autowired
    SampleDAO sampleDAO;

    public String sampleServiceMethod() {
        return sampleDAO.sampleDAOMethod();
    }

}

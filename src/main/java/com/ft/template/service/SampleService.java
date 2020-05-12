package com.ft.template.service;

import com.ft.template.config.PropConfig;
import com.ft.template.dao.SampleDAO;
import com.ft.template.domain.entities.Sample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SampleService {

    @Autowired
    SampleDAO sampleDAO;

    @Autowired
    PropConfig propConfig;

    public String sampleServiceMethod() {
        return sampleDAO.sampleDAOMethod();
    }

    public String addData(String title) {
        String id = sampleDAO.addData(title);
        log.info("data - "+propConfig.getSampleFlag());
        return id;
    }

    public Sample getData(String id) {
        return sampleDAO.getData(id);
    }

}

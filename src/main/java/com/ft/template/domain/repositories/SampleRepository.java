package com.ft.template.domain.repositories;

import com.ft.template.domain.entities.Sample;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SampleRepository extends MongoRepository<Sample, String> {
}

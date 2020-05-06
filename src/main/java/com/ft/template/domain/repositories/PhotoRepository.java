package com.ft.template.domain.repositories;

import com.ft.template.domain.entities.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> {
}

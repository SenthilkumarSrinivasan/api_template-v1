package com.ft.template.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sampleCollection")
public class Sample {

    @Id
    private String id;

    private String title;

    private String value;

}

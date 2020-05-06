package com.ft.template.controller;

import com.ft.template.domain.entities.Photo;
import com.ft.template.service.PhotoService;
import com.ft.template.service.SampleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@RestController
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @PostMapping(
            value= "/photos/add")
    public String addPhoto(
            @RequestParam("title") String title,
            @RequestParam("image") MultipartFile image) throws IOException {
        log.info("title - "+title);
        String id = photoService.addPhoto(title, image);
        return id;
    }

    @GetMapping(
            value = "/photos/{id}"
    )
    public ResponseEntity<byte[]> getPhoto(@PathVariable String id) throws IOException {
        Photo photo = photoService.getPhoto(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photo.getImage().getData());

    }


}

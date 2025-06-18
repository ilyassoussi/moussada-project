package org.gov.moussaada.utilisateur_service.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/utilisateur/auth/media")

public class Media {
    private final String imageDirectory = "/var/tmp/images/";
    private final String videoDirectory = "/var/tmp/video/";
    private final String pdfDirectory = "/var/tmp/PDF/";

    @GetMapping("/images/{filename}")
    public Resource getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(imageDirectory).resolve(filename).normalize();
            return new UrlResource(filePath.toUri());
        } catch (Exception e) {
            throw new RuntimeException("Image not found: " + filename, e);
        }
    }

    @GetMapping("/video/{filename}")
    public Resource getVideo(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(videoDirectory).resolve(filename).normalize();
            return new UrlResource(filePath.toUri());
        } catch (Exception e) {
            throw new RuntimeException("Video not found: " + filename, e);
        }
    }

}

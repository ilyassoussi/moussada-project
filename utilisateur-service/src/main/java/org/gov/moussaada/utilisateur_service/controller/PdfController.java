package org.gov.moussaada.utilisateur_service.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/utilisateur/auth/pdf")
public class PdfController {

    private final String pdfDirectory = "/var/tmp/PDF";

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String fileName) {
        try {
            Path path = Paths.get(pdfDirectory).resolve(fileName);
            Resource resource = new UrlResource(path.toUri());
            System.out.println("ici : "+ resource);
            System.out.println("ici : "+ resource.exists());
            if (resource.exists() && resource.isReadable()) {
                System.out.println("PDF trouvé et lisible.");
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                        .body(resource);
            } else {
                System.out.println("Fichier introuvable ou illisible : " + path.toString());
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

package org.gov.moussaada.terrain_service.controller;

import org.gov.moussaada.terrain_service.service.PdfGeneratorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/terrain/rapport")
public class RapportController {

    private final PdfGeneratorService rapportService;

    // Injection du service (où est ta méthode generateRapportPdf)
    public RapportController(PdfGeneratorService rapportService) {
        this.rapportService = rapportService;
    }

    @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> generateRapport(
            @RequestParam int id_reponse,
            @RequestParam String titreFoncier,
            @RequestParam String nomTechnicien,
            @RequestParam String dateVisite,
            @RequestParam String region,
            @RequestParam String commune,
            @RequestParam Double gpsLatitude,
            @RequestParam Double gpsLongitude,
            @RequestParam double superficieReelleMesuree,
            @RequestParam String typeSol,
            @RequestParam String etatSol,
            @RequestParam String cultureActuelle,
            @RequestParam String systemeIrrigationExistant,
            @RequestParam String besoinReel,
            @RequestParam(required = false) List<MultipartFile> photosTerrain,
            @RequestParam boolean coherenceDemande,
            @RequestParam(required = false) String remarqueCoherence,
            @RequestParam boolean devisJustifie,
            @RequestParam(required = false) String remarquesTechnicien,
            @RequestParam String avis,
            @RequestParam String justificationAvis,
            @RequestParam(required = false) Double montantEstimeProjet
    ) {
        try {
            // Appel à la méthode qui génère le PDF
            String pdfFilePath = rapportService.generateRapportPdf(
                    id_reponse,
                    titreFoncier,
                    nomTechnicien,
                    dateVisite,
                    region,
                    commune,
                    gpsLatitude,
                    gpsLongitude,
                    superficieReelleMesuree,
                    typeSol,
                    etatSol,
                    cultureActuelle,
                    systemeIrrigationExistant,
                    besoinReel,
                    photosTerrain,
                    coherenceDemande,
                    remarqueCoherence,
                    devisJustifie,
                    remarquesTechnicien,
                    avis,
                    justificationAvis,
                    montantEstimeProjet
            );

            // Lire le fichier généré en bytes
            Path file = Paths.get(pdfFilePath);
            byte[] pdfContent = Files.readAllBytes(Path.of("/var/tmp/PDF/" + file));
            // Réponse HTTP avec PDF en attachment
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                    .body(pdfContent);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de la génération du PDF : " + e.getMessage());
        }
    }
}

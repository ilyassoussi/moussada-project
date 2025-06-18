package org.gov.moussaada.terrain_service.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.gov.moussaada.common_dto.KafkaMoussaadaDTO;
import org.gov.moussaada.terrain_service.dao.RapportDAO;
import org.gov.moussaada.terrain_service.dao.ResponseDAO;
import org.gov.moussaada.terrain_service.dto.KafkaUpdateStatusTerrain;
import org.gov.moussaada.terrain_service.model.EtatServiceTewrrain;
import org.gov.moussaada.terrain_service.model.Rapport;
import org.gov.moussaada.terrain_service.model.Response;
import org.gov.moussaada.terrain_service.utils.utile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PdfGeneratorService {

    private ResponseDAO responseDAO;
    private RapportDAO rapportDAO;

    @Autowired
    private KafkaTerrainService kafkaTerrainService;

    public PdfGeneratorService(ResponseDAO responseDAO, RapportDAO rapportDAO) {
        this.responseDAO = responseDAO;
        this.rapportDAO = rapportDAO;
    }

    public String generateRapportPdf(
            int id_reponse,
            String titreFoncier,
            String nomTechnicien,
            String dateVisite,
            String region,
            String commune,
            Double gpsLatitude,
            Double gpsLongitude,
            double superficieReelleMesuree,
            String typeSol,
            String etatSol,
            String cultureActuelle,
            String systemeIrrigationExistant,
            String besoinReel,
            List<MultipartFile> photosTerrain,
            boolean coherenceDemande,
            String remarqueCoherence,
            boolean devisJustifie,
            String remarquesTechnicien,
            String avis,
            String justificationAvis,
            Double montantEstimeProjet
    ) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 40, 40, 60, 40);
        PdfWriter.getInstance(document, baos);
        document.open();

        // Titre principal
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
        Paragraph title = new Paragraph("Rapport Technique de Terrain", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Section 1 : Identifiants
        document.add(sectionTitle("1. Identifiants"));
        PdfPTable identifiants = new PdfPTable(new float[]{3, 7});
        identifiants.setWidthPercentage(100);
        identifiants.addCell("ID Demande");
        identifiants.addCell(String.valueOf(id_reponse));
        identifiants.addCell("Titre Foncier");
        identifiants.addCell(titreFoncier);
        identifiants.addCell("Nom Technicien");
        identifiants.addCell(nomTechnicien);
        identifiants.addCell("Date Visite");
        identifiants.addCell(dateVisite);
        document.add(identifiants);

        // Section 2 : Localisation
        document.add(sectionTitle("2. Localisation"));
        PdfPTable localisation = new PdfPTable(new float[]{3, 7});
        localisation.setWidthPercentage(100);
        localisation.addCell("Région");
        localisation.addCell(region);
        localisation.addCell("Commune");
        localisation.addCell(commune);
        localisation.addCell("Latitude");
        localisation.addCell(String.valueOf(gpsLatitude));
        localisation.addCell("Longitude");
        localisation.addCell(String.valueOf(gpsLongitude));
        document.add(localisation);

        // Section 3 : Détails Terrain
        document.add(sectionTitle("3. Détails du Terrain"));
        PdfPTable detailsTerrain = new PdfPTable(new float[]{3, 7});
        detailsTerrain.setWidthPercentage(100);
        detailsTerrain.addCell("Superficie Réelle Mesurée (m²)");
        detailsTerrain.addCell(String.format("%.2f", superficieReelleMesuree));
        detailsTerrain.addCell("Type de Sol");
        detailsTerrain.addCell(typeSol);
        detailsTerrain.addCell("État du Sol");
        detailsTerrain.addCell(etatSol);
        detailsTerrain.addCell("Culture Actuelle");
        detailsTerrain.addCell(cultureActuelle);
        detailsTerrain.addCell("Système d'Irrigation Existant");
        detailsTerrain.addCell(systemeIrrigationExistant);
        detailsTerrain.addCell("Besoin Réel");
        detailsTerrain.addCell(besoinReel);
        document.add(detailsTerrain);

        // Section 4 : Analyse de la Demande
        document.add(sectionTitle("4. Analyse de la Demande"));
        PdfPTable analyseDemande = new PdfPTable(new float[]{3, 7});
        analyseDemande.setWidthPercentage(100);
        analyseDemande.addCell("Cohérence de la Demande");
        analyseDemande.addCell(coherenceDemande ? "Oui" : "Non");
        analyseDemande.addCell("Remarques sur la Cohérence");
        analyseDemande.addCell(remarqueCoherence != null ? remarqueCoherence : "Aucune");
        analyseDemande.addCell("Devis Justifié");
        analyseDemande.addCell(devisJustifie ? "Oui" : "Non");
        analyseDemande.addCell("Remarques du Technicien");
        analyseDemande.addCell(remarquesTechnicien != null ? remarquesTechnicien : "Aucune");
        document.add(analyseDemande);

        // Section 5 : Conclusion
        document.add(sectionTitle("5. Conclusion du Technicien"));
        PdfPTable conclusion = new PdfPTable(new float[]{3, 7});
        conclusion.setWidthPercentage(100);
        conclusion.addCell("Avis");
        conclusion.addCell(avis);
        conclusion.addCell("Justification");
        conclusion.addCell(justificationAvis);
        conclusion.addCell("Montant estimé");
        conclusion.addCell(montantEstimeProjet != null ? montantEstimeProjet + " MAD" : "Non spécifié");
        document.add(conclusion);

        // Section 6 : Photos du Terrain
        if (photosTerrain != null && !photosTerrain.isEmpty()) {
            document.add(sectionTitle("6. Photos du Terrain"));
            for (MultipartFile photoFile : photosTerrain) {
                try {
                    String photoPathString = utile.saveImage(photoFile);
                    Image img = Image.getInstance("/var/tmp/images/"+photoPathString);
                    img.scaleToFit(400, 250);
                    img.setSpacingBefore(10);
                    document.add(img);
                } catch (Exception e) {
                    document.add(new Paragraph("Erreur lors de l’ajout d’une photo : " + photoFile.getOriginalFilename()));
                }
            }
        }

        document.close();

        String fileName = utile.generatePdfFileName(id_reponse, titreFoncier);
        String savedFileName = utile.saveGeneratedPdf(baos.toByteArray(), fileName);
        try {
            Optional<Response> reponse = responseDAO.findById(id_reponse);
            if (reponse.isPresent()){
                Rapport rapport = Rapport.builder()
                        .isvalid(false)
                        .id_reponse(reponse.get())
                        .rapport(savedFileName)
                        .build();
                rapportDAO.save(rapport);

                reponse.get().setId_response(reponse.get().getId_response());
                reponse.get().setEtats(EtatServiceTewrrain.TERMINEE);
                reponse.get().setTitre(avis);
                reponse.get().setCommentaire(justificationAvis);
                reponse.get().setDate_de_sortie(utile.ReformulateDate(dateVisite));
                reponse.get().setId_rapport(rapport);

                Response Saved = responseDAO.save(reponse.get());
                KafkaMoussaadaDTO kafkaMoussaadaDTO = new KafkaMoussaadaDTO("TERRAIN",new KafkaUpdateStatusTerrain(Saved.getId_traitement_subvention(),Saved.getId_response(), Saved.getDate_de_sortie(), String.valueOf(Saved.getEtats())));
                kafkaTerrainService.SendIdReponseTraitementDemandeSubvention(kafkaMoussaadaDTO);
            }
            return savedFileName;
        } catch (Exception e){
            return "Error";
        }

    }

    // Méthode helper pour titre section (réutilisable)
    private Paragraph sectionTitle(String text) {
        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Paragraph p = new Paragraph(text, sectionFont);
        p.setSpacingBefore(15);
        p.setSpacingAfter(10);
        return p;
    }

}

package org.gov.moussaada.paysan_service.service;

import io.spring.guides.gs_producing_web_service.*;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.paysan_service.dto.UtilisateurReponseDTO;
import org.gov.moussaada.paysan_service.feign.UtilisateurFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Map;

@Slf4j
@Service
public class TerrainSOAPService {

    private final WebServiceTemplate webServiceTemplate;

    @Autowired
    private UtilisateurFeign utilisateurFeign;


    public TerrainSOAPService(WebServiceTemplate webServiceTemplate, UtilisateurFeign utilisateurFeign) {
        this.webServiceTemplate = webServiceTemplate;
        this.utilisateurFeign = utilisateurFeign;
    }

    public GetInformationsResponse getTerreByCIN() {
        try {
            // Extraction de l'ID de l'utilisateur à partir du principal
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long idUtilisateur = null;
            Map<String, Object> userDetails = (Map<String, Object>) principal;
            idUtilisateur = Long.valueOf(userDetails.get("id_utilisateur").toString());

            UtilisateurReponseDTO utilisateur = utilisateurFeign.getById(Math.toIntExact(idUtilisateur));
            if (utilisateur == null) {
                throw new RuntimeException("Utilisateur non trouvé");
            }
            log.info("voila : {}",utilisateur);
            GetInformationRequest request = new GetInformationRequest();
            request.setCIN(utilisateur.getIdentite());

            GetInformationsResponse response = (GetInformationsResponse) webServiceTemplate.marshalSendAndReceive(request);

            if (response == null) {
                log.error("La réponse du service SOAP est vide");
                throw new RuntimeException("Aucune réponse du service SOAP");
            }

            return response;
        } catch (Exception e) {
            log.error("Erreur lors de l'appel SOAP : ", e);
            throw new RuntimeException("Erreur de communication avec le service SOAP", e);
        }
    }


}

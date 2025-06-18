package org.gov.moussaada.terrain_service.service;

import io.spring.guides.gs_producing_web_service.GetInformationRequest;
import io.spring.guides.gs_producing_web_service.GetInformationsResponse;
import io.spring.guides.gs_producing_web_service.GetTerreRequest;
import io.spring.guides.gs_producing_web_service.GetTerreResponse;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.terrain_service.dto.UtilisateurReponseDTO;
import org.gov.moussaada.terrain_service.feign.UtilisateurFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

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

    public GetInformationsResponse getTerreByCIN(int id) {
        try {
            // Extraction de l'ID de l'utilisateur à partir du principal
            UtilisateurReponseDTO utilisateur = utilisateurFeign.getById(Math.toIntExact(id));
            if (utilisateur == null) {
                log.error("Utilisateur avec ID {} non trouvé", id);
                throw new RuntimeException("Utilisateur non trouvé");
            }

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


    public GetTerreResponse getTerreByTitre(String numeroTitre) {

        try{
            GetTerreRequest request = new GetTerreRequest();
            request.setNumeroTitre(numeroTitre);

            GetTerreResponse response = (GetTerreResponse) webServiceTemplate.marshalSendAndReceive(request);

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

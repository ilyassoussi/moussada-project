package org.gov.moussaada.admin_service.service;


import org.gov.moussaada.admin_service.dao.ActualiteDAO;
import org.gov.moussaada.admin_service.dto.ActualiteReponseDTO;
import org.gov.moussaada.admin_service.dto.ActualiteRequestDTO;
import org.gov.moussaada.admin_service.model.Actualite;
import org.gov.moussaada.admin_service.model.ActualiteLangue;
import org.gov.moussaada.admin_service.response.ErrorResponse;
import org.gov.moussaada.admin_service.response.SuccessResponse;
import org.gov.moussaada.admin_service.service.inter.IActualiteService;
import org.gov.moussaada.admin_service.utils.utile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActualiteService implements IActualiteService {

    @Autowired
    private ActualiteDAO actualitedao;
    @Autowired
    private ModelMapper modelmapper;

    @Override
    public ResponseEntity<?> save(ActualiteRequestDTO actualiteRQ) {
        Actualite actualite = new Actualite();
        actualite.setImage(actualiteRQ.getImage());
        actualite.setDate_creation(actualiteRQ.getDate_creation());
        actualite.setActive(actualiteRQ.isActive());

        ActualiteLangue fr = new ActualiteLangue();
        fr.setLangue("fr");
        fr.setTitre(actualiteRQ.getTitreFr());
        fr.setDescription(actualiteRQ.getDescriptionFr());
        fr.setActualite(actualite);

        ActualiteLangue ar = new ActualiteLangue();
        ar.setLangue("ar");
        ar.setTitre(actualiteRQ.getTitreAr());
        ar.setDescription(actualiteRQ.getDescriptionAr());
        ar.setActualite(actualite);

        actualite.setTraductions(List.of(fr, ar));
        try{
            Actualite saved = actualitedao.save(actualite);
            if (saved != null) {
                return ResponseEntity.created(URI.create(String.format("/actualite/%d", saved.getId())))
                        .body(new SuccessResponse<>("Actualite Added successfully",201,saved));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Actualite does not add"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> findByTitre(String titre) {
        Actualite actualite = actualitedao.findByTitre(titre);
        if(actualite!=null){
            return ResponseEntity.ok(new SuccessResponse<>("Actualite found successfully",200,actualite));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Actualite with title " + titre + " not found"));
        }
    }

    @Override
    public ResponseEntity<?> update(ActualiteRequestDTO actualiteRQ, Integer id) {
        Optional<Actualite> existingOptional = actualitedao.findById(id);

        if (existingOptional.isPresent()) {
            Actualite existing = existingOptional.get();
            // Mise à jour de la date et du PDF
            existing.setDate_creation(utile.CurentDate());
            existing.setActive(actualiteRQ.isActive());
            if (actualiteRQ.getImage() != null) {
                existing.setImage(actualiteRQ.getImage());
            }

            // Mettre à jour les traductions
            for (ActualiteLangue traduction : existing.getTraductions()) {
                if ("fr".equalsIgnoreCase(traduction.getLangue())) {
                    traduction.setTitre(actualiteRQ.getTitreFr());
                    traduction.setDescription(actualiteRQ.getDescriptionFr());
                } else if ("ar".equalsIgnoreCase(traduction.getLangue())) {
                    traduction.setTitre(actualiteRQ.getTitreAr());
                    traduction.setDescription(actualiteRQ.getDescriptionAr());
                }
            }

            // Sauvegarde
            Actualite updated = actualitedao.save(existing);
            ActualiteReponseDTO responseDTO = modelmapper.map(updated, ActualiteReponseDTO.class);
            return ResponseEntity.accepted()
                    .body(new SuccessResponse<>("Actualite updated successfully", 202, responseDTO));

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Actualite with id " + id + " not found"));
        }
    }


    @Override
    public ResponseEntity<?> Delete(Integer id) {
        Optional<Actualite> existingActualite = actualitedao.findById(id);
        if(existingActualite.isPresent()){
            actualitedao.deleteById(id);
            ActualiteReponseDTO responseDTO = modelmapper.map(existingActualite,ActualiteReponseDTO.class);
            return ResponseEntity.ok(new SuccessResponse<>("Actualite Deleted successfully",200,responseDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Actualite with id " + id + " not found"));
        }
    }

    @Override
    public ResponseEntity<?> DeleteAll() {
        List<ActualiteReponseDTO> allActualite = this.findAll("fr");
        if(allActualite!=null){
            for (ActualiteReponseDTO actuali : allActualite){
                actualitedao.deleteById(actuali.getId());
            }
            return ResponseEntity.ok(new SuccessResponse<>("All Actualite are Deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Error....."));
        }
    }

    @Override
    public ResponseEntity<?> getByIdAndLang(int id, String lang) {
        Optional<Actualite> optionalActualite = actualitedao.findById(id);

        if (optionalActualite.isPresent()) {
            Actualite actualite = optionalActualite.get();

            // Cherche la traduction correspondante
            ActualiteLangue selectedLang = actualite.getTraductions()
                    .stream()
                    .filter(t -> t.getLangue().equalsIgnoreCase(lang))
                    .findFirst()
                    .orElse(null);

            if (selectedLang != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("id", actualite.getId());
                response.put("pdf", actualite.getImage());
                response.put("is_active", actualite.isActive());
                response.put("date_creation", actualite.getDate_creation());
                response.put("titre", selectedLang.getTitre());
                response.put("description", selectedLang.getDescription());
                response.put("langue", selectedLang.getLangue());

                return ResponseEntity.ok(new SuccessResponse<>("Actualité récupérée avec succès", 200, response));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Traduction non trouvée pour la langue : " + lang));
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Actualité non trouvée avec l'id : " + id));
        }
    }

    @Override
    public ResponseEntity<?> getById(int id) {
        Optional<Actualite> optionalActualite = actualitedao.findById(id);
        if(optionalActualite.isPresent()){
            return ResponseEntity.ok().body(new SuccessResponse<>("actualite ", 200 , optionalActualite.get()));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("no actualite"));
        }
    }


    @Override
    public List<ActualiteReponseDTO> findAll(String lang) {
        List<Actualite> all = actualitedao.findAll();
        List<ActualiteReponseDTO> result = new ArrayList<>();

        for (Actualite actualite : all) {
            ActualiteLangue selectedLang = actualite.getTraductions()
                    .stream()
                    .filter(t -> t.getLangue().equalsIgnoreCase(lang))
                    .findFirst()
                    .orElse(null);

            if (selectedLang != null) {
                ActualiteReponseDTO dto = new ActualiteReponseDTO();
                dto.setId(actualite.getId());
                dto.setImage(actualite.getImage());
                dto.setDate_creation(actualite.getDate_creation());
                dto.setTitre(selectedLang.getTitre());
                dto.setDescription(selectedLang.getDescription());
                dto.set_active(selectedLang.getActualite().isActive());
                result.add(dto);
            }
        }

        return result;
    }

}

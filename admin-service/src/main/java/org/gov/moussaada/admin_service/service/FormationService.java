package org.gov.moussaada.admin_service.service;

import org.gov.moussaada.admin_service.dao.FormationDAO;
import org.gov.moussaada.admin_service.dto.FormationReponseDTO;
import org.gov.moussaada.admin_service.dto.FormationRequestDTO;
import org.gov.moussaada.admin_service.model.Formation;
import org.gov.moussaada.admin_service.model.FormationLangue;
import org.gov.moussaada.admin_service.response.SuccessResponse;
import org.gov.moussaada.admin_service.service.inter.IFormation;
import org.gov.moussaada.admin_service.utils.utile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FormationService implements IFormation {


    private FormationDAO formationDAO;

    public FormationService(FormationDAO formationDAO) {
        this.formationDAO = formationDAO;
    }

    @Override
    public ResponseEntity<?> save(FormationRequestDTO dto) {
        Formation formation = new Formation();
        formation.setDateCreation(utile.CurentDate());
        formation.setLieu(dto.getLieu());
        formation.setIntervenant(dto.getIntervenant());
        formation.setDate(dto.getDate());
        formation.setHeure(dto.getHeure());
        formation.setParticipantsMax(dto.getParticipantsMax());
        formation.setActive(dto.getActive());

        FormationLangue fr = new FormationLangue();
        fr.setLangue("fr");
        fr.setTitre(dto.getTitreFr());
        fr.setDescription(dto.getDescriptionFr());
        fr.setFormation(formation);

        FormationLangue ar = new FormationLangue();
        ar.setLangue("ar");
        ar.setTitre(dto.getTitreAr());
        ar.setDescription(dto.getDescriptionAr());
        ar.setFormation(formation);

        formation.setTraductions(List.of(fr, ar));

        try{
            Formation saved = formationDAO.save(formation);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse<>("Formation créée avec succès", 201, saved.getId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<FormationReponseDTO> findAll(String lang) {
        List<Formation> all = formationDAO.findAll();
        List<FormationReponseDTO> result = new ArrayList<>();

        for (Formation formation : all) {
            formation.getTraductions().stream()
                    .filter(t -> t.getLangue().equalsIgnoreCase(lang))
                    .findFirst()
                    .ifPresent(selectedLang -> {
                        FormationReponseDTO dto = new FormationReponseDTO();
                        dto.setId(formation.getId());
                        dto.setTitre(selectedLang.getTitre());
                        dto.setDescription(selectedLang.getDescription());
                        dto.setLieu(formation.getLieu());
                        dto.setIntervenant(formation.getIntervenant());
                        dto.setDate(formation.getDate());
                        dto.setHeure(formation.getHeure());
                        dto.setParticipantsMax(formation.getParticipantsMax());
                        dto.setActive(formation.getActive());
                        dto.setLangue(lang);
                        result.add(dto);
                    });
        }

        return result;
    }

    @Override
    public ResponseEntity<?> deleteId(int id) {
        Optional<Formation> formation = formationDAO.findById(id);
        if(formation.isPresent()){
            formationDAO.deleteById(id);
            return ResponseEntity.ok().body(new SuccessResponse<>("deleted",200,null));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
    }

}

package org.gov.moussada.shared_micro.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.gov.moussaada.subventions_service.model.Demande_technique;
import org.gov.moussaada.subventions_service.model.TraitementSubvention;
import org.gov.moussada.shared_micro.feign.PaysanFeign;
import org.gov.moussada.shared_micro.feign.SubventionFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shared")

@AllArgsConstructor
@NoArgsConstructor
public class Terrain_subvention {

    @Autowired
    private SubventionFeign subventionFeign;

    @Autowired
    private PaysanFeign paysanFeign;

    @GetMapping("/from-subvention/{id}")
    public ResponseEntity<?> getDemandeTerrainById(@PathVariable int id){
        return subventionFeign.getById(id);
    }

    @GetMapping("/from-subvention")
    public ResponseEntity<?> getDemandeTerrainById(){
        return subventionFeign.getAlldm();
    }

    @GetMapping("/from-subvention/subvention-traitement/{id}")
    public ResponseEntity<?> getDemandetraitementById(@PathVariable int id){
        return subventionFeign.getByIdSubventionDemande(id);
    }

    @GetMapping("/from-paysan/demande-paysan/{id}")
    public ResponseEntity<?> getDemandeById(@PathVariable Long id){
        return paysanFeign.getDemande(id);
    }
}

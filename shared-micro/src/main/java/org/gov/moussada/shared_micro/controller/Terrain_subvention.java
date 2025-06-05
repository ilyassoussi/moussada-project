package org.gov.moussada.shared_micro.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.gov.moussada.shared_micro.feign.PaysanFeign;
import org.gov.moussada.shared_micro.feign.SubventionFeign;
import org.gov.moussada.shared_micro.feign.TerrainFeign;
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

    @Autowired
    private TerrainFeign terrainFeign;

    @GetMapping("/from-subvention/{id}")
    public ResponseEntity<?> getDemandeTerrainById(@PathVariable int id){
        return subventionFeign.getById(id);
    }

    @GetMapping("/from-subvention")
    public ResponseEntity<?> getDemandeTerrainAll(){
        return subventionFeign.getAlldm();
    }

    @GetMapping("/from-subvention/subvention-traitement/{id}")
    public ResponseEntity<?> getDemandetraitementById(@PathVariable int id){
        return subventionFeign.getByIdSubventionDemande(id);
    }

    @GetMapping("/from-terrain/reponse/{id}")
    public ResponseEntity<?> getResponseById(@PathVariable int id){
        return terrainFeign.getById(id);
    }

    @GetMapping("/from-terrain/reponse/get-rapport")
    public ResponseEntity<?> getRapport(){
        return terrainFeign.getRapport();
    }

    @GetMapping("/from-paysan/demande-paysan/{id}")
    public ResponseEntity<?> getDemandeById(@PathVariable Long id){
        return paysanFeign.getDemande(id);
    }

    @GetMapping("/from-paysan/demande-paysan")
    public ResponseEntity<?> getDemande(){
        return paysanFeign.getDemandeAll();
    }
}

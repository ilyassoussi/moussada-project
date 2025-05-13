package org.gov.moussaada.terrain_service.controller;

import io.spring.guides.gs_producing_web_service.GetInformationsResponse;
import io.spring.guides.gs_producing_web_service.GetTerreResponse;
import org.gov.moussaada.admin_service.response.SuccessResponse;
import org.gov.moussaada.admin_service.service.TerrainSOAPService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/info/terre")
public class InfoPaysanTerres {
    private final TerrainSOAPService terrainSoapService;

    public InfoPaysanTerres(TerrainSOAPService terrainSoapService) {
        this.terrainSoapService = terrainSoapService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTerre(@PathVariable int id) {
        GetInformationsResponse response = terrainSoapService.getTerreByCIN(id);
        return ResponseEntity.ok().body(new SuccessResponse<>("information sur le paysan",200,response));
    }

    @GetMapping("/{Numero_titre}")
    public ResponseEntity<?> ByTitre(@PathVariable String Numero_titre) {
        GetTerreResponse response = terrainSoapService.getTerreByTitre(Numero_titre);
        return ResponseEntity.ok().body(new SuccessResponse<>("information de terre",200,response));
    }
}

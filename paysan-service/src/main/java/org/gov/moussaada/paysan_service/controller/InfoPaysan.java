package org.gov.moussaada.paysan_service.controller;

import io.spring.guides.gs_producing_web_service.GetInformationsResponse;
import org.gov.moussaada.paysan_service.response.SuccessResponse;
import org.gov.moussaada.paysan_service.service.TerrainSOAPService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("paysan/info/terre")
public class InfoPaysan {
    private final TerrainSOAPService terrainSoapService;

    public InfoPaysan(TerrainSOAPService terrainSoapService) {
        this.terrainSoapService = terrainSoapService;
    }

    @GetMapping("")
    public ResponseEntity<?> getTerre() {
        GetInformationsResponse response = terrainSoapService.getTerreByCIN();
        return ResponseEntity.ok().body(new SuccessResponse<>("information sur le paysan",200,response));
    }
}

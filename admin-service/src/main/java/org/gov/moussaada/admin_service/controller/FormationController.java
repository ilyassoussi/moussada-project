package org.gov.moussaada.admin_service.controller;

import org.gov.moussaada.admin_service.dto.FormationReponseDTO;
import org.gov.moussaada.admin_service.dto.FormationRequestDTO;
import org.gov.moussaada.admin_service.response.SuccessResponse;
import org.gov.moussaada.admin_service.service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/formations")

public class FormationController {

    @Autowired
    private FormationService formationService;

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody FormationRequestDTO dto) {
        return formationService.save(dto);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "fr") String lang) {
        List<FormationReponseDTO> list = formationService.findAll(lang);
        return ResponseEntity.ok(new SuccessResponse<>("Liste des formations", 200, list));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteId(@PathVariable int id) {
        return formationService.deleteId(id);
    }
}

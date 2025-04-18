package org.gov.moussaada.subventions_service.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.subventions_service.dto.SubventionRequest;
import org.gov.moussaada.subventions_service.service.SubventionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
@RequestMapping("/subvention")
public class SubventionController {
    private SubventionService subventionService;

    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody SubventionRequest subventionRequest){
        return subventionService.Create(subventionRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> Update(@PathVariable int id ,@RequestBody SubventionRequest subventionRequest){
        return subventionService.Update(id , subventionRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> remove(@PathVariable int id){
        return subventionService.RemoveSubvention(id);
    }

    @GetMapping("")
    public ResponseEntity<?> get(){
        return subventionService.GetAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id){
        return subventionService.GetById(id);
    }
}

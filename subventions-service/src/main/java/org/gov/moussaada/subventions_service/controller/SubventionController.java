package org.gov.moussaada.subventions_service.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.subventions_service.dto.SubventionRequest;
import org.gov.moussaada.subventions_service.service.SubventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
@RequestMapping("/subvention")
public class SubventionController {

    @Autowired
    private SubventionService subventionService;

    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody SubventionRequest subventionRequest){
        return subventionService.Create(subventionRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> Update(@PathVariable Long id ,@RequestBody SubventionRequest subventionRequest){
        return subventionService.Update(id , subventionRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){
        return subventionService.RemoveSubvention(id);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> get(){
        return subventionService.GetAll();
    }

    @GetMapping("/notexpired")
    public ResponseEntity<?> getnotexpired(){
        return subventionService.GetNotExpired();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return subventionService.GetById(id);
    }

//    @PostMapping("/traitement/{id}")
//    public ResponseEntity<?> traite(@PathVariable Long id){
//
//    }

}

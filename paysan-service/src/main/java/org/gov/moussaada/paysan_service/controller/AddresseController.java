package org.gov.moussaada.paysan_service.controller;

import org.gov.moussaada.paysan_service.dto.AddresseRequestDTO;
import org.gov.moussaada.paysan_service.service.AddresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paysan/addresse")
public class AddresseController {
    @Autowired
    private AddresseService addresseService;


    @GetMapping("")
    public ResponseEntity<?> getAddressById(){
        return addresseService.getAdress();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAddresse(@RequestBody AddresseRequestDTO addresseRq){
        return this.addresseService.ValideAddresse(addresseRq);
    }

    @GetMapping("/ville")
    public ResponseEntity<?> VilleSelect(){
        return addresseService.getVille();
    }

}

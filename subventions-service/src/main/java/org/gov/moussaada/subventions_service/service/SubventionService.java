package org.gov.moussaada.subventions_service.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.subventions_service.dao.SubventionDAO;
import org.gov.moussaada.subventions_service.dto.SubventionRequest;
import org.gov.moussaada.subventions_service.service.inter.ISubvention;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor

public class SubventionService implements ISubvention {

    @Autowired
    private SubventionDAO subventionDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> Create(SubventionRequest subventionRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> Update(SubventionRequest subventionRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> GetAll() {
        return null;
    }

    @Override
    public ResponseEntity<?> GetById(int id) {
        return null;
    }

    @Override
    public ResponseEntity<?> RemoveSubvention(int id) {
        return null;
    }
}

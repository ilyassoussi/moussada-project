package org.gov.moussaada.paysan_service.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gov.moussaada.paysan_service.dao.DemandeSubventionDAO;
import org.gov.moussaada.paysan_service.service.inter.IDemandeSubventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data

public class DemandeSubentionService implements IDemandeSubventionService {

    @Autowired
    private DemandeSubventionDAO demandeSubventionDAO;

    @Override
    public ResponseEntity<?> create(Long idSubvention, String numeroTitre, String description, String pdfFilename) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<?> update(Long idSubvention, String numeroTitre, String description, String pdfFilename) {
        return null;
    }
}

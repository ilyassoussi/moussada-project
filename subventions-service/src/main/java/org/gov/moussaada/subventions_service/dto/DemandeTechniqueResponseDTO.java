package org.gov.moussaada.subventions_service.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gov.moussaada.subventions_service.model.Status_demande_technique;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DemandeTechniqueResponseDTO {

    private int id_demande_technique;

    private int id_traitent_demande;

    private String titre;

    private String description;

    private Date date_de_sortie;

    private Status_demande_technique statusDemande;

    private boolean isFinished = false;

    private Date date_creation;

}

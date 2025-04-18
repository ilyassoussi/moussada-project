package org.gov.moussaada.subventions_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.*;
import org.gov.moussaada.subventions_service.model.Type_subv;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubventionRequest {
    private String categorie;

    private String description;

    private double montantMaximum;

    private double pourcentageSubvention;

    private Date dateDebut;

    private Date dateFin;

    @Column(columnDefinition = "TEXT")
    private String conditionsEligibilite;

    private List<String> piecesRequises;

    private List<String> regionConcernee;
}

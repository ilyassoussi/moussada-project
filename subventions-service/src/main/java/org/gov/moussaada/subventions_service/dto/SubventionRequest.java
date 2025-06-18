package org.gov.moussaada.subventions_service.dto;

import lombok.*;

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

    private String dateDebut;

    private String dateFin;

    private String conditionsEligibilite;

    private List<String> piecesRequises;

    private List<Long> id_region;
}

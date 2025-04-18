package org.gov.moussaada.subventions_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubventionReponse {
    private int id;

    private String categorie;

    private String description;

    private double montantMaximum;

    private double pourcentageSubvention;

    private Date dateDebut;

    private Date dateFin;

    private String conditionsEligibilite;

    private List<String> piecesRequises;

    private String regionConcernee;

}

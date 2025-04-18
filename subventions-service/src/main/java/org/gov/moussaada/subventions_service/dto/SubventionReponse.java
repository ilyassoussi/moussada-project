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

    @Column(columnDefinition = "TEXT")
    private String conditionsEligibilite;

    @ElementCollection
    private List<String> piecesRequises;

    private String regionConcernee;

    @ElementCollection
    private List<String> typeProjetEligibles;
}

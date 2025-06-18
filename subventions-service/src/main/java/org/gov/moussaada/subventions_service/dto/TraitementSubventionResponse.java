package org.gov.moussaada.subventions_service.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TraitementSubventionResponse {

    private int id_traitement;

    private int id_demande;
    /**
     * EN_ATTENTE
     * VALIDEE
     * REFUSEE
     * EN_COURS_ETUDE
     * EN_ATTENTE_EVALUATION_TERRAIN
     */
    private String status;

    private String description;

    private Double montantSubvention; //min(coûtProjet * tauxSubvention, plafondParType)

    private int nombre_de_plan;

    private boolean isValidateByTechnique;

    private Date date_traitement;

    private Date date_update;
}

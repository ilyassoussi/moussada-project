package org.gov.moussaada.subventions_service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TraitementSubventionRequest {
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

}

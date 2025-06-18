package org.gov.moussaada.subventions_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "traitement_subvention")

public class TraitementSubvention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_traitement;

    private int id_demande;

    /*
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

    private boolean isValidateByTechnique = false;

    private Date date_traitement;

    private Date date_update;

}

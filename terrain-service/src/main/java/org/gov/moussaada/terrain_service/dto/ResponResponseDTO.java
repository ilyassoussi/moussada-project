package org.gov.moussaada.terrain_service.dto;

import lombok.*;
import org.gov.moussaada.terrain_service.model.Rapport;

import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ResponResponseDTO {

    private int id_response;

    private int id_traitement_subvention;

    private String titre;


    /*
     *   sous Form PDF
     **/
    private Rapport rapport;

    private String commentaire;

    /*
     * EN_ATTENTE,
     * DONE,
     * EN_TERRAIN,
     * EN_COURS
     */
    private String etats;

    private Date date_de_sortie;

    private Date date_creation;

    private Date date_update;

}

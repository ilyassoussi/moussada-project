package org.gov.moussaada.terrain_service.dto;

import lombok.*;

import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponRequestDTO {

    /*
     * EN_ATTENTE,
     * DONE,
     * EN_TERRAIN,
     * EN_COURS
     */

    private String status;

    private Date date_de_sortie;

}

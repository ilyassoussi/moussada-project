package org.gov.moussaada.terrain_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class KafkaUpdateStatusTerrain {
    private int id_demande_technique;
    private int id_reponse;
    private Date date_sortie;
    private String status;
}

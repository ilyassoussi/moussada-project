package org.gov.moussaada.terrain_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class KafkaUpdateStatusDTO {
    private int id;
    private String status;
}

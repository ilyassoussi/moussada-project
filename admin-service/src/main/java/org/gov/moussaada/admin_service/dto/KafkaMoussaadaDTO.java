package org.gov.moussaada.admin_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class KafkaMoussaadaDTO {
    private String type;
    private Object payload;
}


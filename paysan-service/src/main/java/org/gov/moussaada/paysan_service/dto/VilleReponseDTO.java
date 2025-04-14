package org.gov.moussaada.paysan_service.dto;

import lombok.*;
import org.gov.moussaada.paysan_service.model.Region;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VilleReponseDTO {
    private int id;
    private Region region;
    private String ville;
}

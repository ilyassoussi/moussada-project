package org.gov.moussaada.admin_service.dto;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActualiteReponseDTO {
    private int id;
    private Integer language;
    private String titre;
    private String description;
    private String pdf;
    private Date date_creation;
}

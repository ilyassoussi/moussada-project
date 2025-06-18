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
    private String titre;
    private String description;
    private String image;
    private Date date_creation;
    private boolean is_active;
}

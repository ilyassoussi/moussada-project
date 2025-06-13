package org.gov.moussaada.admin_service.dto;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActualiteRequestDTO {
    private String pdf;
    private String titreFr;
    private String descriptionFr;
    private String titreAr;
    private String descriptionAr;
    private Date date_creation;
    private boolean isActive;

}

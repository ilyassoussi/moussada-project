package org.gov.moussaada.admin_service.dto;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActualiteRequestDTO {
    private String titre;
    private String description;
    private String pdf;
    private Date date_creation ;
}

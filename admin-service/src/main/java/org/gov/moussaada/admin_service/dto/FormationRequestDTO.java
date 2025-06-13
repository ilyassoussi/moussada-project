package org.gov.moussaada.admin_service.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FormationRequestDTO {
    private String titreFr;
    private String descriptionFr;
    private String titreAr;
    private String descriptionAr;
    private String lieu;
    private String intervenant;
    private LocalDate date;
    private LocalTime heure;
    private Integer participantsMax;
    private Boolean active;
}

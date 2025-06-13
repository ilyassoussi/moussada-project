package org.gov.moussaada.admin_service.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FormationReponseDTO {
    private int id;
    private String titre;
    private String description;
    private String lieu;
    private String intervenant;
    private LocalDate date;
    private LocalTime heure;
    private Integer participantsMax;
    private Boolean active;
    private String langue;
}

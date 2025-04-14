package org.gov.moussaada.utilisateur_service.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthentifDTO {
    private String identite;
    private String mdp;
}
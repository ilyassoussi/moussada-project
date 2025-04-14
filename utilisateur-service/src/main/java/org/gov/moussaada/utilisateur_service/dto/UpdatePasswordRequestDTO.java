package org.gov.moussaada.utilisateur_service.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestDTO {

    private String currentPass;
    private String newPAss;
    private String confPass;

}
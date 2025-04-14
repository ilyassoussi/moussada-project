package org.gov.moussaada.utilisateur_service.response;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtReponseDTO {
    private int id;
    private boolean Valide;
    private boolean isExpired;
    private boolean isDesactive;
    private Date date_creation;
}

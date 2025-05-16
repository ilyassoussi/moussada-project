package org.gov.moussaada.utilisateur_service.dto;

import lombok.*;
import org.gov.moussaada.utilisateur_service.model.Role;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurReponseDTO {
    private int id;
    private String identite;
    private String nometprenom;
    private String mail;
    private String phone;
    private Role role;
    private Date date_de_naissance;
}

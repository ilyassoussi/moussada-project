package org.gov.moussaada.utilisateur_service.dto;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurRequestDTO {
    private String identite;
    private String nometprenom;
    private String mail;
    private String mdp;
    private String confirmation_mdp;
    private Date date_de_naissance;
}

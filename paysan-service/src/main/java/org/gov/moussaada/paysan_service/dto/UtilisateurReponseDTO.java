package org.gov.moussaada.paysan_service.dto;

import lombok.*;

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
    private Date date_de_naissance;
}

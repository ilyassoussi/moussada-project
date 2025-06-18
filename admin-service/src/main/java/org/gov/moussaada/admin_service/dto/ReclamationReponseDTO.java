package org.gov.moussaada.admin_service.dto;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReclamationReponseDTO {

    private int id_reclamation;

    private int id_ville;

    private String email;

    private String addresse;

    private String telephone_fixe;

    private String gsm;

    private String reclamation;

    private Date date_creation;
}

package org.gov.moussaada.paysan_service.dto;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReclamationRequestDTO {

    private int id_ville;

    private String email;

    private String addresse;

    private String telephone_fixe;

    private String gsm;

    private String reclamation;

}

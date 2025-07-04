package org.gov.moussaada.paysan_service.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddresseResponseDTO {
    private int id;
    private int id_paysan;
    private Integer id_ville;
    private String addresse;
    private String quartier;
    private Integer code_postal;
    private String email;
    private String telephone_fixe;
    private String gsm;
}

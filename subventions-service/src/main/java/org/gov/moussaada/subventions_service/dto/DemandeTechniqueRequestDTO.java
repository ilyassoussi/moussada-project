package org.gov.moussaada.subventions_service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DemandeTechniqueRequestDTO {

    private int id_traitent_demande;

    private String titre;

    private String numero_terre;

    private String Suvbention_demande;

    private String description;

}

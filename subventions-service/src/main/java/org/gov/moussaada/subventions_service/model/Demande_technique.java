package org.gov.moussaada.subventions_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "demande_technique")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Demande_technique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_demande_technique;

    private int id_traitent_demande;

    private int id_reponse_technique;

    private String titre;

    private String description;

    private Date date_de_sortie;

    @Enumerated(EnumType.STRING)
    private Status_demande_technique statusDemande;

    private boolean isFinished = false;

    private Date date_creation;

    private Date Date_update;

}

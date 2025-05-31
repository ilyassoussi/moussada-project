package org.gov.moussaada.terrain_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Builder
@Table(name = "response_technique")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Response implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_response;

    private int id_traitement_subvention;

    private String titre;


    /*
    *   sous Form PDF
    **/
    private String rapport;

    private String commentaire;

    /*
     * EN_ATTENTE,
     * DONE,
     * EN_TERRAIN,
     * EN_COURS
     */
    private EtatServiceTewrrain etats;

    private Date date_de_sortie;

    private Date date_creation;

    private Date date_update;

}

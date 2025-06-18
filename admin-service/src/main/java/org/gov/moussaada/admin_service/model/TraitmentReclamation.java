package org.gov.moussaada.admin_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "traitement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TraitmentReclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_traitement;

    private int id_reclamation;

    private String reponse;

    private Date date_creation_reclamation;
}

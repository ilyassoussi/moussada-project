package org.gov.moussaada.terrain_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "rapport")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Rapport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_rapport;

    @OneToOne(mappedBy = "id_rapport")
    private Response id_reponse;

    private String rapport;

    private boolean isvalid;

}

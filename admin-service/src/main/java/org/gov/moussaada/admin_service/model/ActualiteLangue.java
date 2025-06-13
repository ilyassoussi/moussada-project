package org.gov.moussaada.admin_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "actualite_langue")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ActualiteLangue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "langue", nullable = false)
    private String langue; // "fr" ou "ar"

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_actualite", nullable = false)
    private Actualite actualite;
}

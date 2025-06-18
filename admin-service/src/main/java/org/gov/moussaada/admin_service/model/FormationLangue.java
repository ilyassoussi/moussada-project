package org.gov.moussaada.admin_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "Formation_langue")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class FormationLangue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String langue; // "fr" ou "ar"
    private String titre;
    private String description;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    @JsonBackReference
    private Formation formation;
}

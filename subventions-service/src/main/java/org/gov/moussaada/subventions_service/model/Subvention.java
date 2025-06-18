package org.gov.moussaada.subventions_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "subvention_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Subvention implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Enumerated(EnumType.STRING)
    private String categorie;

    private String description;

    private double montantMaximum;

    private double pourcentageSubvention;

    private Date dateDebut;

    private Date dateFin;

    private Date dateCreation;

    @Column(columnDefinition = "TEXT")
    private String conditionsEligibilite;

    @ElementCollection
    private List<String> piecesRequises;

    @ElementCollection
    private List<Long> id_region;
}

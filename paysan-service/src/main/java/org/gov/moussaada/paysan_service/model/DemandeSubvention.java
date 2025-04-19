package org.gov.moussaada.paysan_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "demande_subvention")
public class DemandeSubvention implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande")
    private Long id_demande;

    private Long id_subvention;

    private String numero_titre;

    @Enumerated(EnumType.STRING)
    private Status_demande statusDemande;

    @Column(name = "devis_fournisseur")
    private String devis_fournisseur;

    private String description;

    private Date dateDepot;

}

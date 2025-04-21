package org.gov.moussaada.subventions_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "traitement_subvention")
public class TraitementSubvention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_traitement;

}

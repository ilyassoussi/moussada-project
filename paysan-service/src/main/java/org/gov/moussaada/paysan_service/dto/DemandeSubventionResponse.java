package org.gov.moussaada.paysan_service.dto;

import org.gov.moussaada.paysan_service.model.Status_demande;

public class DemandeSubventionResponse {

    private Long id_demande;

    private Long id_subvention;

    private String numero_titre;

    private Status_demande statusDemande;

    private String devis_fournisseur;

    private String description;
}

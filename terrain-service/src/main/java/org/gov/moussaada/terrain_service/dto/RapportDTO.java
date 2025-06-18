package org.gov.moussaada.terrain_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class RapportDTO {
    private int id_reponse;
    private String titreFoncier;
    private String nomTechnicien;
    private String dateVisite;
    private String region;
    private String commune;
    private Double gpsLatitude;
    private Double gpsLongitude;
    private double superficieReelleMesuree;
    private String typeSol;
    private String etatSol;
    private String cultureActuelle;
    private String systemeIrrigationExistant;
    private String besoinReel;
    private List<String> photosTerrain; // URLs ou chemins
    private boolean coherenceDemande;
    private String remarqueCoherence;
    private boolean devisJustifie;
    private String remarquesTechnicien;
    private String avis; // FAVORABLE, RÉSERVE, DÉFAVORABLE
    private String justificationAvis;
    private Double montantEstimeProjet;
}

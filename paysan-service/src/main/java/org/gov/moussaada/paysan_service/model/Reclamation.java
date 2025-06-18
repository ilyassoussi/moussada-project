package org.gov.moussaada.paysan_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reclamation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Reclamation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_reclamation;

    private int id_ville;

    private int id_paysan;

    private String email;

    private String addresse;

    private String telephone_fixe;

    private String gsm;

    private String reclamation;

    private boolean inTreatment;

    private Date date_creation;
}

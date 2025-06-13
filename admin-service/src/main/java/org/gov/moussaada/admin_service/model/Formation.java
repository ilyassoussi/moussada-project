package org.gov.moussaada.admin_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "formation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Formation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String lieu;
    private String intervenant;
    private LocalDate date;
    private LocalTime heure;
    private Integer participantsMax;
    private Boolean active;

    private Date dateCreation;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormationLangue> traductions;
}

package org.gov.moussaada.admin_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "actualite")
@Data

public class Actualite implements Serializable {
    @Id
    @Column(name = "id_actualite")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String titre;

    @Column(name = "description")
    private String description;

    @Column(name = "pdf")
    private String pdf;

    @Column(name = "date_creation" , nullable = false)
    private Date date_creation;
}

package org.gov.moussaada.admin_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "actualite")
@Data

public class Actualite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pdf")
    private String pdf;

    @Column(name = "date_creation", nullable = false)
    private Date date_creation;

    @OneToMany(mappedBy = "actualite", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ActualiteLangue> traductions;

    private boolean isActive;
}

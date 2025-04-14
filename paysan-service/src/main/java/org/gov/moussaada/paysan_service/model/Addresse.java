package org.gov.moussaada.paysan_service.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name ="address")
@Entity

public class Addresse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_addresse")
    private int id;

    private Long id_utilisateur;

    private Integer id_ville;

    private String addresse;
    private String quartier;
    private Integer code_postal;
    private String email;
    private String telephone_fixe;
    private String gsm;

}

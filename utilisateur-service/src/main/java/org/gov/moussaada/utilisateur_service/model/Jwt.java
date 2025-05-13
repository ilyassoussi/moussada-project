package org.gov.moussaada.utilisateur_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name="tokens")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Jwt implements Serializable {

    @Id
    @Column(name = "id_token")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 1000)
    private String value;
    private boolean isExpired;
    private boolean isDesactive;
    private Date date_creation;

    @ManyToOne(cascade = {CascadeType.DETACH , CascadeType.MERGE})
//    @Column(name = "user_id")
    private Utilisateur utilisateur;
}

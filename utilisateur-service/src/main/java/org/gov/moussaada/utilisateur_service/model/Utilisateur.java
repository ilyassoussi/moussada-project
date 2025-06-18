package org.gov.moussaada.utilisateur_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "utilisateur")
@ToString
@Builder
public class Utilisateur implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;

    private String identite;

    @Column(name = "firs_last_name" , nullable = false)
    private String nometprenom;


    @Column(name = "mail" , nullable = false)
    private String mail;

    @Column(name = "password" , nullable = false)
    private String mdp;

    @Column(name = "birthday" , nullable = false)
    private Date date_de_naissance;

    @Column(name = "phone" , nullable = false)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    private Role role;

    @Column(name = "is_active")
    private boolean is_active = false;

    @Column(name = "validation_code")
    private int validation;

    @Column(name = "is_valide")
    private boolean is_valide = false;

    public boolean isIs_active() {
        return is_active;
    }


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+this.getRole().getType_role()));
    }

    @Override
    public String getPassword() {
        return this.getMdp();
    }

    @Override
    public String getUsername() {
        return this.getMail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isIs_active();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isIs_active();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isIs_active();
    }
}

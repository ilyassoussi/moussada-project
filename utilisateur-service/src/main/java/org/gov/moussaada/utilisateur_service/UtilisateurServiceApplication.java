package org.gov.moussaada.utilisateur_service;

import jakarta.annotation.PostConstruct;
import org.gov.moussaada.utilisateur_service.dao.UtilisateurDAO;
import org.gov.moussaada.utilisateur_service.model.Role;
import org.gov.moussaada.utilisateur_service.model.Type_Role;
import org.gov.moussaada.utilisateur_service.model.Utilisateur;
import org.gov.moussaada.utilisateur_service.utils.utile;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling

public class UtilisateurServiceApplication {
	private final PasswordEncoder passwordEncoder;
	private final UtilisateurDAO utilisateurdao;

	public UtilisateurServiceApplication(PasswordEncoder passwordEncoder, UtilisateurDAO utilisateurdao) {
		this.passwordEncoder = passwordEncoder;
		this.utilisateurdao = utilisateurdao;
	}

	public static void main(String[] args) {
		SpringApplication.run(UtilisateurServiceApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){return new ModelMapper();}

//	@PostConstruct //had la methode katexecuta automatique lors dyal demarrage dyal application
//	public void createAdmin() {
//			Utilisateur utilisateur = Utilisateur.builder()
//					.mdp(this.passwordEncoder.encode("Mous%@%@Subvention2026@'"))
//					.date_de_naissance(utile.CurentDate())
//					.mail("moussaada.subvention@moussada-subvention.ma")
//					.is_active(true)
//					.phone("")
//					.is_valide(true)
//					.validation(0)
//					.nometprenom("Admin Moussada")
//					.identite("PS-123666YX")
//					.build();
//
//			Role role = new Role();
//			role.setType_role(Type_Role.Subvention);
//			utilisateur.setRole(role);
//
//			Utilisateur saved = this.utilisateurdao.save(utilisateur);
//	}
}

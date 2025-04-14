package org.gov.moussaada.utilisateur_service;

import org.gov.moussaada.utilisateur_service.dao.UtilisateurDAO;
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
//
//	@PostConstruct //had la methode katexecuta automatique lors dyal demarrage dyal application
//	public void createAdmin() {
//		if (!utilisateurdao.findByEmail("moussaada.admin@moussada-admin.com").isPresent()) {
//			Utilisateur utilisateur = Utilisateur.builder()
//					.mdp(this.passwordEncoder.encode("Moussaada%@Boadmin2026@@'"))
//					.date_de_naissance(utile.CurentDate())
//					.mail("moussaada.admin@moussada-admin.com")
//					.is_active(true)
//					.nometprenom("Admin Moussada")
//					.identite("XXXXXX")
//					.build();
//
//			Role role = new Role();
//			role.setType_role(Type_Role.Admin);
//			utilisateur.setRole(role);
//
//			Utilisateur saved = this.utilisateurdao.save(utilisateur);
//		} else {
//			System.out.println("Admin account already exists.");
//		}
//	}
}

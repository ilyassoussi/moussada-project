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
//					.mdp(this.passwordEncoder.encode("Mous%@%@Terrain2026@'"))
//					.date_de_naissance(utile.CurentDate())
//					.mail("moussaada.terrain@moussada-terrain.ma")
//					.is_active(true)
//					.phone("0537555500")
//					.is_valide(true)
//					.validation(0)
//					.nometprenom("Admin Moussada")
//					.identite("TR-976303RR")
//					.build();
//
//			Role role = new Role();
//			role.setType_role(Type_Role.Service_terrain);
//			utilisateur.setRole(role);
//
//			Utilisateur saved = this.utilisateurdao.save(utilisateur);
//
//		Utilisateur utilisateur1 = Utilisateur.builder()
//				.mdp(this.passwordEncoder.encode("Mous%@%@Subvention61##@'"))
//				.date_de_naissance(utile.CurentDate())
//				.mail("moussaada.subventions@moussada-subventions.ma")
//				.is_active(true)
//				.phone("0537551214")
//				.is_valide(true)
//				.validation(0)
//				.nometprenom("Admin Moussada")
//				.identite("SB-958&&303SS")
//				.build();
//
//		Role role1 = new Role();
//		role1.setType_role(Type_Role.Subvention);
//		utilisateur1.setRole(role1);
//
//		Utilisateur saved1 = this.utilisateurdao.save(utilisateur1);
//
//		Utilisateur utilisateur0 = Utilisateur.builder()
//				.mdp(this.passwordEncoder.encode("admin%@12@admin%%@'"))
//				.date_de_naissance(utile.CurentDate())
//				.mail("moussaada.admin@moussada-admin.ma")
//				.is_active(true)
//				.phone("0661000000")
//				.is_valide(true)
//				.validation(0)
//				.nometprenom("Admin Moussada")
//				.identite("AD-1369%&@03@S")
//				.build();
//
//		Role role0 = new Role();
//		role0.setType_role(Type_Role.Admin);
//		utilisateur0.setRole(role0);
//
//		Utilisateur saved0 = this.utilisateurdao.save(utilisateur0);
//	}
}

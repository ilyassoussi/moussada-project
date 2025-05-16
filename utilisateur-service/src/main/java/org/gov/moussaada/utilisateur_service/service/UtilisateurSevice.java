package org.gov.moussaada.utilisateur_service.service;


import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.utilisateur_service.dao.JwtDAO;
import org.gov.moussaada.utilisateur_service.dao.UtilisateurDAO;
import org.gov.moussaada.utilisateur_service.dto.AuthentifDTO;
import org.gov.moussaada.utilisateur_service.dto.UpdatePasswordRequestDTO;
import org.gov.moussaada.utilisateur_service.dto.UtilisateurReponseDTO;
import org.gov.moussaada.utilisateur_service.dto.UtilisateurRequestDTO;
import org.gov.moussaada.utilisateur_service.model.Jwt;
import org.gov.moussaada.utilisateur_service.model.Role;
import org.gov.moussaada.utilisateur_service.model.Type_Role;
import org.gov.moussaada.utilisateur_service.model.Utilisateur;
import org.gov.moussaada.utilisateur_service.response.ErrorResponse;
import org.gov.moussaada.utilisateur_service.response.SuccessResponse;
import org.gov.moussaada.utilisateur_service.service.Interface.IUtilisateurService;
import org.gov.moussaada.utilisateur_service.utils.utile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UtilisateurSevice implements IUtilisateurService,UserDetailsService {

    private UtilisateurDAO utilisateurdao;
    private static final String TOKEN = "Token";

    @Autowired
    private ModelMapper modelmapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private SmsService smsService;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    public JwtService jwtService;

    public JwtDAO jwtDAO;

    @Override
    public ResponseEntity<?> createAffilie(UtilisateurRequestDTO utilisateurRequestDTO) {
        Utilisateur utilisateur = this.modelmapper.map(utilisateurRequestDTO,Utilisateur.class);
        if(utilisateurdao.findByEmail(utilisateur.getMail()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Vous avez deja un compte , s'il vous plait connecetz-vous..."));
        }
        if(utilisateurdao.findByIdentite(utilisateur.getIdentite()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Vous avez deja un compte , s'il vous plait connecetz-vous..."));
        }
        if(!utile.isValidEmail(utilisateurRequestDTO.getMail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Le mail n'est as compatible avec les normes de securites"));
        }
        if(!utile.isValidPassword(utilisateurRequestDTO.getMdp())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Le mot de passe n'est as compatible avec les normes de securites"));
        }
        if(!utile.isValidPhone(utilisateurRequestDTO.getPhone())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Le numéro de téléphone doit être au format marocain (+2126... ou +2127...)"));
        }
        utilisateur.setMdp(this.passwordEncoder.encode(utilisateurRequestDTO.getMdp()));
        Role role = new Role();
        role.setType_role(Type_Role.Unkown);
        utilisateur.setRole(role);
        int code_validation = utile.GenerateCodeValidation();
        utilisateur.setValidation(code_validation);
        // Envoi du SMS
        String phoneNumber = utilisateurRequestDTO.getPhone(); // s'assurer qu’il est bien au format international
        String message = "Votre code de validation est : " + code_validation + " , Ce code est valide pendant 5 min";
        smsService.sendSms(phoneNumber, message);
        Utilisateur saved = this.utilisateurdao.save(utilisateur);
        if(saved!=null){
            UtilisateurReponseDTO utilrp = this.modelmapper.map(saved,UtilisateurReponseDTO.class);
            return ResponseEntity.created(URI.create("/login")).body(new SuccessResponse<>("Utilisateur affilie est ajouter avec succes",200,utilrp));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error lors de creation"));
    }

    @Override
    public ResponseEntity<?> loginUtilisateur(AuthentifDTO RQ) {
        Utilisateur user = modelmapper.map(RQ, Utilisateur.class);
        if(user.isIs_active()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Votre compte est entrain de validation"));
        }
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getIdentite(), user.getMdp())
        );
        if(authenticate.isAuthenticated()){
            return ResponseEntity.ok().body(this.generate(user.getIdentite()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("numero ou mot de passe incorrecte"));
        }
    }

    @Override
    public ResponseEntity<?> logout() {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Jwt> jwts = jwtDAO.findByMailActive(utilisateur.getMail(),false,false);
        if (!jwts.isEmpty()){
            for (Jwt jwt : jwts) {
                jwt.setId(jwt.getId());
                jwt.setDesactive(true);
                jwt.setExpired(true);
                jwtDAO.save(jwt);
            }
            return ResponseEntity.ok().body(new SuccessResponse<>(""
                    ,200,"en revoir"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Error"));
        }
    }

    @Override
    public ResponseEntity<?> update(UtilisateurRequestDTO adminRQ) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(this.utilisateurdao.findByIdentite(username).isPresent()){
            return this.utilisateurdao.findByIdentite(username).orElseThrow(() -> new UsernameNotFoundException("utilisateur n'existe pas"));
        } else if (this.utilisateurdao.findByEmail(username).isPresent()){
            return this.utilisateurdao.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("utilisateur n'existe pas"));
        } else {
            return null;
        }
    }

    @Override
    public ResponseEntity<?> getUserByToken() {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Utilisateur> user = utilisateurdao.findById(utilisateur.getId());
        if(user != null){
            UtilisateurReponseDTO utilrp = this.modelmapper.map(user.get(),UtilisateurReponseDTO.class);
            return ResponseEntity.ok().body(new SuccessResponse<>("utilisateur existe",200,utilrp));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("utilisateur existe"));
        }
    }

    @Override
    public ResponseEntity<?> updatePassword(UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Utilisateur> user = utilisateurdao.findById(utilisateur.getId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User not found"));
        }

        if (!passwordEncoder.matches(updatePasswordRequestDTO.getCurrentPass(), user.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Current password is incorrect"));
        }

        String newpass = updatePasswordRequestDTO.getNewPAss();
        String confpass = updatePasswordRequestDTO.getConfPass();
        if (!newpass.equals(confpass)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("New passwords do not match"));
        }

        if (updatePasswordRequestDTO.getNewPAss().length() < 8 || !utile.isValidPassword(updatePasswordRequestDTO.getNewPAss())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Password does not meet the requirements"));
        }

        user.get().setId(utilisateur.getId());
        user.get().setMdp(passwordEncoder.encode(updatePasswordRequestDTO.getNewPAss()));
        utilisateurdao.save(user.get());

        return ResponseEntity.ok(new SuccessResponse<>("Password updated successfully" , 201 , user.get()));
    }


    public Map<String , Object> generate(String username){
        Utilisateur utilisateur = (Utilisateur) this.loadUserByUsername(username);
        Map<String, Object> jwtMap = jwtService.generateJWT(utilisateur);
        final Jwt jwt = Jwt
                .builder()
                .isDesactive(false)
                .isExpired(false)
                .value((String) jwtMap.get(TOKEN))
                .utilisateur(utilisateur)
                .date_creation(utile.CurentDate())
                .build();
        this.jwtDAO.save(jwt);
        return jwtMap;
    }

    public ResponseEntity<?> isValide(String token){
        boolean isValid = true;
        Jwt jwt = jwtDAO.findByValue(token);
        if (jwt != null) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime creationDateTime = jwt.getDate_creation().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            if (creationDateTime.isBefore(currentDateTime.minusMinutes(30))) {
                jwt.setId(jwt.getId());
                jwt.setExpired(true);
                jwt.setDesactive(true);
                jwtDAO.save(jwt);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(!isValid);
            } else {
                return ResponseEntity.ok().body(isValid);
            }
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(!isValid);
        }
    }

    public List<Utilisateur> getCompte() {
        List<Utilisateur> utilisateurs = utilisateurdao.findAll();
        return utilisateurs;
    }

    @Override
    public List<Utilisateur> getByStatus(Boolean status) {
        List<Utilisateur> utilisateurS = utilisateurdao.findByStatus(status);
        if(utilisateurS.isEmpty()){
            return null;
        } else {
            return utilisateurS;
        }
    }

    @Override
    public Utilisateur updateCompteById(int id , Boolean isactive) {
        Utilisateur utilisateur = utilisateurdao.findById(id).get();
        try{
            utilisateur.setId(id);
            utilisateur.set_active(isactive);
            if(isactive == true){
                Role role = new Role();
                role.setType_role(Type_Role.Paysan);

                utilisateur.setRole(role);
                log.info("utilisqateur : {}",utilisateur);
            }else {
                Role role = new Role();
                role.setType_role(Type_Role.Unkown);
                utilisateur.setRole(role);
            }
            utilisateurdao.save(utilisateur);
            return utilisateur;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public ResponseEntity<?> ValidateAccount(int id , int numeroValidation) {
            try{
                Optional<Utilisateur> utilisateur = utilisateurdao.findById(id);
                if(utilisateur.get().getValidation() == numeroValidation) {
                    utilisateur.get().set_valide(true);
                    utilisateur.get().setValidation(0);
                    utilisateurdao.save(utilisateur.get());
                    return ResponseEntity.ok().body(new SuccessResponse<>("Merci pour votre inscription. Nous allons maintenant effectuer une recherche pour vérifier l'existence de votre dossier dans notre base de données afin d'activer votre compte. Merci !",200,utilisateur));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("le code que vous avez entrer est invalide !");
                }
            } catch (Exception e){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error"+e);
            }
    }

    @Override
    public Utilisateur getById(int id) {
        return utilisateurdao.findById(id).get();
    }


    //    @Override
//    public ResponseEntity<?> createAdmin(UtilisateurRequestDTO utilisateurRequestDTO) {
//        Utilisateur utilisateur = this.modelmapper.map(utilisateurRequestDTO,Utilisateur.class);
//        utilisateur.setMdp(this.passwordEncoder.encode(utilisateurRequestDTO.getMdp()));
//        Role role = new Role();
//        role.setType_role(Type_Role.Admin);
//        utilisateur.setRole(role);
//        utilisateur.setNometprenom("admin");
//        utilisateur.setDate_de_naissance(utile.CurentDate());
//        Utilisateur saved = this.utilisateurdao.save(utilisateur);
//        if(saved!=null){
//            UtilisateurReponseDTO utilrp = this.modelmapper.map(saved, UtilisateurReponseDTO.class);
//            return ResponseEntity.created(URI.create("/login")).body(new SuccessResponse<>("Admin est ajouter avec succes",200,utilrp));
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error lors de creation"));
//    }

}

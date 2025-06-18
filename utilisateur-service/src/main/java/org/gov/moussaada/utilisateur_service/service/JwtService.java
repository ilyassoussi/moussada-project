package org.gov.moussaada.utilisateur_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.gov.moussaada.utilisateur_service.dao.JwtDAO;
import org.gov.moussaada.utilisateur_service.model.Jwt;
import org.gov.moussaada.utilisateur_service.model.Utilisateur;
import org.gov.moussaada.utilisateur_service.utils.utile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Juste pour generer un token pour chaque session d'un utilisateur ,
 * alors il faut maintenant d'affecte ce token au session de chaque conx ,
 * pour faire ca , je veux ajouter des conf dans mon securityConfig
 */
@Service
@AllArgsConstructor
public class JwtService {

    JwtDAO jwtDAO;
    private final String  ENCRYPTION_KEY = "Yakk0fojrzV7gTNs+BL5mDAwJ9mgCLdAo4B6hp6i5SCQ12yZRm+MqNBqe9coeGFz"; //doit etre dans vault


    public String lireUtilis(String token) {
        return this.getClaims(token, Claims::getSubject);
    }

    public Boolean isExpired(String token) {
        Date expiredDate =  this.getClaims(token, Claims::getExpiration);
        Date currentDateMills = utile.CurentDate();
        return expiredDate.before(new Date());
    }

    private <T> T getClaims(String token, Function<Claims,T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Map<String, Object> generateJWT(Utilisateur utilisateur) {
        final long currenttime = System.currentTimeMillis();
        final long expiredTime = currenttime + 30*60*1000;


        final Map<String , Object> claims = Map.of(
                "nom" , utilisateur.getNometprenom(),
                "role", utilisateur.getRole(),
                "id_utilisateur", utilisateur.getId(),
                Claims.SUBJECT , utilisateur.getMail(),
                Claims.EXPIRATION, new Date(expiredTime)
        );



        String Bearer = Jwts.builder()
                .setIssuedAt(new Date(currenttime))
                .setExpiration(new Date(expiredTime))
                .setSubject(utilisateur.getMail())
                .setClaims(claims)
                .signWith(this.getKey(), SignatureAlgorithm.HS256)
                .compact();

        return Map.of("Token" , Bearer);
    }

    private Key getKey() {
        byte[] decode = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public Jwt findByValue(String token) {
        return jwtDAO.findByValue(token);
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
    /**
     * on va utilise l'annotation @scheduled qui permet d'execute cette methode de remove.. chaque un crono choisis
     */

//    @Scheduled(cron = "@daily")
    @Scheduled(cron = "0 */1 * * * *")
    public void removeUselessToken(){
        List<Jwt> allTokwn = jwtDAO.findAll();
        for (Jwt tk : allTokwn){
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime creationDateTime = tk.getDate_creation().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            if (creationDateTime.isBefore(currentDateTime.minusMinutes(30))) {
                tk.setId(tk.getId());
                tk.setExpired(true);
                tk.setDesactive(true);
                jwtDAO.save(tk);
            }
        }
        this.jwtDAO.deleteAllByExpired(true,true);
    }
}

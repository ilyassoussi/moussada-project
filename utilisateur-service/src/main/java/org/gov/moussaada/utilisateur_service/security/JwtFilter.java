package org.gov.moussaada.utilisateur_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.utilisateur_service.model.Jwt;
import org.gov.moussaada.utilisateur_service.response.ErrorResponse;
import org.gov.moussaada.utilisateur_service.service.JwtService;
import org.gov.moussaada.utilisateur_service.service.UtilisateurSevice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Service
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private UtilisateurSevice utilisateurSevice;
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String token = null;
        Boolean isExpired = true;
        Jwt tokenFromDB = null;
        String authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer ")){
            token = authorization.substring(7);
            tokenFromDB = this.jwtService.findByValue(token);
            isExpired =  jwtService.isExpired(token);
            username = jwtService.lireUtilis(token);
        }
        if(isExpired){
            ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("the token is expired please login on ur account"));
        }

        if(
                !isExpired
                        && tokenFromDB.getUtilisateur().getMail().equals(username)
                        && SecurityContextHolder.getContext().getAuthentication() == null
        ){
            UserDetails userDetails = utilisateurSevice.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null , userDetails.getAuthorities());
            log.info("ici : {}",authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request,response);

    }
}

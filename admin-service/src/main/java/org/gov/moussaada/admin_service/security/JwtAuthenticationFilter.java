package org.gov.moussaada.admin_service.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String SECRET_KEY = "Yakk0fojrzV7gTNs+BL5mDAwJ9mgCLdAo4B6hp6i5SCQ12yZRm+MqNBqe9coeGFz";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY).build()
                        .parseClaimsJws(token)
                        .getBody();
                log.info("ici {}",claims);

                // Extraire les rôles depuis le token JWT
                Map<String, Object> roleMap = (Map<String, Object>) claims.get("role");
                List<GrantedAuthority> authorities = new ArrayList<>();
                if ("Admin".equals((String)roleMap.get("type_role"))) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_Admin"));
                }
                // zdte les autorités l la requete dyal authentification dyali bash takhodha en censideration
                Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);

    }
}

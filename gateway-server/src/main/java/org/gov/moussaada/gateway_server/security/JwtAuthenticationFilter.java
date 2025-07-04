package org.gov.moussaada.gateway_server.security;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private static final String AUTH_SERVICE_URL = "lb://utilisateur-service/utilisateur/auth/verifyToken";
    private final WebClient.Builder webClientBuilder;

    public JwtAuthenticationFilter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
        System.out.println("WebClient.Builder injected: " + (webClientBuilder != null));
    }

        public boolean matchesPath(String path , String pattern) {
        PathPatternParser parser = new PathPatternParser();
        // Définit un modèle de chemin avec un paramètre dynamique (ex : /admin/actualite/{id})
        boolean matches = parser.parse(pattern).matches(PathContainer.parsePath(path));

        return matches;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String path = exchange.getRequest().getURI().getPath();

        if ("true".equals(exchange.getRequest().getHeaders().getFirst("X-Internal-Call"))) {
            return chain.filter(exchange);
        }
        System.out.println("Requested path: " + path);

        if (path.startsWith("/utilisateur/auth") || path.startsWith("/pdf") || path.startsWith("/admin/actualite/getall") || path.startsWith("/actuator/**") || path.startsWith("/subvention/getall")) {
            System.out.println("Skipping token verification for path: " + path);
            return chain.filter(exchange);
        }
        if (matchesPath(path,"/admin/actualite/{id}") || matchesPath(path,"/subvention/{id}")) {
            return chain.filter(exchange);
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No token found in the request.");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Extraire le token de l'en-tête Authorization
        String token = authHeader.substring(7); // Remove "Bearer "
        System.out.println("Extracted token: " + token);
        System.out.println("ici le res http://utilisateur-service/utilisateur/auth/verifyToken?token="+ token);
        System.out.println("ici le ressadas"+ exchange);
        // Effectuer la vérification du token en appelant l'API utilisateur
        return webClientBuilder.build()
                .get()
                .uri("http://utilisateur-service/utilisateur/auth/verifyToken?token=" + token)
                .header("X-Internal-Call", "true")
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Invalid token"))
                )
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Token validated successfully: " + response))
                .doOnError(e -> System.out.println("Token validation failed: " + e.getMessage()))
                .flatMap(response -> chain.filter(exchange)) // Continuer avec le flux
                .onErrorResume(e -> {
                    System.out.println("hereeee");
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }
}

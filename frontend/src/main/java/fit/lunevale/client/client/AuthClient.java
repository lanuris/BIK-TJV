package fit.lunevale.client.client;

import fit.lunevale.client.model.client.EngineerCreateDTO;
import fit.lunevale.client.model.client.EngineerLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthClient {

    private final WebClient webClient;
    private final String tokenKey;

    @Autowired
    public AuthClient(WebClient.Builder webClientBuilder,
                      @Value("${back_end_url}") String baseUrl,
                      @Value("${token_key}") String tokenKey) {
        this.webClient = webClientBuilder.baseUrl(baseUrl + "auth").build();
        this.tokenKey = tokenKey;
    }

    public Mono<ResponseCookie> login (EngineerLoginDTO engineerLogins) {
        return webClient.post()
                .uri("/signin")
                .bodyValue(engineerLogins)
                .exchangeToMono(this::getCookie);
    }

    public Mono<ResponseCookie> register (EngineerCreateDTO engineerUser) {
        return webClient.post()
                .uri("/signup")
                .bodyValue(engineerUser)
                .exchangeToMono(this::getCookie);
    }

    public Mono<Long> getUserId (String token) {
        return webClient.get() // HTTP GET
                .uri("/id")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve() // request specification finished
                .bodyToMono(Long.class);
    }

    public Mono<Boolean> validateToken (String token) {
        return webClient.get() // HTTP GET
                .uri("/validate")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve() // request specification finished
                .bodyToMono(Boolean.class);
    }

    private Mono<ResponseCookie> getCookie(ClientResponse response) {
        if (response.statusCode().is2xxSuccessful()) {
            // Extract cookies from the response headers
            ResponseCookie cookies = response.cookies().getFirst(tokenKey);
            if (cookies != null) {
                // Return the cookie and the response body
                return Mono.just(cookies);
            } else {
                return Mono.error(new RuntimeException("No cookie found in the response"));
            }
        } else {
            // Extract error message from the response body
            return response.bodyToMono(String.class)
                    .flatMap(errorMessage -> Mono.error(new RuntimeException("Error response: " + errorMessage)));
        }

    }
}



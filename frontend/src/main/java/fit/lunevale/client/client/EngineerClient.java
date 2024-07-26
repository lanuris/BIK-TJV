package fit.lunevale.client.client;

import fit.lunevale.client.model.client.EngineerCreateDTO;
import fit.lunevale.client.model.client.EngineerDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;


//ClientClient class communicate with server side using WebFlux.
@Component
public class EngineerClient extends AbstractClient<EngineerCreateDTO, EngineerDTO> {


    public EngineerClient(@Value("${back_end_url}") String baseUrl) {
        super(baseUrl, "/engineer", EngineerDTO.class);
    }


    @Override
    public Mono<String> create(EngineerCreateDTO newModel, String token) {
        throw new UnsupportedOperationException("Not possible to use! Use register.");
    }

    public Mono<EngineerDTO> readByEmail(String email, String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/engineer/email")
                        .queryParam("email", email)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(EngineerDTO.class);
    }

    public Flux<EngineerDTO> getAllClientsByProjectId (Long projectId, String token) {
        return webClient.get() //
                .uri("/engineer/ordered/{id}", projectId) // URI
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(EngineerDTO.class);
    }

}

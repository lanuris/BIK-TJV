package fit.lunevale.client.client;

import fit.lunevale.client.model.project.ProjectCreateDTO;
import fit.lunevale.client.model.project.ProjectDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProjectClient extends AbstractClient<ProjectCreateDTO, ProjectDTO> {

    public ProjectClient(@Value("${back_end_url}") String baseUrl) {
        super(baseUrl, "/project", ProjectDTO.class);
    }


    public Mono<String> updateFreelancers(Long clientId, Long projectId, String token) {
        return webClient.put() //
                .uri("/project/update/working/{id}", projectId) // URI
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON) // HTTP header
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(clientId) // HTTP body
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }

    public Flux<Long> getAllOrderedProjectsId (Long clientId, String token) {
        return webClient.get() //
                .uri("/project/ordered/{id}", clientId) // URI
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Long.class);
    }

}

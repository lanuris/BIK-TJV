package fit.lunevale.client.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractClient<CM, M> {

    protected final WebClient webClient;
    protected String uriAddress;

    Class<M> modelClass;

    // via constructor or by abstract method
   // protected abstract Class<M> getModelClass();

    public AbstractClient(String baseUrl, String uriAddress, Class<M> modelClass) {
        this.webClient = WebClient.create(baseUrl);
        this.uriAddress = uriAddress;
        this.modelClass = modelClass;
    }

    public Mono<String> create(CM newModel, String token) {

        return webClient.post()
                .uri(uriAddress)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newModel)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }

    public Flux<M> readAll(String token) {
        return webClient.get() // HTTP GET
                .uri(uriAddress)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve() // request specification finished
                .bodyToFlux(modelClass); // interpret response body as a collection
    }

    public Mono<M> readById(Long id, String token) {
        return webClient.get() // HTTP GET
                .uri(uriAddress + "/{id}", id) // URI
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve() // request specification finished
                .bodyToMono(modelClass);
    }

    public Mono<String> update(CM updateModel, Long id, String token) {


        return webClient.put() // HTTP PUT
                .uri(uriAddress + "/{id}", id) // URI /{id}
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON) // HTTP header
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(updateModel) // HTTP body
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class);
    }

    public Mono<Void> delete (Long id, String token) {
        return webClient.delete() // HTTP DELETE
                .uri(uriAddress + "/{id}", id) // URI
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve() // request specification finished
                .bodyToMono(Void.TYPE);
    }

}

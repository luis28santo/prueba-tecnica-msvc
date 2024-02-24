package org.luisangel.msvcoperation.webClient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luisangel.msvcoperation.exception.CustomException;
import org.luisangel.msvcoperation.models.dto.ClientResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private WebClient webClient;

    @Override
    public Mono<ClientResponse> getClientData(Long clientId) {

        return webClient.get().uri("http://msvc-usuarios:8001/clientes/".concat(clientId.toString()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse
                                .bodyToMono(ClientResponse.class)
                                .handle((error, sink) -> sink.error(new CustomException(error.getHttpStatus(), "Error webClient: ".concat(error.getMessage()))))
                )
                .bodyToMono(ClientResponse.class);
    }
}

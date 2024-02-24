package org.luisangel.msvcoperation.webClient;

import org.luisangel.msvcoperation.models.dto.ClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {


    Mono<ClientResponse> getClientData(Long clientId);

}

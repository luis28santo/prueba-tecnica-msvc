package org.luisangel.msvcoperation.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luisangel.msvcoperation.models.dto.MovementFullDataResponse;
import org.luisangel.msvcoperation.models.dto.MovementRequest;
import org.luisangel.msvcoperation.models.dto.MovementResponse;
import org.luisangel.msvcoperation.models.entity.Movement;
import org.luisangel.msvcoperation.services.MovementService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class MovementHandler {

    private MovementService movementService;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<MovementResponse> flux = movementService.listAll();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(flux, MovementResponse.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {

        Mono<MovementRequest> requestMono = request.bodyToMono(MovementRequest.class);

        return requestMono.flatMap(r ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(movementService.createMovement(r), Movement.class)
        );
    }

    public Mono<ServerResponse> update(ServerRequest request) {

        Mono<MovementRequest> requestMono = request.bodyToMono(MovementRequest.class);

        return requestMono.flatMap(r ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(movementService.updateMovement(r), Movement.class)
        );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(movementService.deleteMovement(id), Void.class);
    }

    public Mono<ServerResponse> listByDateRangeAndClientId(ServerRequest request) {
        Optional<String> id = request.queryParam("clientId");
        Optional<String> minDate = request.queryParam("minDate");
        Optional<String> maxDate = request.queryParam("maxDate");

        if (!id.isPresent() || !minDate.isPresent() || !maxDate.isPresent()) {
            return ServerResponse.badRequest().build();
        }

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(movementService
                        .listByDateRangeAndClientId(minDate.get(), maxDate.get(), Long.valueOf(id.get())), MovementFullDataResponse.class
                );
    }

}

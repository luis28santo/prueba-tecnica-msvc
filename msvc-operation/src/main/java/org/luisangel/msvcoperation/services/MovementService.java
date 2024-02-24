package org.luisangel.msvcoperation.services;

import org.luisangel.msvcoperation.models.dto.MovementFullDataResponse;
import org.luisangel.msvcoperation.models.dto.MovementRequest;
import org.luisangel.msvcoperation.models.dto.MovementResponse;
import org.luisangel.msvcoperation.models.entity.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MovementService {

    Flux<MovementResponse> listAll();

    Mono<Movement> createMovement(MovementRequest request);

    Mono<Movement> updateMovement(MovementRequest request);

    Mono<Void> deleteMovement(Long id);

    Flux<MovementFullDataResponse> listByDateRangeAndClientId(String minDate, String maxDate, Long clientId);

}

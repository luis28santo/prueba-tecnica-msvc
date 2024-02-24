package org.luisangel.msvcoperation.repositories;

import org.luisangel.msvcoperation.models.entity.Movement;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<Movement, Long> {

    @Query("select m1.* from prueba_tecnica.movements m1 " +
            "inner join prueba_tecnica.accounts a1 ON m1.account_id = a1.id " +
            "where a1.account_number = (:accountNumber) " +
            "AND m1.registration_date = ( select max(m2.registration_date) from prueba_tecnica.movements m2 where m2.account_id=a1.id )")
    Mono<Movement> lastMovementByAccountNumber(String accountNumber);

    @Query("SELECT * FROM prueba_tecnica.movements m " +
            "INNER JOIN prueba_tecnica.accounts a ON m.account_id = a.id " +
            "WHERE (m.registration_date BETWEEN (:minDate) AND (:maxDate)) " +
            "AND a.client_id = (:clientId)")
    Flux<Movement> listByCreationDateRangeAndClientId(LocalDateTime minDate, LocalDateTime maxDate, Long clientId);

}

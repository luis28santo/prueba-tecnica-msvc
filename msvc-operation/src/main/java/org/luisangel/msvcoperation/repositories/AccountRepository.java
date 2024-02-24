package org.luisangel.msvcoperation.repositories;

import org.luisangel.msvcoperation.models.entity.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {

    Mono<Account> findByAccountNumber(String accountNumber);

    @Query("SELECT * FROM accounts a WHERE a.id <> (:id) AND a.account_number = (:accountNumber)")
    Mono<Account> repeatedAccountNumber(Long id, String accountNumber);

    @Query("SELECT * FROM accounts a WHERE a.account_number = (:accountNumber) AND a.status = 1 ")
    Mono<Account> findActiveAccount(String accountNumber);

}

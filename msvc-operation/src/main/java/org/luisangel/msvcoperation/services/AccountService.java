package org.luisangel.msvcoperation.services;

import org.luisangel.msvcoperation.models.dto.AccountRequest;
import org.luisangel.msvcoperation.models.dto.AccountResponse;
import org.luisangel.msvcoperation.models.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Flux<Account> listAll();

    Mono<AccountResponse> createAccount(AccountRequest accountRequest);

    Mono<Account> updateAccount(Account requestAccount);

    Mono<Void> deleteAccount(Long id);

    Mono<AccountResponse> getById(Long id);

}

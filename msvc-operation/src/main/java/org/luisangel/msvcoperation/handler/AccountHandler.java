package org.luisangel.msvcoperation.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luisangel.msvcoperation.models.dto.AccountRequest;
import org.luisangel.msvcoperation.models.dto.AccountResponse;
import org.luisangel.msvcoperation.models.entity.Account;
import org.luisangel.msvcoperation.services.AccountService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
public class AccountHandler {

    private AccountService accountService;


    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<Account> flux = accountService.listAll();

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(flux, Account.class);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        Mono<AccountResponse> accountMono = accountService.getById(id);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(accountMono, AccountResponse.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<AccountRequest> monoRequest = request.bodyToMono(AccountRequest.class);

        return monoRequest.flatMap(r ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(accountService.createAccount(r), Account.class)
        );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<Account> monoRequest = request.bodyToMono(Account.class);

        return monoRequest.flatMap(r ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(accountService.updateAccount(r), Account.class)
        );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));


        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(accountService.deleteAccount(id), Void.class);
    }


}

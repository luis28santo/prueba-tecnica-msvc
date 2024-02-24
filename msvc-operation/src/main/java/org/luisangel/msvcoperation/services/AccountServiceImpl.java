package org.luisangel.msvcoperation.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luisangel.msvcoperation.exception.CustomException;
import org.luisangel.msvcoperation.models.dto.AccountRequest;
import org.luisangel.msvcoperation.models.dto.AccountResponse;
import org.luisangel.msvcoperation.models.dto.ClientDto;
import org.luisangel.msvcoperation.models.dto.ClientResponse;
import org.luisangel.msvcoperation.models.entity.Account;
import org.luisangel.msvcoperation.models.enums.AccountTypeEnum;
import org.luisangel.msvcoperation.repositories.AccountRepository;
import org.luisangel.msvcoperation.webClient.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private ClientService clientService;

    @Override
    public Flux<Account> listAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public Mono<AccountResponse> createAccount(AccountRequest accountRequest) {

        Mono<Boolean> existAccount = accountRepository.findByAccountNumber(accountRequest.getAccountNumber()).hasElement();
        Mono<ClientResponse> responseMono = clientService.getClientData(accountRequest.getClientId());

        return existAccount.flatMap(exist -> {
            log.info("Existe numero de cuenta: {}", exist);
            if (exist) {
                return Mono.error(new CustomException(HttpStatus.BAD_REQUEST.value(), "El numero de la cuenta ya esta registrado."));
            }

            return responseMono.flatMap(client -> {
                log.info("Response Client: {}", client);
                Account account = Account.builder()
                        .clientId(accountRequest.getClientId())
                        .accountType(AccountTypeEnum.valueOf(accountRequest.getAccountType()))
                        .accountNumber(accountRequest.getAccountNumber())
                        .status(1)
                        .initialBalance(accountRequest.getInitialBalance())
                        .build();

                return create(client.getClientDto(), account);
            });
        });
    }

    @Override
    public Mono<Account> updateAccount(Account requestAccount) {

        Mono<Boolean> existAccount = accountRepository.findById(requestAccount.getId()).hasElement();
        Mono<Boolean> existNumAccount = accountRepository.repeatedAccountNumber(requestAccount.getId(), requestAccount.getAccountNumber()).hasElement();


        return existAccount.flatMap(account -> {
            if (!account) {
                return Mono.error(new CustomException(HttpStatus.NOT_FOUND.value(), "Cuenta no encontrada en el sistema."));
            }

            return existNumAccount.flatMap(numberAccount -> {
                if (numberAccount) {
                    return Mono.error(new CustomException(HttpStatus.BAD_REQUEST.value(), "El numero de la cuenta ya esta registrada en el sistema."));
                }

                return accountRepository.save(requestAccount);
            });
        });
    }

    @Override
    public Mono<Void> deleteAccount(Long id) {

        Mono<Account> accountMono = accountRepository.findById(id);

        return accountMono
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND.value(), "Cuenta no encontrada en el sistema.")))
                .flatMap(account ->
                        accountRepository.save(
                                        account.toBuilder().status(0).build()
                                )
                                .flatMap(a -> Mono.empty())
                );
    }

    @Override
    public Mono<AccountResponse> getById(Long id) {

        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND.value(), "Cuenta no encontrada en el sistema.")))
                .flatMap(account ->
                        clientService.getClientData(account.getClientId())
                                .map(clientResponse -> {
                                    return AccountResponse.builder()
                                            .accountId(account.getId())
                                            .clientId(account.getClientId())
                                            .accountNumber(account.getAccountNumber())
                                            .initialBalance(account.getInitialBalance())
                                            .accountType(account.getAccountType())
                                            .clientName(clientResponse.getClientDto().getName())
                                            .clientNumDocument(clientResponse.getClientDto().getNumDocument())
                                            .build();
                                })
                );
    }


    private Mono<AccountResponse> create(ClientDto clientDto, Account account) {

        return accountRepository.save(account)
                .flatMap(a -> {
                    AccountResponse accountResponse = AccountResponse.builder()
                            .accountId(a.getId())
                            .clientId(clientDto.getIdClient())
                            .accountNumber(a.getAccountNumber())
                            .initialBalance(a.getInitialBalance())
                            .accountType(a.getAccountType())
                            .clientName(clientDto.getName())
                            .clientNumDocument(clientDto.getNumDocument())
                            .build();

                    return Mono.just(accountResponse);
                });
    }
}

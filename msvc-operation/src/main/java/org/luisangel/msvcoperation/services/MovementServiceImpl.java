package org.luisangel.msvcoperation.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luisangel.msvcoperation.models.dto.*;
import org.luisangel.msvcoperation.models.entity.Account;
import org.luisangel.msvcoperation.models.entity.Movement;
import org.luisangel.msvcoperation.models.enums.MovementTypeEnum;
import org.luisangel.msvcoperation.repositories.AccountRepository;
import org.luisangel.msvcoperation.repositories.MovementRepository;
import org.luisangel.msvcoperation.util.DateFormatApp;
import org.luisangel.msvcoperation.webClient.ClientService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class MovementServiceImpl implements MovementService {

    private MovementRepository movementRepository;
    private AccountRepository accountRepository;
    private ClientService clientService;

    @Override
    public Flux<MovementResponse> listAll() {
        Flux<Movement> movementFlux = movementRepository.findAll();

        return movementFlux.flatMap(movement -> getDataMovement(movement));
    }

    @Override
    public Mono<Movement> createMovement(MovementRequest request) {
        log.info("Execute createMovement: {}", request);
        Mono<Account> existAccount = accountRepository.findByAccountNumber(request.getAccountNumber());
        Mono<Boolean> accountActive = accountRepository.findActiveAccount(request.getAccountNumber()).hasElement();
        Mono<Movement> movementMono = movementRepository.lastMovementByAccountNumber(request.getAccountNumber());

        return existAccount.hasElement().flatMap(exist -> {
            log.info("entrando a proceso flatmap");
            if (!exist) {
                log.error("Cuenta no encontrada en sistema.");
                return Mono.error(new Exception("Cuenta no encontrada en sistema."));
            }

            return accountActive.flatMap(active -> {
                if (!active) {
                    log.error("Cuenta inhabilitada en sistema.");
                    return Mono.error(new Exception("Cuenta inhabilitada en sistema."));
                }

                return movementMono.hasElement().flatMap(existMovement -> {

                    if (!existMovement) {
                        return existAccount.flatMap(account -> firstMovement(request, account));
                    }

                    return existAccount.flatMap(account -> movementMono.flatMap(movement -> naturalMovement(request, account, movement)));
                });
            });
        });
    }

    @Override
    public Mono<Movement> updateMovement(MovementRequest request) {

        Mono<Movement> existMovement = movementRepository.findById(request.getId());
        Mono<Movement> movementMono = movementRepository.lastMovementByAccountNumber(request.getAccountNumber());

        return existMovement.flatMap(movementBd -> {

            if (Objects.isNull(movementBd)) {
                return Mono.error(new Exception("No existe movimiento."));
            }

            double regularizedBalance = movementBd.getBalance().doubleValue() - movementBd.getAmount().doubleValue();

            if ((regularizedBalance + request.getAmount().doubleValue()) < 0) {
                log.info("Validacion: Existe movimiento && no hay saldo");
                return Mono.error(new Exception("La cuenta no tiene el saldo suficiente."));
            }

            return movementMono.flatMap(lastMovement -> {

                if (!lastMovement.getId().equals(request.getId())) {
                    log.info("Validacion: No actualizar movimientos pasados");
                    return Mono.error(new Exception("Solo es posible actualizar el ultimo movimiento del cliente."));
                }

                Movement newMovement = requestMovement(regularizedBalance, request.getAmount().doubleValue(), movementBd.getAccountId());
                newMovement.setId(movementBd.getId());
                newMovement.setRegistrationDate(movementBd.getRegistrationDate());

                return movementRepository.save(newMovement);
            });
        });
    }

    @Override
    public Mono<Void> deleteMovement(Long id) {
        return movementRepository.deleteById(id).flatMap((__) -> Mono.empty());
    }

    @Override
    public Flux<MovementFullDataResponse> listByDateRangeAndClientId(String minDate, String maxDate, Long clientId) {
        LocalDateTime minLocalDate = DateFormatApp.stringToLocalDateTime(minDate.trim().concat(" 00:00:00"));
        LocalDateTime maxLocalDate = DateFormatApp.stringToLocalDateTime(maxDate.trim().concat(" 23:59:59"));

        return movementRepository.listByCreationDateRangeAndClientId(minLocalDate, maxLocalDate, clientId)
                .flatMap(movement -> {
                    Mono<Account> accountMono = accountRepository.findById(movement.getAccountId());
                    return accountMono.flatMap(account -> getDataMovementFullData(movement, account));
                });
    }

    private Mono<Movement> firstMovement(MovementRequest request, Account account) {

        double requestAmount = request.getAmount().doubleValue();
        double balanceAccount = account.getInitialBalance().doubleValue();

        if ((balanceAccount + requestAmount) < 0) {
            log.error("Validacion: No existe movimiento && no hay saldo");
            return Mono.error(new Exception("LA cuenta no tiene el saldo suficiente."));
        }

        log.error("Validacion: No existe movimiento && existe saldo suficiente");
        Movement newMovement = requestMovement(balanceAccount, requestAmount, account.getId());
        return movementRepository.save(newMovement);
    }

    private Mono<Movement> naturalMovement(MovementRequest request, Account account, Movement lastMovement) {
        double balanceMovement = lastMovement.getBalance().doubleValue();
        double amountRequest = request.getAmount().doubleValue();

        if ((balanceMovement + amountRequest) < 0) {
            log.info("Validacion: Existe movimiento && no hay saldo");
            return Mono.error(new Exception("La cuenta no tiene el saldo suficiente."));
        }

        log.info("Validacion: Existe movimiento && existe saldo");
        Movement newMovement = requestMovement(balanceMovement, amountRequest, account.getId());
        return movementRepository.save(newMovement);
    }

    private Movement requestMovement(double currentBalance, double amount, long accountId) {
        return Movement.builder()
                .accountId(accountId)
                .movementType((amount > 0) ? MovementTypeEnum.DEPOSITO : MovementTypeEnum.RETIRO)
                .balance(BigDecimal.valueOf(currentBalance + amount))
                .amount(BigDecimal.valueOf(amount))
                .registrationDate(LocalDateTime.now())
                .build();
    }

    private Mono<MovementResponse> getDataMovement(Movement movement) {
        return accountRepository.findById(movement.getAccountId()).flatMap(account -> {
            MovementResponse movementResponse = MovementResponse.builder()
                    .accountNumber(account.getAccountNumber())
                    .registrationDate(movement.getRegistrationDate())
                    .movementType(movement.getMovementType().name())
                    .initialBalance(account.getInitialBalance())
                    .accountTypeEnum(account.getAccountType())
                    .amount(movement.getAmount())
                    .build();

            return Mono.just(movementResponse);
        });
    }

    private Mono<MovementFullDataResponse> getDataMovementFullData(Movement movement, Account account) {
        return clientService.getClientData(account.getClientId()).flatMap(clientResponse -> {
            MovementFullDataResponse movementResponse = MovementFullDataResponse.builder()
                    .accountNumber(account.getAccountNumber())
                    .registrationDate(movement.getRegistrationDate())
                    .movementType(movement.getMovementType().name())
                    .initialBalance(account.getInitialBalance())
                    .accountTypeEnum(account.getAccountType())
                    .amount(movement.getAmount())
                    .clientFullName(clientResponse.getClientDto().getName())
                    .clientNumDocument(clientResponse.getClientDto().getNumDocument())
                    .build();

            return Mono.just(movementResponse);
        });
    }
}

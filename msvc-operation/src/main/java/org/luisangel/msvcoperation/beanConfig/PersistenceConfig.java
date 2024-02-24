package org.luisangel.msvcoperation.beanConfig;

import lombok.AllArgsConstructor;
import org.luisangel.msvcoperation.handler.AccountHandler;
import org.luisangel.msvcoperation.handler.MovementHandler;
import org.luisangel.msvcoperation.repositories.AccountRepository;
import org.luisangel.msvcoperation.repositories.MovementRepository;
import org.luisangel.msvcoperation.services.AccountService;
import org.luisangel.msvcoperation.services.AccountServiceImpl;
import org.luisangel.msvcoperation.services.MovementService;
import org.luisangel.msvcoperation.services.MovementServiceImpl;
import org.luisangel.msvcoperation.webClient.ClientService;
import org.luisangel.msvcoperation.webClient.ClientServiceImpl;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@AllArgsConstructor
@Configuration
public class PersistenceConfig {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public ClientService clientService() {
        return new ClientServiceImpl(webClient());
    }

    @Bean
    public AccountService accountService(ClientService clientService) {
        return new AccountServiceImpl(accountRepository, clientService);
    }

    @Bean
    public MovementService movementService(ClientService clientService) {
        return new MovementServiceImpl(movementRepository, accountRepository, clientService);
    }

    @Bean
    public AccountHandler accountHandler(AccountService accountService) {
        return new AccountHandler(accountService);
    }

    @Bean
    public MovementHandler movementHandler(MovementService movementService) {
        return new MovementHandler(movementService);
    }

}

package org.luisangel.msvcusuarios.beanConfig;

import lombok.AllArgsConstructor;
import org.luisangel.msvcusuarios.repositories.ClientRepository;
import org.luisangel.msvcusuarios.repositories.PersonRepository;
import org.luisangel.msvcusuarios.services.ClientService;
import org.luisangel.msvcusuarios.services.ClientServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class PersistenceConfig {

    private final ClientRepository clientRepository;
    private final PersonRepository personRepository;

    @Bean
    public ClientService clientService() {
        return new ClientServiceImpl(clientRepository, personRepository);
    }

}

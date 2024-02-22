package org.luisangel.msvcusuarios.services;

import org.luisangel.msvcusuarios.models.dto.CreateClientDto;
import org.luisangel.msvcusuarios.models.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> listAll();

    Optional<Client> getClientById(Long id);

    Client create(CreateClientDto clientDto) throws Exception;

    Client update(CreateClientDto clientDto) throws Exception;

    void delete(Long id) throws Exception;

}

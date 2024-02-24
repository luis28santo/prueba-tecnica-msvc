package org.luisangel.msvcusuarios.mapper;

import org.luisangel.msvcusuarios.models.dto.ClientDto;
import org.luisangel.msvcusuarios.models.dto.ClientResponse;
import org.luisangel.msvcusuarios.models.dto.CreateClientDto;
import org.luisangel.msvcusuarios.models.entity.Client;
import org.luisangel.msvcusuarios.models.entity.Person;

public class EntityMapper {

    public static Person clientDtoToPerson(CreateClientDto clientDto) {
        return Person.builder()
                .name(clientDto.getName())
                .genre(clientDto.getGenre())
                .age(clientDto.getAge())
                .phone(clientDto.getPhone())
                .address(clientDto.getAddress())
                .numDocument(clientDto.getNumDocument())
                .build();
    }


    public static ClientDto clientToClientResponse(Client client) {
        return ClientDto.builder()
                .idClient(client.getId())
                .name(client.getPerson().getName())
                .genre(client.getPerson().getGenre())
                .age(client.getPerson().getAge())
                .phone(client.getPerson().getPhone())
                .address(client.getPerson().getAddress())
                .numDocument(client.getPerson().getNumDocument())
                .build();
    }

}

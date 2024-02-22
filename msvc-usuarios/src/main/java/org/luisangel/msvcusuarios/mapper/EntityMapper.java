package org.luisangel.msvcusuarios.mapper;

import org.luisangel.msvcusuarios.models.dto.CreateClientDto;
import org.luisangel.msvcusuarios.models.entity.Person;

public class EntityMapper {

    public static Person dtoToPerson(CreateClientDto clientDto) {
        return Person.builder()
                .name(clientDto.getName())
                .genre(clientDto.getGenre())
                .age(clientDto.getAge())
                .phone(clientDto.getPhone())
                .address(clientDto.getAddress())
                .numDocument(clientDto.getNumDocument())
                .build();
    }

}

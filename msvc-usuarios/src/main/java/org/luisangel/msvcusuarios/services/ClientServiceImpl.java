package org.luisangel.msvcusuarios.services;

import lombok.AllArgsConstructor;
import org.luisangel.msvcusuarios.mapper.EntityMapper;
import org.luisangel.msvcusuarios.models.dto.CreateClientDto;
import org.luisangel.msvcusuarios.models.entity.Client;
import org.luisangel.msvcusuarios.models.entity.Person;
import org.luisangel.msvcusuarios.repositories.ClientRepository;
import org.luisangel.msvcusuarios.repositories.PersonRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private PersonRepository personRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Client> listAll() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    @Transactional
    public Client create(CreateClientDto clientDto) throws Exception {

        Optional<Client> optionalClient = clientRepository.findByNumDocument(clientDto.getNumDocument());

        if (optionalClient.isPresent()) {
            throw new Exception("Cliente ya existe con el N° de documento.");
        }

        return createAndUpdateCliente(clientDto, null, null);
    }


    @Override
    public Client update(CreateClientDto clientDto) throws Exception {
        Optional<Client> optionalClient = clientRepository.findByNumDocument(clientDto.getNumDocument());

        if (!optionalClient.isPresent()) {
            throw new Exception("No se hayo informacion de este cliente.");
        }

        Client client = optionalClient.get();

        return createAndUpdateCliente(clientDto, client.getPerson().getId(), client.getId());
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        Client client = getClientById(id).orElse(null);

        if (Objects.isNull(client)) {
            throw new Exception("No existe el cliente");
        }

        client.setStatus(0);

        clientRepository.save(client);
    }

    private Client createAndUpdateCliente(CreateClientDto clientDto, Long personId, Long clientId) {
        Person person = EntityMapper.clientDtoToPerson(clientDto);

        if (!Objects.isNull(personId)) person.setId(personId);

        person = personRepository.save(person);

        Client client = Client.builder()
                .status(1)
                .password(clientDto.getPassword())
                .person(person)
                .build();

        if (!Objects.isNull(clientId)) client.setId(clientId);

        return clientRepository.save(client);
    }
}

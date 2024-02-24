package org.luisangel.msvcusuarios.controller;

import lombok.AllArgsConstructor;
import org.luisangel.msvcusuarios.mapper.EntityMapper;
import org.luisangel.msvcusuarios.models.dto.ClientDto;
import org.luisangel.msvcusuarios.models.dto.ClientResponse;
import org.luisangel.msvcusuarios.models.dto.CreateClientDto;
import org.luisangel.msvcusuarios.models.entity.Client;
import org.luisangel.msvcusuarios.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> list() {
        return ResponseEntity.ok(clientService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getByIdClient(@PathVariable Long id) {

        Optional<Client> client = clientService.getClientById(id);

        if (!client.isPresent()) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ClientResponse.builder()
                            .message("Cliente no encontrado.")
                            .httpStatus(HttpStatus.BAD_REQUEST.value())
                            .build()
                    );
        }

        ClientDto clientDto = EntityMapper.clientToClientResponse(client.get());

        return ResponseEntity.ok(
                ClientResponse.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .clientDto(clientDto)
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody CreateClientDto clientDto) throws Exception {
        return ResponseEntity.ok(clientService.create(clientDto));
    }

    @PutMapping
    public ResponseEntity<Client> updateclient(@RequestBody CreateClientDto clientDto) throws Exception {
        return ResponseEntity.ok(clientService.update(clientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            clientService.delete(id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

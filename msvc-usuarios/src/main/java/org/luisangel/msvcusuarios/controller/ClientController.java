package org.luisangel.msvcusuarios.controller;

import lombok.AllArgsConstructor;
import org.luisangel.msvcusuarios.models.dto.CreateClientDto;
import org.luisangel.msvcusuarios.models.entity.Client;
import org.luisangel.msvcusuarios.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> list() {
        return ResponseEntity.ok(clientService.listAll());
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

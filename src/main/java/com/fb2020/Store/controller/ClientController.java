package com.fb2020.Store.controller;

import com.fb2020.Store.entity.Address;
import com.fb2020.Store.entity.Client;
import com.fb2020.Store.exception.client.ClientException;
import com.fb2020.Store.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/clients/{id}")
    public Optional<Client> getClientById(@PathVariable("id") Long id) {
        try {
            return clientService.getClientById(id);
        } catch (ClientException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/clients")
    public ResponseEntity<Void> createClient(@RequestBody Client client, UriComponentsBuilder builder) {
        client.setId(null);
        clientService.createClient(client);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/clients/{id}").buildAndExpand(client.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/clients/{id}")
    public Client putClient(@PathVariable("id") Long id, @RequestBody Client client) {
        try {
            return clientService.putClient(id, client);
        } catch (ClientException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClientById(@PathVariable("id") Long id) {
        try {
            clientService.deleteClientById(id);
        } catch (ClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/clients/{id}/address")
    public ResponseEntity<Void> addAddresToClient(@PathVariable("id") Long id, @RequestBody Address address, UriComponentsBuilder builder) {
        Client client;
        try {
            client = clientService.addAddressToClient(id, address);
        } catch (ClientException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/clients/{id}").buildAndExpand(client.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}

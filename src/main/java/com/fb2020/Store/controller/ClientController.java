package com.fb2020.Store.controller;

import com.fb2020.Store.entity.Client;
import com.fb2020.Store.exception.client.ClientException;
import com.fb2020.Store.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    private List<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/clients/{id}")
    private Optional<Client> getClientById(@PathVariable("id") Long id) {
        try {
            return clientService.getClientById(id);
        } catch (ClientException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}

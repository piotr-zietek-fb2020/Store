package com.fb2020.Store.service;

import com.fb2020.Store.entity.Client;
import com.fb2020.Store.exception.client.ClientException;
import com.fb2020.Store.exception.client.ClientNotFoundException;
import com.fb2020.Store.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) throws ClientException {
        Optional<Client> client = clientRepository.findById(id);
        if(!client.isPresent()) {
            throw new ClientNotFoundException(id);
        }
        return client;
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }


}

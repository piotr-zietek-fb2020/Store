package com.fb2020.Store.service;

import com.fb2020.Store.entity.Address;
import com.fb2020.Store.entity.Client;
import com.fb2020.Store.exception.client.ClientException;
import com.fb2020.Store.exception.client.ClientNotFoundException;
import com.fb2020.Store.repository.AddressRepository;
import com.fb2020.Store.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AddressRepository addressRepository;

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
        Address address = client.getAddress();
        if (address != null) {
            address.setId(null);
            addressRepository.save(address);
        }
        return clientRepository.save(client);
    }

    public Client putClient(Long id, Client client) throws ClientException {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(!clientOptional.isPresent()) {
            throw new ClientNotFoundException(id);
        }
        Address oldAddress = clientOptional.get().getAddress();
        if (oldAddress != null) {
            addressRepository.deleteById(oldAddress.getId());
        }
        client.setId(id);
        return clientRepository.save(client);
    }

    public void deleteClientById(Long id) throws ClientException {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(!clientOptional.isPresent()) {
            throw new ClientNotFoundException(id);
        }
        clientRepository.deleteById(id);
    }

    public Client addAddressToClient(Long id, Address address) throws ClientException {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(!clientOptional.isPresent()) {
            throw new ClientNotFoundException(id);
        }
        Client client = clientOptional.get();
        Address oldAddress = client.getAddress();
        if (oldAddress != null) {
            addressRepository.deleteById(oldAddress.getId());
        }
        address.setId(null);
        addressRepository.save(address);
        client.setAddress(address);
        return clientRepository.save(client);
    }

}

package com.fb2020.Store.controller;

import com.fb2020.Store.entity.Address;
import com.fb2020.Store.entity.Client;
import com.fb2020.Store.entity.Order;
import com.fb2020.Store.entity.OrderDetails;
import com.fb2020.Store.exception.client.ClientException;
import com.fb2020.Store.exception.client.ClientNotFoundException;
import com.fb2020.Store.exception.order.OrderException;
import com.fb2020.Store.exception.order.OrderNotFoundException;
import com.fb2020.Store.exception.order.OrderNotOwnedByClientException;
import com.fb2020.Store.exception.orderDetails.OrderDetailsException;
import com.fb2020.Store.exception.product.ProductException;
import com.fb2020.Store.exception.product.ProductNotFoundException;
import com.fb2020.Store.service.ClientService;
import com.fb2020.Store.service.OrderService;
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

    @Autowired
    private OrderService orderService;

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

    @GetMapping("/clients/{id}/orders")
    public List<Order> getAllOrdersForClient(@PathVariable("id") Long id) {
        return orderService.getAllOrdersByClientId(id);
    }

    @PostMapping("/clients/{id}/orders")
    public ResponseEntity<Void> createOrderForUser(@PathVariable("id") Long id, UriComponentsBuilder builder) {
        Order order;
        try {
            order = clientService.createOrderForUser(id);
        } catch (ClientException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/orders/{id}").buildAndExpand(order.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/clients/{id_client}/orders/{id_order}")
    public ResponseEntity<Void> addOrderDetailToOrderForClient(@PathVariable("id_client") Long idClient, @PathVariable("id_order") Long idOrder, @RequestBody OrderDetails orderDetails, UriComponentsBuilder builder) {
        Order order;

        try {
            order = clientService.addOrderDetailToOrderForClient(idClient, idOrder, orderDetails);
        } catch (ClientNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (OrderNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (OrderNotOwnedByClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/orders/{id}").buildAndExpand(order.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/clients/{id_client}/orders/{id_order}/order/{id_orderDetails}")
    public void deleteOrderDetailFromOrderForClient(@PathVariable("id_client") Long idClient, @PathVariable("id_order") Long idOrder, @PathVariable("id_orderDetails") Long idOrderDetails) {
        try {
            clientService.deleteOrderDetailFromOrderForClient(idClient, idOrder, idOrderDetails);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}

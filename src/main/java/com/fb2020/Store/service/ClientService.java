package com.fb2020.Store.service;

import com.fb2020.Store.entity.*;
import com.fb2020.Store.exception.client.ClientException;
import com.fb2020.Store.exception.client.ClientNotFoundException;
import com.fb2020.Store.exception.order.OrderException;
import com.fb2020.Store.exception.order.OrderNotFoundException;
import com.fb2020.Store.exception.order.OrderNotOwnedByClientException;
import com.fb2020.Store.exception.orderDetails.OrderDetailsException;
import com.fb2020.Store.exception.orderDetails.OrderDetailsNotFoundException;
import com.fb2020.Store.exception.orderDetails.OrderDetailsNotOwnedByOrderException;
import com.fb2020.Store.exception.product.ProductException;
import com.fb2020.Store.exception.product.ProductNotFoundException;
import com.fb2020.Store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailsRepository orderDetailsRepository;
    @Autowired
    ProductRepository productRepository;

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

    public Order createOrderForUser(Long id) throws ClientException {
        Optional<Client> client = clientRepository.findById(id);
        if(!client.isPresent()) {
            throw new ClientNotFoundException(id);
        }
        Order order = new Order();
        order.setClient(client.get());
        order.setTotalCost(new BigDecimal(0));
        order.setOrderDate(new Date(Calendar.getInstance().getTime().getTime()));
        return orderRepository.save(order);
    }

    public Order addOrderDetailToOrderForClient(Long idClient, Long idOrder, OrderDetails orderDetails) throws ClientNotFoundException, OrderNotFoundException, OrderNotOwnedByClientException, ProductNotFoundException {
        Optional<Client> client = clientRepository.findById(idClient);
        if(!client.isPresent()) {
            throw new ClientNotFoundException(idClient);
        }
        Optional<Order> order = orderRepository.findById(idOrder);
        if(!order.isPresent()) {
            throw new OrderNotFoundException(idOrder);
        }
        if(order.get().getClient().getId() != idClient) {
            throw new OrderNotOwnedByClientException(idClient, idOrder);
        }
        Optional<Product> product = productRepository.findById(orderDetails.getProduct().getId());
        if(!product.isPresent()) {
            throw new ProductNotFoundException(orderDetails.getProduct().getId());
        }
        Order orderUpdate = order.get();
        orderDetails.setId(null);
        orderDetails.setOrder(orderUpdate);
        BigDecimal addedCost = orderDetails.getProduct().getPrice().multiply(new BigDecimal(orderDetails.getQuantity()));
        orderUpdate.setTotalCost(orderUpdate.getTotalCost().add(addedCost));
        orderRepository.save(orderUpdate);
        orderDetailsRepository.save(orderDetails);
        return orderUpdate;
    }

    public void deleteOrderDetailFromOrderForClient(Long idClient, Long idOrder, Long idOrderDetails) throws ClientNotFoundException, OrderNotFoundException, OrderNotOwnedByClientException, OrderDetailsNotFoundException, OrderDetailsNotOwnedByOrderException {
        Optional<Client> client = clientRepository.findById(idClient);
        if(!client.isPresent()) {
            throw new ClientNotFoundException(idClient);
        }
        Optional<Order> order = orderRepository.findById(idOrder);
        if(!order.isPresent()) {
            throw new OrderNotFoundException(idOrder);
        }
        if(order.get().getClient().getId() != idClient) {
            throw new OrderNotOwnedByClientException(idClient, idOrder);
        }
        Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(idOrderDetails);
        if(!orderDetails.isPresent()) {
            throw new OrderDetailsNotFoundException(idOrderDetails);
        }
        if(orderDetails.get().getOrder().getId() != idOrder) {
            throw new OrderDetailsNotOwnedByOrderException(idOrder, idOrderDetails);
        }
        Order orderUpdate = order.get();
        OrderDetails orderDetailsDelete = orderDetails.get();
        BigDecimal minusCost = orderDetailsDelete.getProduct().getPrice().multiply(new BigDecimal(orderDetailsDelete.getQuantity()));
        orderUpdate.setTotalCost(orderUpdate.getTotalCost().subtract(minusCost));
        orderRepository.save(orderUpdate);
        orderDetailsRepository.deleteById(idOrderDetails);
    }

}

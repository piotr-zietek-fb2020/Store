package com.fb2020.Store.controller;

import com.fb2020.Store.entity.Address;
import com.fb2020.Store.exception.address.AddressException;
import com.fb2020.Store.service.AddressService;
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
public class AddressController {
    @Autowired
    AddressService addressService;

   /* @GetMapping("/addresses")
    public List<Address> getAll() {
        return addressService.getAll();
    }*/

    @GetMapping("/addresses/{id}")
    public Optional<Address> getAddressById(@PathVariable("id") Long id) {
        try {
            return addressService.getAddressById(id);
        } catch (AddressException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/addresses")
    public List<Address> getAllByCountry(@RequestParam(required = false, value = "country")  String country) {
        if (country == null) {
            return addressService.getAll();
        }
        return addressService.getAllByCountry(country);
    }

 /*   @PostMapping("/addresses")
    public ResponseEntity<Void> createAddress(@RequestBody Address address, UriComponentsBuilder builder) {
        address.setId(null);
        addressService.createAddress(address);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/addresses/{id}").buildAndExpand(address.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }*/

    @DeleteMapping("/addresses/{id}")
    public void deleteAddressById(@PathVariable("id") Long id) {
        try {
            addressService.deleteAddressById(id);
        } catch (AddressException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/addresses/{id}")
    public Address putAddress(@PathVariable("id") Long id, @RequestBody Address address) {
        try {
            return addressService.putAddress(id, address);
        } catch (AddressException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}

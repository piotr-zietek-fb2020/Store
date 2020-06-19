package com.fb2020.Store.service;

import com.fb2020.Store.entity.Address;
import com.fb2020.Store.exception.address.AddressException;
import com.fb2020.Store.exception.address.AddressNotFoundException;
import com.fb2020.Store.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    public Optional<Address> getAddressById(Long id) throws AddressException {
        Optional<Address> address = addressRepository.findById(id);
        if(!address.isPresent()) {
            throw new AddressNotFoundException(id);
        }
        return address;
    }

    public List<Address> getAllByCountry(String country) {
        return addressRepository.findAllByCountry(country);
    }

    public Address createAddress(Address address) {
       return addressRepository.save(address);
    }

    public void deleteAddressById(Long id) throws AddressException {
        Optional<Address> address = addressRepository.findById(id);
        if(!address.isPresent()) {
            throw new AddressNotFoundException(id);
        }
        addressRepository.deleteById(id);
    }

    public Address putAddress(Long id, Address address) throws AddressException {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if(!addressOptional.isPresent()) {
            throw new AddressNotFoundException(id);
        }
        address.setId(id);
        return addressRepository.save(address);
    }
}

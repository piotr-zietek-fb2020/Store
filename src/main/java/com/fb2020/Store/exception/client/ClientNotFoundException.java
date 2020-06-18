package com.fb2020.Store.exception.client;

import com.fb2020.Store.exception.client.ClientException;

public class ClientNotFoundException extends ClientException {
    public ClientNotFoundException(Long id) {
        super("No client in database with id: " + id);
    }
}

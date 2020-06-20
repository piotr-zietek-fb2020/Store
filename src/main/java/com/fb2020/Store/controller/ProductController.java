package com.fb2020.Store.controller;

import com.fb2020.Store.entity.Product;
import com.fb2020.Store.exception.product.ProductException;
import com.fb2020.Store.service.ProductService;
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
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/products/{id}")
    public Optional<Product> getProductById(@PathVariable("id") Long id) {
        try {
            return productService.getProductById(id);
        } catch (ProductException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Void>createProduct(@RequestBody Product product, UriComponentsBuilder builder) {
        product.setId(null);
        productService.createProduct(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/products/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable("id") Long id) {
        try {
            productService.deleteProductById(id);
        } catch (ProductException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/products/{id}")
    public Product putProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        try {
            return productService.putProduct(id, product);
        } catch (ProductException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}

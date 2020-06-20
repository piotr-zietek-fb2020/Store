package com.fb2020.Store.service;

import com.fb2020.Store.entity.Product;
import com.fb2020.Store.exception.product.ProductException;
import com.fb2020.Store.exception.product.ProductNotFoundException;
import com.fb2020.Store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) throws ProductException {
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }

    public Product createProduct(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) throws ProductException {
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    public Product putProduct(Long id, Product product) throws ProductException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(!productOptional.isPresent()) {
            throw new ProductNotFoundException(id);
        }
        product.setId(id);
        return productRepository.save(product);
    }
}

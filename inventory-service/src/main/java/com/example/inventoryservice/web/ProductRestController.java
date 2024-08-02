package com.example.inventoryservice.web;

import com.example.inventoryservice.entities.Product;
import com.example.inventoryservice.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductRestController {

private ProductRepository productRepository;

    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping()
    //@PreAuthorize("hasAuthority('admin')")
    public List<Product> productList(){
        return productRepository.findAll();
    }
}


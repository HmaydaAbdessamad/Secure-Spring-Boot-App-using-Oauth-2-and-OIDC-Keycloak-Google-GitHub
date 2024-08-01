package com.example.inventoryservice;

import com.example.inventoryservice.entities.Product;
import com.example.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(ProductRepository productRepository){
		return args->{
			List<Product> productList =List.of(
					Product.builder().id(String.valueOf(UUID.randomUUID())).name("laptob").price(34d).quantity(3).build(),
					Product.builder().id(String.valueOf(UUID.randomUUID())).name("phone").price(2500d).quantity(53).build(),
					Product.builder().id(String.valueOf(UUID.randomUUID())).name("printer").price(34d).quantity(3).build());
			productRepository.saveAll(productList);
		};
	}

}

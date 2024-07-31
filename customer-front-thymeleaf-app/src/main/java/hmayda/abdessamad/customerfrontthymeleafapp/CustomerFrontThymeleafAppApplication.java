package hmayda.abdessamad.customerfrontthymeleafapp;

import hmayda.abdessamad.customerfrontthymeleafapp.entities.Customer;
import hmayda.abdessamad.customerfrontthymeleafapp.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CustomerFrontThymeleafAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerFrontThymeleafAppApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
		return args -> {
			List<Customer> customerList=List.of(
					Customer.builder().name("Abdessamad").email("abdoessamadhmayda@gmail.com").build(),
					Customer.builder().name("Hafida").email("hafida@gmail.com").build(),
					Customer.builder().name("Hamza").email("Hamza@gmail.com").build()
			);
			customerRepository.saveAll(customerList);
		} ;
	}
}

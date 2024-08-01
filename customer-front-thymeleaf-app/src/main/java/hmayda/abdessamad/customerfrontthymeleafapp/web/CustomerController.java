package hmayda.abdessamad.customerfrontthymeleafapp.web;


import hmayda.abdessamad.customerfrontthymeleafapp.entities.Customer;
import hmayda.abdessamad.customerfrontthymeleafapp.repository.CustomerRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('admin')")
    public String customers(Model model){
        List<Customer> customerList=customerRepository.findAll();
        model.addAttribute(customerList);
        return "customers";
    }

    @GetMapping("/products")
    public String products(Model model){
        return "products";
    }

    @GetMapping("/auth")
    @ResponseBody
    Authentication authentication(Authentication authentication){
        return authentication;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }
}

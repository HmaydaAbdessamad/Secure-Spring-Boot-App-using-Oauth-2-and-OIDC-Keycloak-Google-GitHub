package hmayda.abdessamad.customerfrontthymeleafapp.web;


import hmayda.abdessamad.customerfrontthymeleafapp.entities.Customer;
import hmayda.abdessamad.customerfrontthymeleafapp.repository.CustomerRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    private CustomerRepository customerRepository;
    private ClientRegistrationRepository clientRegistrationRepository;
    public CustomerController(CustomerRepository customerRepository,ClientRegistrationRepository clientRegistrationRepository) {
        this.customerRepository = customerRepository;
        this.clientRegistrationRepository=clientRegistrationRepository;
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

    @GetMapping("/notAuthorized")
    public String notAuthorized(Model model){
            return "notAuthorized";
    }


    @GetMapping("/oauth2login")
    public String oauth2login(Model model){

        String authorizationRequestBaseUri = "oauth2/authorization";
        Map<String, String> oauth2AuthenticationUrls = new HashMap();
        Iterable<ClientRegistration> clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;;
        clientRegistrations.forEach(registration ->{
            oauth2AuthenticationUrls.put(registration.getClientName(),
                    authorizationRequestBaseUri + "/" + registration.getRegistrationId());
        });
        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "oauth2login";
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

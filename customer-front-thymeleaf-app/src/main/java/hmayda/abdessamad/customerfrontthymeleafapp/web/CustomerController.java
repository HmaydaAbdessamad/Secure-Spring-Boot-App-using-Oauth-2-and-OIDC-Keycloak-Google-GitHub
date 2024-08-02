package hmayda.abdessamad.customerfrontthymeleafapp.web;


import hmayda.abdessamad.customerfrontthymeleafapp.entities.Customer;
import hmayda.abdessamad.customerfrontthymeleafapp.model.Product;
import hmayda.abdessamad.customerfrontthymeleafapp.repository.CustomerRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

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
    @PreAuthorize("hasAuthority('admin')")
    public String products(Model model) {
        //calling spring security to get the context ?
        SecurityContext context = SecurityContextHolder.getContext();
        // extracting the authentication object
        Authentication authentication = context.getAuthentication();

        OAuth2AuthenticationToken oAuth2AuthenticationToken= (OAuth2AuthenticationToken) authentication;
        DefaultOidcUser oidcUser = (DefaultOidcUser) oAuth2AuthenticationToken.getPrincipal();
        String jwtTokenValue=oidcUser.getIdToken().getTokenValue();
        RestClient restClient = RestClient.create("http://localhost:8082");
        List<Product> products = restClient.get()
                .uri("/products")
                .headers(httpHeaders -> httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer "+jwtTokenValue))
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});

        model.addAttribute("products",products);
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

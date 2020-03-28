package rbn.spring.cloud.client.customer.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import rbn.spring.cloud.client.customer.model.Customer;
import rbn.spring.cloud.client.customer.model.Product;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getById(@PathVariable("id") String id) {

	ResponseEntity<Product> responseEntity = new RestTemplate().exchange(//
		"http://localhost:8085/product/" + id, //
		HttpMethod.GET, //
		new HttpEntity<>(new HttpHeaders()), //
		Product.class);

	return new Customer(id, responseEntity.getBody());
    }

}

package rbn.spring.cloud.client.product.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rbn.spring.cloud.client.product.model.Product;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getById(@PathVariable("id") String id) {
	return new Product(id, "aaa");
    }

}

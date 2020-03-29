package rbn.spring.cloud.client.product.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rbn.spring.cloud.client.product.model.Product;
import rbn.spring.cloud.client.product.service.AnyService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private AnyService anyService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getById(@PathVariable("id") String id) {
	anyService.doAnyThing();
	return new Product(id, "aaa");
    }

}

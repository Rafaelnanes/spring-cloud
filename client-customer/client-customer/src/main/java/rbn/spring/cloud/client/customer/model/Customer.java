package rbn.spring.cloud.client.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {

    private String id;

    private Product product;

}

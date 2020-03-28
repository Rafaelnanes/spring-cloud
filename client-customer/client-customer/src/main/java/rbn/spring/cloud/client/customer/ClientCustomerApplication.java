package rbn.spring.cloud.client.customer;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientCustomerApplication {

    public static void main(String[] args) throws IOException {
	SpringApplication.run(ClientCustomerApplication.class, args);
    }

}

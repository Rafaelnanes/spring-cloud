package rbn.spring.cloud.client.product;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientProductApplication {

    public static void main(String[] args) {
	new SpringApplicationBuilder(ClientProductApplication.class).run(args);
    }

}
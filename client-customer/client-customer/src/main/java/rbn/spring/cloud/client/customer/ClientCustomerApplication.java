package rbn.spring.cloud.client.customer;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientCustomerApplication {

    public static void main(String[] args) throws IOException {
	ConfigurableApplicationContext context = SpringApplication.run(ClientCustomerApplication.class, args);
	System.out.println("Hit enter to terminate");
	System.in.read();
	context.close();
    }

}

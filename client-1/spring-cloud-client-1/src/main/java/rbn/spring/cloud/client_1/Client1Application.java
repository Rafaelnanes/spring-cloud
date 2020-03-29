package rbn.spring.cloud.client_1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class Client1Application {

    public static void main(String[] args) {
	SpringApplication.run(Client1Application.class, args);
    }

}

@RefreshScope
@RestController
@RequestMapping("/client1")
class ServiceInstanceRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceInstanceRestController.class);

    @Value("${my-value:any}")
    private String myValue;

    @Autowired
    private Environment env;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
	return this.discoveryClient.getInstances(applicationName);
    }

    @GetMapping("/any")
    public String status() {
	LOG.info("#### Reached Endpoint here ####");
	return "Working on port " + env.getProperty("server.port");
    }

    @GetMapping(value = "/my-value", produces = MediaType.TEXT_PLAIN_VALUE)
    public String myValue() {
	return String.format("Here is my value: %s", myValue);
    }

}
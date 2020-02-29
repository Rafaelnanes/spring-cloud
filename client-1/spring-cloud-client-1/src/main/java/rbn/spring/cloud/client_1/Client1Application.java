package rbn.spring.cloud.client_1;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class Client1Application {

    private static final Logger LOG = LoggerFactory.getLogger(Client1Application.class);

    @Value("${my-value}")
    private String myValue;

    public static void main(String[] args) {
	SpringApplication.run(Client1Application.class, args);
    }

    @PostConstruct
    public void init() {
	LOG.info("## My value loaded from config-service, {}", myValue);
    }
}

@RestController
class ServiceInstanceRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceInstanceRestController.class);

    @Autowired
    private Environment env;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
	return this.discoveryClient.getInstances(applicationName);
    }

    @GetMapping("/client1/any")
    public String status() {
	LOG.info("#### Reached Endpoint here ####");
	return "Working on port " + env.getProperty("local.server.port");
    }
}
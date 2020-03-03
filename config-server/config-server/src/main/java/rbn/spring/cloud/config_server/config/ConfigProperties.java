package rbn.spring.cloud.config_server.config;

import org.springframework.cloud.bus.endpoint.RefreshBusEndpoint;
import org.springframework.cloud.config.server.environment.NativeEnvironmentProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import rbn.spring.cloud.config_server.entity.PropertyRepository;

@Configuration
public class ConfigProperties {

    @Bean
    public MyRespositoryProperties getBeanNay(ConfigurableEnvironment environment,
	    NativeEnvironmentProperties properties, PropertyRepository propertyRepository,
	    RefreshBusEndpoint refreshBusEndpoint) {
	return new MyRespositoryProperties(environment, properties, propertyRepository, refreshBusEndpoint);
    }

}

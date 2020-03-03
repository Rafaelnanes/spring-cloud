package rbn.spring.cloud.config_server.scheduler;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.endpoint.RefreshBusEndpoint;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rbn.spring.cloud.config_server.config.MyRespositoryProperties;
import rbn.spring.cloud.config_server.entity.Property;
import rbn.spring.cloud.config_server.entity.PropertyRepository;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerConfig.class);

    @Autowired
    private RefreshBusEndpoint refreshBusEndpoint;

    @Autowired
    private MyRespositoryProperties myRespositoryProperties;

    @Autowired
    private PropertyRepository propertyRepository;

    @Scheduled(fixedRate = 20000)
    public void scheduleFixedRateTask() {
	LOG.info("#### SCHEDULER RUNNING ####");
	Environment environment = myRespositoryProperties.findOnePure("client1", "local", null, false);

	for (PropertySource propertySource : environment.getPropertySources()) {

	    try {
		String value = new ObjectMapper().writeValueAsString(propertySource.getSource());
		Optional<Property> optional = propertyRepository.findById("myConfigServer");
		if (optional.isPresent()) {
		    Property property = optional.get();
		    if (!property.getValue().equals(value)) {

			LOG.info("#### SCHEDULER REFRESHING ####");
			refreshBusEndpoint.busRefresh();
			return;
		    }
		}
	    } catch (JsonProcessingException e) {
		e.printStackTrace();
	    }

	}
    }

}

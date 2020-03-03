package rbn.spring.cloud.config_server.config;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bus.endpoint.RefreshBusEndpoint;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.NativeEnvironmentProperties;
import org.springframework.cloud.config.server.environment.NativeEnvironmentRepository;
import org.springframework.core.env.ConfigurableEnvironment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rbn.spring.cloud.config_server.entity.Property;
import rbn.spring.cloud.config_server.entity.PropertyRepository;

public class MyRespositoryProperties extends NativeEnvironmentRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MyRespositoryProperties.class);

    private PropertyRepository propertyRepository;
    private RefreshBusEndpoint refreshBusEndpoint;
    private final String ID = "myConfigServer";

    public MyRespositoryProperties(ConfigurableEnvironment environment, NativeEnvironmentProperties properties,
	    PropertyRepository propertyRepository, RefreshBusEndpoint refreshBusEndpoint) {
	super(environment, properties);
	this.propertyRepository = propertyRepository;
	this.refreshBusEndpoint = refreshBusEndpoint;
    }

    @Override
    public Environment findOne(String config, String profile, String label) {
	Environment environment = super.findOne(config, profile, label);
	return getLatest(config, profile, label, environment);
    }

    @Override
    public Environment findOne(String config, String profile, String label, boolean includeOrigin) {
	Environment environment = super.findOne(config, profile, label, includeOrigin);
	return getLatest(config, profile, label, environment);
    }

    public Environment findOnePure(String config, String profile, String label, boolean includeOrigin) {
	return super.findOne(config, profile, label, includeOrigin);
    }

    private Environment getLatest(String config, String profile, String label, Environment environment) {
	LOG.info("######### LOADING ######");

	Optional<Property> optional = propertyRepository.findById(ID);
	if (optional.isPresent()) {
	    Property property = optional.get();
	    String value = property.getValue();
	    List<PropertySource> propertySources = environment.getPropertySources();
	    propertySources.clear();

	    Map<?, ?> map = null;
	    try {
		map = new ObjectMapper().readValue(value, Map.class);
	    } catch (JsonProcessingException e) {
		e.printStackTrace();
	    }

	    Environment finalEnv = new Environment(environment);
	    PropertySource propertySource = new PropertySource(property.getName(), map);
	    propertySources.add(propertySource);
	    finalEnv.add(propertySource);
	    return finalEnv;

	} else {
	    saveProperties(environment, config, profile, label);
	    return environment;
	}
    }

    private void saveProperties(Environment environment, String config, String profile, String label) {
	Property property = new Property();
	for (PropertySource propertySource : environment.getPropertySources()) {
	    try {
		String value = new ObjectMapper().writeValueAsString(propertySource.getSource());
		property.setApplication(ID);
		property.setName(propertySource.getName());
		property.setValue(value);
	    } catch (JsonProcessingException e) {
		e.printStackTrace();
	    }
	}

	property.setConfig(config);
	property.setProfile(profile);
	property.setLabel(label);

	propertyRepository.save(property);

    }

}

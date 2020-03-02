package rbn.spring.cloud.config_server.config;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.environment.NativeEnvironmentProperties;
import org.springframework.cloud.config.server.environment.NativeEnvironmentRepository;
import org.springframework.core.env.ConfigurableEnvironment;

import rbn.spring.cloud.config_server.entity.Property;
import rbn.spring.cloud.config_server.entity.PropertyRepository;

public class MyRespositoryProperties extends NativeEnvironmentRepository {

    private PropertyRepository propertyRepository;

    public MyRespositoryProperties(ConfigurableEnvironment environment, NativeEnvironmentProperties properties,
	    PropertyRepository propertyRepository) {
	super(environment, properties);
	this.propertyRepository = propertyRepository;
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

    private Environment getLatest(String config, String profile, String label, Environment environment) {
	String id = "myConfigServer";
	Optional<Property> optional = propertyRepository.findById(id);
	if (optional.isPresent()) {
	    optional.get().getValues();
	} else {
	    Property property = new Property();
	    property.setApplication(id);
	    property.setHash("aa");
	    property.setPodName("podName");
	    List<String> list = environment.getPropertySources().stream().map(x -> x.getSource().toString())
		    .collect(Collectors.toList());
	    property.setValues(String.join(",", list));
	    propertyRepository.save(property);
	}
	return environment;
    }

}

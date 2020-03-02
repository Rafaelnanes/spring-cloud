package rbn.spring.cloud.config_server.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Property")
public class Property {

    @Id
    private String application;

    private String podName;

    private String values;

    private String hash;

    public String getApplication() {
	return application;
    }

    public void setApplication(String application) {
	this.application = application;
    }

    public String getPodName() {
	return podName;
    }

    public void setPodName(String podName) {
	this.podName = podName;
    }

    public String getValues() {
	return values;
    }

    public void setValues(String values) {
	this.values = values;
    }

    public String getHash() {
	return hash;
    }

    public void setHash(String hash) {
	this.hash = hash;
    }

}

package rbn.spring.cloud.client.consumer.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;
import rbn.spring.cloud.client.customer.ClientCustomerApplication;
import rbn.spring.cloud.client.customer.web.CustomerController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientCustomerApplication.class)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "rbn.spring-cloud:client-product:+:stubs:8085")
public class ControllerTest {

    @Autowired
    private CustomerController customerController;

    @Before
    public void setup() {
	StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(customerController);
	RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }

    @Test
    public void customerById() throws Exception {

	MockMvcRequestSpecification request = RestAssuredMockMvc.given();

	// when:
	ResponseOptions response = RestAssuredMockMvc.given().spec(request).get("/customer/1");

	// then:
	Assert.assertEquals(200, response.statusCode());
	// and:
	DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
	Assert.assertNotNull(parsedJson);
    }

}

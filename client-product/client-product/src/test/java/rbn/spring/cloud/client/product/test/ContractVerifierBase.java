package rbn.spring.cloud.client.product.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import io.restassured.response.ResponseOptions;
import rbn.spring.cloud.client.product.web.ProductController;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
public class ContractVerifierBase {

    @Autowired
    private ProductController productController;

    @Before
    public void setup() {
	StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(productController);
	RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }

//    @Before
//    public void setup() {
//	RestAssuredMockMvc.standaloneSetup(new ProductController());
//    }

    @Test
    public void productById() throws Exception {
	// given:
	MockMvcRequestSpecification request = RestAssuredMockMvc.given();

	// when:
	ResponseOptions response = RestAssuredMockMvc.given().spec(request).get("/product/1");

	// then:
	Assert.assertEquals(200, response.statusCode());
	// and:
	DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
	Assert.assertNotNull(parsedJson);
//	((parsedJson).field("['fraudCheckStatus']").matches("[A-Z]{5}");
//	assertThatJson(parsedJson).field("['rejection.reason']").isEqualTo("Amount too high");
    }

}

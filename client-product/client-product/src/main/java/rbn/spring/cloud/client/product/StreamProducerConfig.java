package rbn.spring.cloud.client.product;

import java.util.Random;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import lombok.extern.log4j.Log4j2;
import rbn.spring.cloud.client.product.model.Product;

@Log4j2
@EnableBinding(Source.class)
@Configuration
public class StreamProducerConfig {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static final String[] data = new String[] { "abc1", "def1", "qux1", "abc2", "def2", "qux2", "abc3", "def3",
	    "qux3", "abc4", "def4", "qux4", };

    @InboundChannelAdapter(channel = Source.OUTPUT, poller = @Poller(fixedRate = "5000"))
    public Message<?> generate() {
	String value = data[RANDOM.nextInt(data.length)];
	log.info("Sending: {}", value);
	return MessageBuilder.withPayload(new Product(value)).setHeader("rbnKey", value).build();
    }
}

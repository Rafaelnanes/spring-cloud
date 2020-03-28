package rbn.spring.cloud.client.product.mycustom;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableBinding(MyProcessor.class)
@Configuration
@EnableScheduling
public class MyCustomStreamProducerConfig {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static final String[] data = new String[] { "Overwatch", "World Of Warcraft", "Hearthstone" };

    @Autowired
    private MyProcessor myProcessor;

    @StreamListener(MyProcessor.INPUT)
    public void outputToQueue(String value) {
	log.info("Consuming value from queue: {}", value);
    }

    @Scheduled(fixedRate = 15000)
    public void inputToQueue() {
	String value = data[RANDOM.nextInt(data.length)];
	log.info("Producing: {}", value);
	Message<String> message = MessageBuilder.withPayload(value).build();
	myProcessor.input().send(message);
    }
}

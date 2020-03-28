package rbn.spring.cloud.client.product.mycustom;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MyProcessor {

//    String OUTPUT = "rbnOutput";

    String INPUT = "rbnInput";

//    @Output(OUTPUT)
//    MessageChannel output();

    @Input(INPUT)
    SubscribableChannel input();

}

package matano.dos.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication{

    public static void main(String[] args){
        Logger logger = LoggerFactory.getLogger(ClientApplication.class);
        logger.debug("Client application is starting");
        SpringApplication.run(ClientApplication.class, args);
        logger.debug("Client application started");
    }

}

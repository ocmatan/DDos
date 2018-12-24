package matano.dos.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    private static Logger logger = LoggerFactory.getLogger(ServerApplication.class);


    public static void main(String[] args) {
        logger.info("Server application is starting");
        SpringApplication.run(ServerApplication.class, args);
        logger.info("Server application has started");
    }
}

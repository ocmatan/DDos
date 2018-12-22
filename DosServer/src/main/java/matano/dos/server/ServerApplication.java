package matano.dos.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        System.out.println("Server application is starting");
        SpringApplication.run(ServerApplication.class, args);
        System.out.println("Server application has started");
    }
}

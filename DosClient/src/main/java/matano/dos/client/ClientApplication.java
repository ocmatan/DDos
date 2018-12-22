package matano.dos.client;

import matano.dos.client.client.WorkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication  implements CommandLineRunner {

    public static void main(String[] args){
        System.out.println("Client application is starting");
        SpringApplication.run(ClientApplication.class, args);
        System.out.println("Client application has started");
    }

    @Autowired
    WorkManager workManager;

    @Override
    public void run(String... args) throws Exception {
        if(args != null && args.length > 0){
            workManager.execute(Integer.valueOf(args[0]));
        }


    }
}

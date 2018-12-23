package matano.dos.client.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ShutdownService {

    @Autowired
    private WorkManager workManager;

    @Autowired
    private ApplicationContext appContext;

    Scanner scanner = new Scanner(System.in);

    Logger logger = LoggerFactory.getLogger(ShutdownService.class);

    @PostConstruct
    public void init(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            waitForUserSignal();
        });
    }

    public void waitForUserSignal(){
        logger.info("Key press recieved - application is shutting down" );
        String input = scanner.next();
        shutdownApplication();
    }

    public void shutdownApplication(){
        workManager.shutdown();
        int exitCode = SpringApplication.exit(appContext, ()-> 0);
        logger.info("Exit application");
        System.exit(exitCode);
    }

}

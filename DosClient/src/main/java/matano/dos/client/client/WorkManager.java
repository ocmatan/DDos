package matano.dos.client.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WorkManager  implements CommandLineRunner {

    @Value("${server.url}")
    private String serverUrl;

    @Value("${app.worker-idle-time-max-millis}")
    private int workerIdleTimeMaxMillis;

    private ExecutorService executor;

    Logger logger = LoggerFactory.getLogger(ClientWorker.class);

    @Override
    public void run(String... args){

        if(args == null || args.length == 0){
            logger.error("Usage: missing CLI argument" );
            return;
        }
        int numberOfClients = Integer.valueOf(args[0]);
        executor = Executors.newFixedThreadPool(numberOfClients);
        for(int i=0; i<numberOfClients; i++){
            ClientWorker clientWorker = new ClientWorker(i, serverUrl, workerIdleTimeMaxMillis);
            executor.execute(clientWorker);
        }
        logger.debug("Work manager done with setup and init" );

    }

    public void shutdown(){
        executor.shutdownNow();
    }

}

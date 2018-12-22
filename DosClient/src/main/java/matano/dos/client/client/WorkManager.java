package matano.dos.client.client;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class WorkManager {

    public void execute(int numberOfClients){
        ExecutorService executor = Executors.newFixedThreadPool(numberOfClients);
        for(int i=0; i<numberOfClients; i++){
            ClientWorker clientWorker = new ClientWorker(i, "http://localhost:8080/");
            executor.execute(clientWorker);
        }


    }

}

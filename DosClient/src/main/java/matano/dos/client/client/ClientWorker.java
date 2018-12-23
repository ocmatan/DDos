package matano.dos.client.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

public class ClientWorker implements Runnable{

    Logger logger = LoggerFactory.getLogger(ClientWorker.class);
    private int id;
    private RestTemplate restTemplate = new RestTemplate();
    private String url;
    private int idleTimeMaxMillis;

    public ClientWorker(int id, String url, int idleTimeMaxMillis) {
        this.id = id;
        this.url = url;
        this.idleTimeMaxMillis = idleTimeMaxMillis;
    }

    @Override
    public void run(){
        logger.debug("running with id: " + id);
        while(!Thread.currentThread().isInterrupted()){
            try{
                ResponseEntity response = restTemplate.getForEntity(url+ "?clientId=" +id, String.class);
                logger.debug("Response from server: " + response);
            }
            catch (HttpStatusCodeException e){
                logger.debug("Http Status Exception in client worker, status: " + e.getStatusCode()+", " + e.getResponseBodyAsString());
            }
            catch (Exception e){
                logger.warn("Exception in client worker: " + e);
            }
            finally {
                try {
                    Thread.sleep(new Random().nextInt(idleTimeMaxMillis));
                }catch (InterruptedException e){
                    logger.debug("InterruptedException in client worker: " + e);
                }
            }
        }
    }

}

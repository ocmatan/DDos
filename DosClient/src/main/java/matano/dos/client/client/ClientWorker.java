package matano.dos.client.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ClientWorker implements Runnable{

    private int id;
    private RestTemplate restTemplate = new RestTemplate();
    private String url;

    public ClientWorker(int id, String url) {
        this.id = id;
        this.url = url;
    }

    @Override
    public void run(){
        while(true){
            try{
                ResponseEntity response = restTemplate.getForEntity(url+ "?clientId=" +id, String.class);
                System.out.println("response: " + response);
                Thread.sleep(2000);
            }catch (Exception e){
                System.out.println("Exception: " + e);
            }
        }
    }

}

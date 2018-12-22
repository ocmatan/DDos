package matano.dos.server.service;

import org.springframework.stereotype.Service;

@Service
public class DosService {

    public Object validateRequest(int clientId){
        System.out.println("incoming request: " + clientId);
        return null;
    }
}

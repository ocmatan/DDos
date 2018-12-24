package matano.dos.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DDosService {

    @Autowired
    UserTimeFrameRepository userEntryRepository;

    public ResponseEntity<String> handleRequest(int clientId, LocalDateTime timestamp){
        System.out.println("incoming request: " + clientId);
        StatusEnum resultStatus = userEntryRepository.insert(clientId, timestamp);
        ResponseEntity<String> result = null;
        switch(resultStatus){
            case OK:
                result = new ResponseEntity<String>(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK);
                break;
            case TRESHOLD_EXCEEDED:
                result = new ResponseEntity<String>(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),HttpStatus.SERVICE_UNAVAILABLE);
                break;
        }
        return result;
    }
}

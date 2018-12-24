package matano.dos.server.service;

import matano.dos.server.web.ApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DDosService {

    private Logger logger = LoggerFactory.getLogger(DDosService.class);

    @Autowired
    private UserTimeFrameRepository userEntryRepository;

    public ResponseEntity<String> handleRequest(int clientId, LocalDateTime timestamp){
        logger.debug("Start handleRequest for clientId " + " and ts=" + timestamp);
        StatusEnum resultStatus = userEntryRepository.insert(clientId, timestamp);
        logger.debug("repository insert result: " + resultStatus);
        ResponseEntity<String> result = null;
        switch(resultStatus){
            case OK:
                result = new ResponseEntity<String>(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK);
                break;
            case TRESHOLD_EXCEEDED:
                result = new ResponseEntity<String>(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),HttpStatus.SERVICE_UNAVAILABLE);
                break;
        }
        logger.debug("request handle is done, result:  " + result);
        return result;
    }
}

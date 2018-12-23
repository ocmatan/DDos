package matano.dos.server.web;

import matano.dos.server.service.DDosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ApiController {

    @Autowired
    DDosService dDosService;

    @RequestMapping(method =  RequestMethod.GET, value = "/")
    public ResponseEntity<String> interceptRequest(@RequestParam int clientId){
        LocalDateTime now = LocalDateTime.now();
        return dDosService.handleRequest(clientId, now);
    }
}

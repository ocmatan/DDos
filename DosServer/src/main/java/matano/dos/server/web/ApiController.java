package matano.dos.server.web;

import matano.dos.server.service.DosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    DosService dosService;

    @RequestMapping(method =  RequestMethod.GET, value = "/")
    public String interceptRequest(@RequestParam int clientId){
        dosService.validateRequest(clientId);
        return "hello from server";
    }
}

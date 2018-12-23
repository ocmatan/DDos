package matano.dos.server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserTimeFrameRepository {

    private Map<Integer, TimeFrame> map = new ConcurrentHashMap<>();

    @Value("${app.timeFrameTreshold}")
    private int timeFrameTreshold;
    @Value("${app.timeFrameLengthInSeconds}")
    int timeFrameLengthInSeconds;


    public StatusEnum insert(int clientId, LocalDateTime timestamp){
        TimeFrame currentTimeFrame = map.get(clientId);
        if( currentTimeFrame == null || (!currentTimeFrame.isInTimeFrame(timestamp)) ){
            currentTimeFrame = new TimeFrame(timestamp, timestamp.plusSeconds(timeFrameLengthInSeconds));
        }
        if(currentTimeFrame.getRequestCounter() >=  timeFrameTreshold){
            return StatusEnum.TRESHOLD_EXCEEDED;
        }else {
            currentTimeFrame.setRequestCounter(currentTimeFrame.getRequestCounter() + 1);
            map.put(clientId, currentTimeFrame);
            return StatusEnum.OK;
        }
    }


}

package matano.dos.server.service;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserEntryRepository {

    Map<Integer, TimeFrame> map = new ConcurrentHashMap<>();

    int timeFrameTreshold = 5;
    int timeFrameLengthInSeconds = 5;


    public StatusEnum insert(int clientId, LocalDateTime timestamp){
        TimeFrame timeFrame = getTimeFrame(clientId, timestamp);
        if(isRequestValidForTimeFrame(timeFrame)) {
            timeFrame.setRequestCounter(timeFrame.getRequestCounter() + 1);
            return StatusEnum.OK;
        }
        return StatusEnum.TRESHOLD_EXCEEDED;
    }

    private TimeFrame getTimeFrame(int clientId, LocalDateTime timestamp){
        TimeFrame currentTimeFrame = map.get(clientId);
        if( currentTimeFrame == null || (!currentTimeFrame.isInTimeFrame(timestamp)) ){
            currentTimeFrame = new TimeFrame(timestamp, timestamp.plusSeconds(timeFrameLengthInSeconds));
        }
        return currentTimeFrame;
    }

    private boolean isRequestValidForTimeFrame(TimeFrame timeFrame){
        if(timeFrame.getRequestCounter() >=  timeFrameTreshold){
            return false;
        }
        return true;
    }

}

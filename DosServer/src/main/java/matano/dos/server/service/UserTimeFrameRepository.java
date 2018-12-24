package matano.dos.server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserTimeFrameRepository {

    private Map<Integer, MapEntry> map = new ConcurrentHashMap<>();

    @Value("${app.timeFrameTreshold}")
    private int timeFrameTreshold;
    @Value("${app.timeFrameLengthInSeconds}")
    int timeFrameLengthInSeconds;


    public StatusEnum insert(int clientId, LocalDateTime timestamp){
        MapEntry mapEntry = map.get(clientId);
        try{
            if(mapEntry == null){
                mapEntry = new MapEntry();
            }

            mapEntry.getLock().lock();

            if(map.get(clientId) == null){
                map.put(clientId, mapEntry);
            }

            TimeFrame currentTimeFrame = mapEntry.getTimeFrame();
            if( currentTimeFrame == null || (!currentTimeFrame.isInTimeFrame(timestamp)) ){
                currentTimeFrame = new TimeFrame(timestamp, timestamp.plusSeconds(timeFrameLengthInSeconds));
            }
            if(currentTimeFrame.getRequestCounter() >=  timeFrameTreshold){
                return StatusEnum.TRESHOLD_EXCEEDED;
            }else {
                currentTimeFrame.setRequestCounter(currentTimeFrame.getRequestCounter() + 1);
                mapEntry.setTimeFrame(currentTimeFrame);
                return StatusEnum.OK;
            }
        }finally {
            if(mapEntry != null){
                mapEntry.getLock().unlock();
            }
        }

    }

}

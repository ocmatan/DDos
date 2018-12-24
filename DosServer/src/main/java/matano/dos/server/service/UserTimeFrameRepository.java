package matano.dos.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public class UserTimeFrameRepository {

    Logger logger = LoggerFactory.getLogger(UserTimeFrameRepository.class);

    private Map<Integer, MapEntry> map = new ConcurrentHashMap<>();

    private Lock newRecordLock = new ReentrantLock();

    @Value("${app.timeFrameTreshold}")
    private int timeFrameTreshold;
    @Value("${app.timeFrameLengthInSeconds}")
    int timeFrameLengthInSeconds;


    public StatusEnum insert(int clientId, LocalDateTime timestamp){
        logger.debug("Insert: "+ clientId + ", " + timestamp);
        MapEntry mapEntry = map.get(clientId);
        try{
            if(mapEntry == null){
                try{
                    newRecordLock.lock();
                    mapEntry = new MapEntry();
                    if(map.get(clientId) == null){
                        map.put(clientId, mapEntry);
                    }
                }finally {
                    newRecordLock.unlock();
                }
            }

            mapEntry.getLock().lock();

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

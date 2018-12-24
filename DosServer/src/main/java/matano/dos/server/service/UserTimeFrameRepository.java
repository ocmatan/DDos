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

    private Logger logger = LoggerFactory.getLogger(UserTimeFrameRepository.class);

    private Map<Integer, MapEntry> map = new ConcurrentHashMap<>();

    private Lock newRecordLock = new ReentrantLock();

    @Value("${app.timeFrameThreshold}")
    private int timeFrameThreshold;
    @Value("${app.timeFrameLengthInSeconds}")
    private int timeFrameLengthInSeconds;


    public StatusEnum insert(int clientId, LocalDateTime timestamp){
        logger.debug("Insert: "+ clientId + ", " + timestamp);
        MapEntry mapEntry = map.get(clientId);
        try {
            if (mapEntry == null) {
                mapEntry = initMapEntry(clientId);
            }
            mapEntry.getLock().lock();
            return updateTimeFrame(timestamp, mapEntry);
        }
        catch(Exception e){
            logger.error("Exception in insert: " + e);
            throw e;
        }finally {
            if(mapEntry != null){
                mapEntry.getLock().unlock();
            }
        }
    }

    private StatusEnum updateTimeFrame(LocalDateTime timestamp, MapEntry mapEntry){
        TimeFrame currentTimeFrame = mapEntry.getTimeFrame();
        if( currentTimeFrame == null || (!currentTimeFrame.isInTimeFrame(timestamp)) ){
            currentTimeFrame = new TimeFrame(timestamp, timestamp.plusSeconds(timeFrameLengthInSeconds));
        }
        if(currentTimeFrame.isThresholdExceeded(timeFrameThreshold)){
            return StatusEnum.TRESHOLD_EXCEEDED;
        }else {
            currentTimeFrame.incrementCounter();
            mapEntry.setTimeFrame(currentTimeFrame);
            return StatusEnum.OK;
        }
    }

    private MapEntry initMapEntry(int clientId){
        logger.debug("Initializing mao entry for: " + clientId);
        try{
            newRecordLock.lock();
            if(map.get(clientId) == null){
                MapEntry mapEntry = new MapEntry();
                map.put(clientId, mapEntry);
            }
            return map.get(clientId);
        }
        catch (Exception e){
            logger.error("Error while trying to init map entry:" + e);
        }
        finally {
            newRecordLock.unlock();
        }
        return map.get(clientId);
    }

}

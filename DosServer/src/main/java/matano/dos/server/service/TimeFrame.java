package matano.dos.server.service;

import java.time.LocalDateTime;

public class TimeFrame {

    private int requestCounter = 0;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeFrame(LocalDateTime startTime, LocalDateTime endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isInTimeFrame(LocalDateTime timestamp){
        if(     (timestamp.isAfter(startTime) || timestamp.isEqual(startTime))
             && (timestamp.isBefore(endTime) || timestamp.isEqual(endTime))  ){
            return true;
        }
        return false;
    }

    public int getRequestCounter() {
        return requestCounter;
    }

    public void setRequestCounter(int newCount){
        this.requestCounter = newCount;
    }
}

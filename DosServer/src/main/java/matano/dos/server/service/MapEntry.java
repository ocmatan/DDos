package matano.dos.server.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MapEntry {

    private TimeFrame timeFrame;
    private Lock lock = new ReentrantLock();

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}

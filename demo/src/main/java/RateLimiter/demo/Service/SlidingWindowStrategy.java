package RateLimiter.demo.Service;

import RateLimiter.demo.Model.SlidingWindow;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SlidingWindowStrategy implements RateLimitStrategy {
    private ConcurrentHashMap<String, Deque<SlidingWindow>> hm=new ConcurrentHashMap<>();
    private long windowSize=2000;//i.e 2s
    private int allowedRequests=10;

    @Override
    public boolean isAllowed(String s) {
        long current_time=System.currentTimeMillis();

        setWindow(s);

        Deque<SlidingWindow> dq=hm.get(s);
        synchronized (dq)
        {
            dq.add(new SlidingWindow(current_time));
            deleteExpired(dq,current_time);
            if(dq.size()<=allowedRequests)
                return true;
            return false;
        }
    }

    private void deleteExpired(Deque<SlidingWindow> dq, long currentTime) {
        while(dq.size()>0 && currentTime-dq.getFirst().getTime()>windowSize)
            dq.removeFirst();
    }

    private void setWindow(String s) {
        Deque<SlidingWindow> dq=new ArrayDeque<>();
        hm.putIfAbsent(s,dq);
    }
}

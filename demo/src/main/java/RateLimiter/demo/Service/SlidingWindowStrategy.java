package RateLimiter.demo.Service;

import RateLimiter.demo.Config.RateLimitProperties;
import RateLimiter.demo.Model.SlidingWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SlidingWindowStrategy implements RateLimitStrategy {
    @Autowired
    private RateLimitProperties properties;
    private ConcurrentHashMap<String, Deque<SlidingWindow>> hm=new ConcurrentHashMap<>();
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
        while(dq.size()>0 && currentTime-dq.getFirst().getTime()>properties.getWindowSize())
            dq.removeFirst();
    }

    private void setWindow(String s) {
        Deque<SlidingWindow> dq=new ArrayDeque<>();
        hm.putIfAbsent(s,dq);
    }
}

package RateLimiter.demo.Service;

import RateLimiter.demo.Model.FixedWindow;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class FixedWindowStrategy implements RateLimitStrategy{
    ConcurrentHashMap<String, FixedWindow> hm=new ConcurrentHashMap<>();
    int interval=2000;//1 window is of 2s (its in millisecons)
    int initialtoken=5;

    @Override
    public boolean isAllowed(String s) {
        long current_time=System.currentTimeMillis();

        setWindow(s, current_time);

        FixedWindow window=hm.get(s);
        synchronized (window) {
            fillWindow(window,current_time);
            if (window.getAvailable_tokens() > 0) {
                window.setAvailable_tokens(window.getAvailable_tokens() - 1);
                return true;
            }
        }
        return false;
    }

    private void fillWindow(FixedWindow window,long time) {
        long diff=(time-window.getLast_window_time());
        if(diff>=(interval))
        {
            window.setLast_window_time(findStartTime(time));
            window.setAvailable_tokens(initialtoken);
        }
    }
    private long findStartTime(long time)
    {
        long starting_time=time-(time%interval);
        return starting_time;
    }
    private void setWindow(String s,long time) {
        hm.putIfAbsent(s,new FixedWindow(initialtoken-1,findStartTime(time)));
    }
}

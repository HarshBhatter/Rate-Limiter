package RateLimiter.demo.Service;

import RateLimiter.demo.Config.RateLimitProperties;
import RateLimiter.demo.Model.FixedWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class FixedWindowStrategy implements RateLimitStrategy{
    @Autowired
    private RateLimitProperties properties;
    ConcurrentHashMap<String, FixedWindow> hm=new ConcurrentHashMap<>();
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
        if(diff>=(properties.getWindowSize()))
        {
            window.setLast_window_time(findStartTime(time));
            window.setAvailable_tokens(properties.getMaxRequests());
        }
    }
    private long findStartTime(long time)
    {
        long starting_time=time-(time % properties.getWindowSize());
        return starting_time;
    }
    private void setWindow(String s,long time) {
        hm.putIfAbsent(s,new FixedWindow(properties.getMaxRequests(),findStartTime(time)));
    }
}

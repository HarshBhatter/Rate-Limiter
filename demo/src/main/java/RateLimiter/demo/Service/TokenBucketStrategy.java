package RateLimiter.demo.Service;

import RateLimiter.demo.Model.TokenBucket;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
@Service
public class TokenBucketStrategy implements RateLimitStrategy{
    ConcurrentHashMap<String, TokenBucket> hm=new ConcurrentHashMap<>();
    int capacity=500;
    int rate=5;// i.e 5/s
    @Override
    public synchronized boolean isAllowed(String s) {
        long current_time=System.currentTimeMillis();

        setbucket(s, current_time);


        TokenBucket bucket=hm.get(s);
        synchronized (bucket) {
            fillbucket(bucket, current_time);
            if (bucket.getAvailable_tokens() > 0) {
                bucket.setAvailable_tokens(bucket.getAvailable_tokens() - 1);
                return true;
            }
        }
        return false;
    }

    private void setbucket(String s,long time) {
        hm.putIfAbsent(s,new TokenBucket(rate-1,time));
    }

    public  void fillbucket(TokenBucket bucket,long time)
    {
        long timepassed=(time-bucket.getLast_refill_time())/1000;
        long add=timepassed*rate;
        bucket.setAvailable_tokens((int)Math.min(capacity,bucket.getAvailable_tokens()+add));
        bucket.setLast_refill_time(time);
    }
}

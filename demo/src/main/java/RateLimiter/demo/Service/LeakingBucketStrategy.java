package RateLimiter.demo.Service;

import RateLimiter.demo.Model.LeakingBucket;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;


//in this algorithm we need a schedules backend which is schedules at the given rate and we just check here the bucket size and add to queue . queue is emptied by scheduler
//here i have done in a way that will keep https request waiting and it will keep threads busy as a result of which all our threads might get acquired
@Service
public class LeakingBucketStrategy implements RateLimitStrategy{
    ConcurrentHashMap<String, LeakingBucket> hm=new ConcurrentHashMap<>();
    long rate=2000;//i.e 2s...and we leak 3
    int capacity=10;
    @Override
    public boolean isAllowed(String s) {
        long time=System.currentTimeMillis();

        setBucket(s,time);

        LeakingBucket bucket=hm.get(s);
        int idx=0;
        synchronized (bucket)
        {
            leak(bucket,time);
            if(bucket.getAvailable_tokens()==capacity)
                return false;
            idx= bucket.getAvailable_tokens()+1;
            bucket.setAvailable_tokens(idx);
        }
        int required_time_blocks=(idx/3)+1;
        while((System.currentTimeMillis()-time)/rate<required_time_blocks) {}
        return true;

    }

    private void leak(LeakingBucket bucket,long time) {
        long diff=(time-bucket.getLast_empty_time())/rate;
        bucket.setAvailable_tokens((int)Math.max(bucket.getAvailable_tokens()-(diff*3), bucket.getAvailable_tokens()));
        bucket.setLast_empty_time(time);
    }

    private void setBucket(String s,long time) {
        hm.putIfAbsent(s,new LeakingBucket(0,time));
    }

}

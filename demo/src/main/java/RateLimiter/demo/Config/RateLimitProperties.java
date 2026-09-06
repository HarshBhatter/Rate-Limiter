package RateLimiter.demo.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="rate-limit")
public class RateLimitProperties {
    private int maxRequests;
    private long windowSize;
    private int capacity;
    private int refillRate;

    public int getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(int maxRequests) {
        this.maxRequests = maxRequests;
    }

    public long getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(long windowSize) {
        this.windowSize = windowSize;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRefillRate() {
        return refillRate;
    }

    public void setRefillRate(int refillRate) {
        this.refillRate = refillRate;
    }
}

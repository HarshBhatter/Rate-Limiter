package RateLimiter.demo.Service;

public interface RateLimitStrategy {
    boolean isAllowed(String s);
}

package RateLimiter.demo.Model;

public class TokenBucket {
    int available_tokens;
    long last_refill_time;

    public TokenBucket() {
    }

    public TokenBucket(int available_tokens, long last_refill_time) {
        this.available_tokens = available_tokens;
        this.last_refill_time = last_refill_time;
    }

    public int getAvailable_tokens() {
        return available_tokens;
    }

    public void setAvailable_tokens(int available_tokens) {
        this.available_tokens = available_tokens;
    }

    public long getLast_refill_time() {
        return last_refill_time;
    }

    public void setLast_refill_time(long last_refill_time) {
        this.last_refill_time = last_refill_time;
    }
}

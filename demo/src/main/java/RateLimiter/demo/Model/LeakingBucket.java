package RateLimiter.demo.Model;

public class LeakingBucket{
    int available_tokens;
    long last_empty_time;

    public LeakingBucket() {
    }

    public LeakingBucket(int available_tokens, long last_empty_time) {
        this.available_tokens = available_tokens;
        this.last_empty_time = last_empty_time;
    }

    public int getAvailable_tokens() {
        return available_tokens;
    }

    public void setAvailable_tokens(int available_tokens) {
        this.available_tokens = available_tokens;
    }

    public long getLast_empty_time() {
        return last_empty_time;
    }

    public void setLast_empty_time(long last_empty_time) {
        this.last_empty_time = last_empty_time;
    }
}

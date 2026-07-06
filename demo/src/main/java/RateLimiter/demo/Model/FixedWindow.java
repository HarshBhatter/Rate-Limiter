package RateLimiter.demo.Model;

public class FixedWindow {
    int available_tokens;
    long last_window_time;

    public FixedWindow() {
    }

    public FixedWindow(int available_tokens, long last_window_time) {
        this.available_tokens = available_tokens;
        this.last_window_time = last_window_time;
    }

    public int getAvailable_tokens() {
        return available_tokens;
    }

    public void setAvailable_tokens(int available_tokens) {
        this.available_tokens = available_tokens;
    }

    public long getLast_window_time() {
        return last_window_time;
    }

    public void setLast_window_time(long last_window_time) {
        this.last_window_time = last_window_time;
    }
}

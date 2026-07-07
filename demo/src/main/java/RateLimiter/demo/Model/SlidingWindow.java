package RateLimiter.demo.Model;

public class SlidingWindow {
    private long time;

    public SlidingWindow() {
    }

    public SlidingWindow(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

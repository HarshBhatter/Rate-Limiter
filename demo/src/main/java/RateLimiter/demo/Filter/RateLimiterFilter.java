package RateLimiter.demo.Filter;

import RateLimiter.demo.Service.FixedWindowStrategy;
import RateLimiter.demo.Service.RateLimitStrategy;
import RateLimiter.demo.Service.SlidingWindowStrategy;
import RateLimiter.demo.Service.TokenBucketStrategy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class RateLimiterFilter extends OncePerRequestFilter {
    private final FixedWindowStrategy fixedWindowStrategy;
    private final SlidingWindowStrategy slidingWindowStrategy;
    private final TokenBucketStrategy tokenBucketStrategy;
    public RateLimiterFilter( FixedWindowStrategy fixedWindowStrategy,SlidingWindowStrategy slidingWindowStrategy,TokenBucketStrategy tokenBucketStrategy) {
        this.fixedWindowStrategy = fixedWindowStrategy;
        this.slidingWindowStrategy = slidingWindowStrategy;
        this.tokenBucketStrategy = tokenBucketStrategy;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Incoming Request");
        RateLimitStrategy strategy=null;

        if (request.getRequestURI().contains("/hello") &&
                !request.getRequestURI().contains("fixed") &&
                !request.getRequestURI().contains("sliding"))
            strategy = tokenBucketStrategy;
        else if (request.getRequestURI().contains("/hello/fixed"))
            strategy = fixedWindowStrategy;
        else if (request.getRequestURI().contains("/hello/sliding"))
            strategy = slidingWindowStrategy;

        if (strategy == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String clientId = request.getRemoteAddr();
        boolean allowed = strategy.isAllowed(clientId);
        if (allowed) {
            filterChain.doFilter(request, response);
        }
        else {
            response.sendError(429,"Too many requests");
        }
    }
}

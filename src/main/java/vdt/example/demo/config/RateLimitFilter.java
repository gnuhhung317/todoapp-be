package vdt.example.demo.config;

import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {
    private static final int MAX_REQUESTS_PER_MINUTE = 10;
    private final Map<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String clientIp = request.getRemoteAddr();
        long currentMinute = Instant.now().getEpochSecond() / 60;

        requestCounts.compute(clientIp, (ip, counter) -> {
            if (counter == null || counter.minute != currentMinute) {
                return new RequestCounter(currentMinute, 1);
            } else {
                counter.count++;
                return counter;
            }
        });

        RequestCounter counter = requestCounts.get(clientIp);
        if (counter.count > MAX_REQUESTS_PER_MINUTE) {
            ((HttpServletResponse) response).setStatus(409);
            response.getWriter().write("Too many requests");
            return;
        }

        chain.doFilter(request, response);
    }

    private static class RequestCounter {
        long minute;
        int count;

        RequestCounter(long minute, int count) {
            this.minute = minute;
            this.count = count;
        }
    }
}
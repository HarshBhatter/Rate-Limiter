package RateLimiter.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/hello")
    public String hello()
    {
        return "Hello World";
    }

    @GetMapping("/hello/fixed")
    public String helloFixed()
    {
        return "Hello fixed Window";
    }

    @GetMapping("/hello/sliding")
    public String helloSliding()
    {
        return "Hello Sliding Window";
    }
}

package fer.progi.mjesecari.ppadel.api;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping("/hello-world")
public class HelloWorldController {

    @GetMapping
    public String getGreeting() {
        return "Hello World!";
    }
}

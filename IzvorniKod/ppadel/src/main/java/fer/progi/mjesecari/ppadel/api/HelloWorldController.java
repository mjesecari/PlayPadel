
package fer.progi.mjesecari.ppadel.api;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile({"form-security", "oauth-security"})
@RestController
public class HelloWorldController {

    @GetMapping ("/bok")
    public String getGreeting() {
        return "Hello world";
    }
}

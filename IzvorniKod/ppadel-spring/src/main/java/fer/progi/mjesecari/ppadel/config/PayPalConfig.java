package fer.progi.mjesecari.ppadel.config;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {

    /*@Value("${paypal.client-id}")
    private String clientId;
    @Value("${paypal.client-secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;*/

    @Bean
    public APIContext apiContext(){
        return new APIContext("AT9g3apEmH9iP8AUbo5JDhOlyzHOBJqL2sDqDdYfz3Noak3CiKneANfqvwb0U9RKbrUxD2XR94rRmr_R",
                "EBxTQbzpfWTKSh2w629zdkKvt3On0ro21bukm7nQFYiwW21U9Aa13cDhm48kaJTQrwruIcjL3HTfCP4j","sandbox");
    }
}

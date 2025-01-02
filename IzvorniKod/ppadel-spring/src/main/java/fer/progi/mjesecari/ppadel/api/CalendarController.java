package fer.progi.mjesecari.ppadel.api;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.Events;


import org.springframework.web.bind.annotation.GetMapping;


@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping(value = "/kalendar")
public class CalendarController {

    private static final com.google.api.client.json.JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "PlayPadel spring";
    @Autowired
    private OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    @GetMapping("")
    public ResponseEntity<String> getEvents(
    @RequestParam(value = "sdate") String sdate, 
    @RequestParam(value = "edate") String edate, 
    @RequestParam(value = "q") String q) {
        com.google.api.services.calendar.model.Events eventList;
        String message;
        try {
            // System.out.println(oAuth2User.getAttributes());
            // CustomOAuth2User customOAuth2User = (CustomOAuth2User)oAuth2User;
            // String token = customOAuth2User.getToken();
            // Map<String, Object> attributes = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes();
            // System.out.println(attributes);
            // final OAuth2AuthenticationDetails details = 
            // (OAuth2AuthenticationDetails) auth.getDetails();
            
            //token
            // String accessToken = details.getTokenValue();
            
            GoogleCredential credential = new GoogleCredential().setAccessToken(getAccessToken().getTokenValue());
            
            final DateTime date1 = new DateTime(sdate + "T00:00:00");
            final DateTime date2 = new DateTime(edate + "T23:59:59");
            
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME).build();
            Events events = service.events();
            eventList = events.list("primary").setTimeZone("Asia/Kolkata").setTimeMin(date1).setTimeMax(date2).setQ(q).execute();
            message = eventList.getItems().toString();
            System.out.println("My:" + eventList.getItems());
        } catch (Exception e) {
            
            message = "Exception while handling OAuth2 callback (" + e.getMessage() + ")."
            + " Redirecting to google connection status page.";
        }
        
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    public OAuth2AccessToken getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken token) {
            OAuth2AuthorizeRequest authRequest = OAuth2AuthorizeRequest
                    .withClientRegistrationId(token.getAuthorizedClientRegistrationId())
                    .principal(token)
                    .build();
            OAuth2AuthorizedClient client = oAuth2AuthorizedClientManager.authorize(authRequest);
            OAuth2AccessToken accessToken = client.getAccessToken();
            System.out.println("Token raw value: " + accessToken.getTokenValue());
            System.out.println("Token scopes: " + accessToken.getScopes());
            return accessToken;
        }
        throw new IllegalStateException("Oauth2 Security Context not found!");
    }
}